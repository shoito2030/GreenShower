package jp.ac.hcs.GreenShower.job.request;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.ac.hcs.GreenShower.job.common.CommonEnum;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Apply_type;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Content;
import jp.ac.hcs.GreenShower.job.request.JobRequestData.Way;

/**
 * 就職活動申請に関する処理を行うServiceクラス
 * 
 */
@Service
public class JobRequestService {

	@Autowired
	JobRequestRepository jobRequestRepository;

	/**
	 * 就職活動申請の申請情報を全件取得する
	 * 
	 * @return Optional<jobRequestEntity>
	 */
	public Optional<JobRequestEntity> selectAllRequests(String user_id, String role) {
		JobRequestEntity jobRequestEntity;

		try {
			jobRequestEntity = jobRequestRepository.selectAllRequests();

			// ユーザが生徒の場合は、ユーザ自身の申請情報のみを抽出する
			if (role.equals("ROLE_STUDENT")) {
				jobRequestEntity.setJobRequestList(jobRequestEntity.getJobRequestList().stream()
						.filter(report -> report.getApplicant_id().equals(user_id)).collect(toList()));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobRequestEntity = null;
		}
		return Optional.ofNullable(jobRequestEntity);
	}

	/**
	 * 特定の申請情報を1件取得
	 * 
	 * @param apply_id 申請ID
	 * @return
	 */
	public Optional<JobRequestData> selectOne(String apply_id) {
		JobRequestData jobRequestData;

		try {
			jobRequestData = jobRequestRepository.selectOne(apply_id);
		} catch (DataAccessException e) {
			//System.out.println("request selectOneで例外キャッチ");
			e.printStackTrace();
			jobRequestData = null;
		}
		return Optional.ofNullable(jobRequestData);
	}

	/**
	 * 申請マスタに新たな報告情報を1件追加する
	 * 
	 * @param form             検証済み入力情報
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean insert(JobRequestForm form, String register_user_id) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = jobRequestRepository.insertOne(refillToJobReportData(form, register_user_id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber > 0;

	}

	/**
	 * 入力情報をJobRequestData型に変換する（insert用）
	 * 
	 * @param form    検証済み入力データ
	 * @param user_id 登録処理を実行したユーザのID
	 * @return JobRequestData
	 */
	private JobRequestData refillToJobReportData(JobRequestForm form, String register_user_id) {
		JobRequestData data = new JobRequestData();

		int apply_id = jobRequestRepository.apply_id_get() + 1;
		data.setApply_id(String.valueOf(apply_id));
		data.setApplicant_id(register_user_id);
		data.setContent(CommonEnum.getEnum(Content.class, form.getContent()));
		data.setCompany_name(form.getCompany_name());
		data.setIndicate(form.getIndicate());
		data.setDate_activity_from(strLocalDateTimeToDate(form.getDate_activity_from()));
		data.setDate_activity_to(strLocalDateTimeToDate(form.getDate_activity_to()));
		data.setLoc(form.getLoc());
		data.setWay(CommonEnum.getEnum(Way.class, form.getWay()));
		data.setApply_type(CommonEnum.getEnum(Apply_type.class, form.getApply_type()));
		data.setDate_absence_from(strLocalDateTimeToDate(form.getDate_activity_from()));
		data.setDate_absence_from(strLocalDateTimeToDate(form.getDate_activity_to()));
		data.setLeave_early_date(strLocalDateTimeToDate(form.getLeave_eary_date()));
		data.setAttendance_date(strLocalDateTimeToDate(form.getAttendance_date()));
		data.setRemark(form.getRemark());
		data.setRegister_user_id(register_user_id);
		return data;
	}

	/**
	 * 就職活動申請の状態変更処理を行う
	 * 
	 * @param user_id ユーザID
	 * @return Optional<jobRequestEntity>
	 */
	public int updateJobStatus(String apply_id, JobRequestForm form) {
		int rowNumber = 0;
		try {
			rowNumber = jobRequestRepository.updateJobStatus(apply_id, form);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber;

	}

	/**
	 * LocalDateTime形式の文字列をDate型に変換する
	 * 
	 * @param strDate LocalDateTime形式の文字列
	 * @return date
	 */
	public Date strLocalDateTimeToDate(String strDate) {
		Date date;

		if (strDate.equals("")) {
			return null;
		} else {
			LocalDateTime localDateTime = LocalDateTime.parse(strDate.replace("T", " "),
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			date = Date.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant());
		}

		return date;
	}

	public String searchUserId(String classi, String number) {
		return jobRequestRepository.searchUserId(classi, number);
	}

}
