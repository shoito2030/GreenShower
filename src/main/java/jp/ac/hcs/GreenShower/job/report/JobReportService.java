package jp.ac.hcs.GreenShower.job.report;

import static java.util.stream.Collectors.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.ac.hcs.GreenShower.user.UserData;

/**
 * 就職活動申請報告に関する処理を行うServiceクラス
 * 
 */
@Service
public class JobReportService {

	@Autowired
	JobReportRepository jobReportRepository;

	/**
	 * 就職活動申請の報告情報を全件取得する
	 * 
	 * @param user_id ユーザID
	 * @param role アクターの権限
	 * @return Optional<jobReportEntity>
	 */
	public Optional<JobReportEntity> selectAllReports(String user_id, String role) {
		JobReportEntity jobReportEntity;
		
		// ユーザIDに紐づく個人情報を取得
		Optional<UserData> userData = selectPersonalInfoUserId(user_id);
		if (userData.isEmpty()) {
			return Optional.empty();
		}

		try {
			jobReportEntity = jobReportRepository.selectAllReports(userData.get().getClassroom());

			// ユーザが生徒の場合は、ユーザ自身の申請情報のみを抽出する
			if (role.equals("ROLE_STUDENT")) {
				jobReportEntity.setJobReportList(jobReportEntity.getJobReportList().stream()
						.filter(report -> report.getApplicant_id().equals(user_id)).collect(toList()));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}

		return Optional.ofNullable(jobReportEntity);
	}



	/**
	 * 就職活動申請の報告情報を1件取得する
	 * 
	 * @param apply_id
	 * @return Optional<jobJobReportData>
	 */
	public Optional<JobReportData> selectOne(String apply_id) {
		JobReportData jobReportData;

		try {
			jobReportData = jobReportRepository.selectOne(apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(jobReportData);
	}

	/**
	 * applyIdからユーザの個人情報を1件取得
	 * 
	 * @param apply_id 申請ID
	 * @return Optional<userData>
	 */
	public Optional<UserData> selectPersonalInfoApply(String apply_id) {
		UserData userData;

		try {
			userData = jobReportRepository.selectPersonalInfoApply(apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(userData);
	}
	
	/**
	 * userIdからユーザの個人情報を1件取得
	 * 
	 * @param user_id ユーザID
	 * @return Optional<userData>
	 */
	private Optional<UserData> selectPersonalInfoUserId(String user_id) {
		UserData userData;

		try {
			userData = jobReportRepository.selectPersonalInfoUserID(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(userData);
	}
	
	/**
	 * 申請状態を取得する
	 * @param apply_id 申請ID
	 * @return status 
	 */
	public String selectJobHuntingStatus(String apply_id) {
		String status;
		try {
			status = jobReportRepository.selectJobHuntingStatus(apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		return status;
	}

	/**
	 * 報告マスタに新たな報告情報を1件追加する
	 * 
	 * @param form             検証済み入力情報
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean insert(JobReportForm form, String register_user_id) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = jobReportRepository.insertOne(refillToJobReportData(form, register_user_id));
			// 就職活動申請マスタの状態を6:報告承認待に変更する
			jobReportRepository.updateStatusOne(form.getApply_id());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return rowNumber > 0;
	}

	/**
	 * 報告マスタの状態を任意の状態に変更する
	 * 
	 * @param form 検証済み入力情報
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean updateStatus(JobReportForm form) {
		int rowNumber = 0;
		try {
			// 変更処理を行い、変更できた件数を取得
			rowNumber = jobReportRepository.updateStatus(form);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return rowNumber > 0;

	}

	/**
	 * 入力情報をJobReportData型に変換する（insert用）
	 * 
	 * @param form    検証済み入力データ
	 * @param user_id 登録処理を実行したユーザのID
	 * @return JobReportData
	 */
	private JobReportData refillToJobReportData(JobReportForm form, String register_user_id) {
		JobReportData data = new JobReportData();

		data.setApply_id(form.getApply_id());
		data.setAdvance_or_retreat(form.isAdvance_or_retreat());
		data.setRemark(form.getRemark());
		data.setRegister_user_id(register_user_id);

		return data;
	}


	
	/**
	 * 報告マスタの進退を変更する
	 * 
	 * @param form     検証済み入力情報
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean updateAdvance_or_retreat(JobReportForm form) {
		
		int rowNumber = 0;
		try {
			// 変更処理を行い、変更できた件数を取得
			if(form.isAdvance_or_retreat()) {
				rowNumber = jobReportRepository.updateAdvance_or_retreat_to_true(form.getApply_id());
			} else {
				rowNumber = jobReportRepository.updateAdvance_or_retreat_to_false(form.getApply_id());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return rowNumber > 0;

	}
	
	
	/**
	 * 報告マスタの備考を変更する
	 * 
	 * @param form     検証済み入力情報
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean updateRemark(JobReportForm form) {
		
		int rowNumber = 0;
		try {
			// 変更処理を行い、変更できた件数を取得
			rowNumber = jobReportRepository.updateRemark(form.getApply_id(), form.getRemark());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return rowNumber > 0;

	}


	/**
	 * 報告修正した際にstatusを変更する
	 * @param apply_id 申請ID
	 */
	public boolean updateStatusFixed(String apply_id) {
		int rowNumber = 0;
		try {
			// 変更処理を行い、変更できた件数を取得
			rowNumber = jobReportRepository.updateStatusOne(apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return rowNumber > 0;
	}





}
