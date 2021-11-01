package jp.ac.hcs.GreenShower.job.report;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jp.ac.hcs.GreenShower.job.common.CommonEnum;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Status;
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
	 * @param principal ユーザのログイン情報
	 * @return Optional<jobReportEntity>
	 */
	public Optional<JobReportEntity> selectAllReports(Principal principal) {
		JobReportEntity jobReportEntity;

		// アクターの権限を取得
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		try {
			jobReportEntity = jobReportRepository.selectAllReports();

			// ユーザが生徒の場合は、ユーザ自身の申請情報のみを抽出する
			if (role.equals("ROLE_STUDENT")) {
				jobReportEntity.setJobReportList( jobReportEntity.getJobReportList().stream()
						.filter(report -> report.getApplicant_id().equals(principal.getName()))
						.collect(toList()));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobReportEntity = null;
		}

		return Optional.ofNullable(jobReportEntity);
	}

	/**
	 * 就職活動申請の報告情報を1件取得する
	 * 
	 * @param apply_id
	 * @return Optional<jobJobReportData>
	 */
	public Optional<JobHuntingData> selectOne(String apply_id) {
		JobHuntingData jobReportData;

		try {
			jobReportData = jobReportRepository.selectOne(apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobReportData = null;
		}
		return Optional.ofNullable(jobReportData);
	}

	/**
	 * ユーザの個人情報を1件取得
	 * 
	 * @param apply_id 申請ID
	 * @return Optional<userData>
	 */
	public Optional<UserData> selectPersonalInfo(String apply_id) {
		UserData userData;

		try {
			userData = jobReportRepository.selectPersonalInfo(apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			userData = null;
		}
		return Optional.ofNullable(userData);
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
		}
		return rowNumber > 0;
	}
	
	/**
	 * 報告マスタの状態を任意の状態に変更する
	 * 
	 * @param form             検証済み入力情報
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return 
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean updateStatus(JobReportForm form, String update_user_id) {
		int rowNumber = 0;
		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = jobReportRepository.updateStatus(refillToJobHuntingData(form));
		} catch (DataAccessException e) {
			e.printStackTrace();
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
	 * 入力情報をJobHuntingData型に変換する（状態変更用）
	 * 
	 * @param form    検証済み入力データ
	 * @param user_id 登録処理を実行したユーザのID
	 * @return JobReportData
	 */
	private JobHuntingData refillToJobHuntingData(JobReportForm form) {
		JobHuntingData data = new JobHuntingData();

		data.setApply_id(form.getApply_id());
		data.setStatus(CommonEnum.getEnum(Status.class,form.getStatus()));
		data.setIndicate(form.getIndicate());

		return data;
	}

	/**
	 * サーバーに保存されているファイルを取得して、byte配列に変換する.
	 * 
	 * @param fileName ファイル名
	 * @return ファイルのbyte配列
	 * @throws IOException ファイル取得エラー
	 */
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}

}
