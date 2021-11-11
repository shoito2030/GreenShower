package jp.ac.hcs.GreenShower.job.request;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.ac.hcs.GreenShower.job.common.CommonEnum;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Apply_type;
import jp.ac.hcs.GreenShower.job.common.JobHuntingData.Content;
import jp.ac.hcs.GreenShower.job.request.JobRequestData.Way;
import jp.ac.hcs.GreenShower.user.UserData;

/**
 * 就職活動申請に関する処理を行うServiceクラス
 * 
 */
@Service
public class JobRequestService {

	@Autowired
	JobRequestRepository jobRequestRepository;

	/**
	 * 就職活動申請の申請一覧情報をユーザの権限に応じて取得する
	 * 
	 * @param user_id ユーザID
	 * @param role    権限
	 * @return - 処理成功時：JobRequestEntityを持つOptional - 処理失敗時：空のOptional
	 */
	public Optional<JobRequestEntity> selectAllRequests(String user_id, String role) {
		JobRequestEntity jobRequestEntity;

		// ユーザIDに紐づく個人情報を取得
		Optional<UserData> userData = selectPersonalInfo(user_id);
		if (userData.isEmpty()) {
			return Optional.empty();
		}

		try {
			jobRequestEntity = jobRequestRepository.selectAllRequests(userData.get().getClassroom());

			// ユーザが『生徒』の場合は、ユーザ自身の申請情報のみを抽出する
			if (role.equals("ROLE_STUDENT")) {
				jobRequestEntity.setJobRequestList(jobRequestEntity.getJobRequestList().stream()
						.filter(report -> report.getApplicant_id().equals(user_id)).collect(toList()));
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(jobRequestEntity);
	}

	/**
	 * 申請IDに紐づく申請情報を１件取得する
	 * 
	 * @param apply_id 申請ID
	 * @param user_id  ユーザID
	 * @param role     ユーザの権限
	 * @return - 処理成功時：JobRequestDataを持つOptional - 処理失敗時、不正な操作時：空のOptional
	 */
	public Optional<JobRequestData> selectOne(String apply_id, String user_id, String role) {
		JobRequestData jobRequestData;

		try {
			jobRequestData = jobRequestRepository.selectOne(apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}

		if (role.equals("ROLE_STUDENT")) {
			// 取得した情報が申請者本人のものではない場合
			if (!user_id.equals(jobRequestData.getApplicant_id())) {
				return Optional.empty();
			}
		} else if (role.equals("ROLE_TEACHER")) {
			// 担任の所属クラスを取得
			String classroom = selectPersonalInfo(user_id).get().getClassroom();

			// 取得した情報が担任の受け持つクラスの情報ではない場合
			if (!classroom.equals(jobRequestData.getClassroom())) {
				return Optional.empty();
			}
		}

		return Optional.ofNullable(jobRequestData);
	}

	/**
	 * ユーザIDに紐づく個人情報を1件取得
	 * 
	 * @param user_id ユーザID
	 * @return - 処理成功時：UserDataを持つOptional - 処理失敗時：空のOptional
	 */
	public Optional<UserData> selectPersonalInfo(String user_id) {
		UserData userData;

		try {
			userData = jobRequestRepository.selectPersonalInfo(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(userData);
	}

	/**
	 * クラスと出席番号からユーザの個人情報を1件取得（API用）
	 * 
	 * @param classroom    クラス
	 * @param class_number 出席番号
	 * @return - 処理成功時：UserData - 処理失敗時：null
	 */
	public UserData selectPersonalInfo(String classroom, String class_number) {
		UserData userData;

		try {
			userData = jobRequestRepository.selectPersonalInfo(classroom, class_number);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}

		return userData;
	}

	/**
	 * 担任が受け持つ生徒数を取得する
	 * 
	 * @param user_id 担任のユーザID
	 * @return - 処理成功時: studentsNumber(生徒数) - 処理失敗時：studentsNumber(0)
	 */
	public int selectStudentsNumber(String user_id) {
		int studentsNumber;

		try {
			studentsNumber = jobRequestRepository.selectStudentsNumber(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return 0;
		}

		return studentsNumber;
	}

	/**
	 * 申請マスタに新たな報告情報を1件追加する
	 * 
	 * @param form             検証済み入力情報
	 * @param applicant_id     申請者ID
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return - true：追加件数1件（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean hasInserted(JobRequestForm form, String applicant_id, String register_user_id) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = jobRequestRepository.insertOne(refillToJobReportData(form, applicant_id, register_user_id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		//TODO finaly句が必要である可能性あり
		return rowNumber > 0;

	}

	/**
	 * 入力情報をJobRequestData型に変換する（insert用）
	 * 
	 * @param form             検証済み入力データ
	 * @param applicant_id     申請者ID
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return JobRequestData
	 */
	private JobRequestData refillToJobReportData(JobRequestForm form, String applicant_id, String register_user_id) {
		JobRequestData data = new JobRequestData();

		int apply_id = jobRequestRepository.apply_id_get() + 1;
		data.setApply_id(String.valueOf(apply_id));
		data.setApplicant_id(applicant_id);
		data.setContent(CommonEnum.getEnum(Content.class, form.getContent()));
		data.setCompany_name(form.getCompany_name());
		data.setIndicate(form.getIndicate());
		data.setDate_activity_from(strLocalDateTimeToDate(form.getDate_activity_from()));
		data.setDate_activity_to(strLocalDateTimeToDate(form.getDate_activity_to()));
		data.setLoc(form.getLoc());

		// 内容を複数（遅刻と早退）を選択している可能性がある
		if (form.getWay().size() >= 2) {
			data.setWay(CommonEnum.getEnum(Way.class, "4"));
		} else {
			data.setWay(CommonEnum.getEnum(Way.class, form.getWay().get(0)));
		}

		data.setApply_type(CommonEnum.getEnum(Apply_type.class, form.getApply_type()));
		data.setDate_absence_from(strLocalDateTimeToDate(form.getDate_absence_from()));
		data.setDate_absence_from(strLocalDateTimeToDate(form.getDate_absence_to()));
		data.setLeave_early_date(strLocalDateTimeToDate(form.getLeave_early_date()));
		data.setAttendance_date(strLocalDateTimeToDate(form.getAttendance_date()));
		data.setRemark(form.getRemark());
		data.setRegister_user_id(register_user_id);
		return data;
	}

	/**
	 * 就職活動申請の状態変更処理を行う
	 * 
	 * @param apply_id 申請ID
	 * @param form     検証済み入力データ
	 * @return - true：更新件数1件（処理成功）の場合 - false：更新件数0件（処理失敗）の場合
	 */
	public boolean hasUpdateJobStatus(String apply_id, JobRequestForm form) {
		int rowNumber = 0;
		try {
			rowNumber = jobRequestRepository.updateJobStatus(apply_id, form.getStatus(), form.getIndicate());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		//TODO finaly句が必要である可能性あり
		return rowNumber > 0;

	}

	/**
	 * 就職活動申請の内容変更処理を行う
	 * 
	 * @param apply_id 申請ID
	 * @param form     検証済み入力データ
	 * @return - true：更新件数1件（処理成功）の場合 - false：更新件数0件（処理失敗）の場合
	 */
	public boolean hasUpdatedJobContent(String apply_id, JobRequestForm form) {
		int rowNumber = 0;
		try {
			rowNumber = jobRequestRepository.updateJobContent(refillToJobRequestDataUpdate(form, apply_id), apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		//TODO finaly句が必要である可能性あり
		return rowNumber > 0;

	}

	/**
	 * 入力情報をJobRequestData型に変換する（内容変更用）
	 * 
	 * @param form     検証済み入力データ
	 * @param apply_id 申請ID
	 * @return JobRequestData
	 */
	private JobRequestData refillToJobRequestDataUpdate(JobRequestForm form, String apply_id) {
		JobRequestData data = new JobRequestData();

		data.setApply_id(String.valueOf(apply_id));
		data.setApply_type(CommonEnum.getEnum(Apply_type.class, form.getApply_type()));
		data.setCompany_name(form.getCompany_name());
		data.setContent(CommonEnum.getEnum(Content.class, form.getContent()));
		data.setDate_activity_from(strLocalDateTimeToDate(form.getDate_activity_from()));
		data.setDate_activity_to(strLocalDateTimeToDate(form.getDate_activity_to()));
		data.setLoc(form.getLoc());

		// 内容を複数（遅刻と早退）を選択している可能性がある
		if (form.getWay().size() >= 2) {
			data.setWay(CommonEnum.getEnum(Way.class, "4"));
		} else {
			data.setWay(CommonEnum.getEnum(Way.class, form.getWay().get(0)));
		}
		data.setDate_absence_from(strLocalDateTimeToDate(form.getDate_absence_from()));
		data.setDate_absence_to(strLocalDateTimeToDate(form.getDate_absence_to()));
		data.setLeave_early_date(strLocalDateTimeToDate(form.getLeave_early_date()));
		data.setAttendance_date(strLocalDateTimeToDate(form.getAttendance_date()));
		data.setRemark(form.getRemark());
		return data;
	}

	/**
	 * LocalDateTime形式の文字列をDate型に変換する
	 * 
	 * @param strDate LocalDateTime形式の文字列
	 * @return date
	 */
	public Date strLocalDateTimeToDate(String strDate) {
		Date date;

		if (strDate == null || strDate.isBlank()) {
			return null;
		} else {
			TemporalAccessor parsed;

			try {
				parsed = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(strDate);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
				return null;
			}
			LocalDateTime localDateTime = LocalDateTime.from(parsed);
			date = Date.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant());
		}

		return date;
	}

	/**
	 * イベントマスタに新たにイベント情報を1件追加する
	 * 
	 * @param form 検査済み入力内容
	 * @param user_id ユーザID
	 * @return - true：追加件数1件（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean hasInsertedEvent(EventForm form, String user_id) {
		int rowNumber = 0;
		jobRequestRepository.insertEvent(refillToEventData(form), user_id);
		return rowNumber > 0;
	}

	/**
	 * 入力情報をEventData型に変換する（イベント情報登録用）
	 * 
	 * @param form 検証済み入力情報
	 * @return EventData
	 */
	private EventData refillToEventData(EventForm form) {
		EventData data = new EventData();

		int event_id = jobRequestRepository.event_id_get() + 1;
		data.setEvent_id((String.valueOf(event_id)));
		data.setCompany_name(form.getCompany_name());
		data.setDatetime(strLocalDateTimeToDate(form.getDatetime()));
		data.setLoc(form.getLoc());
		data.setContent(form.getContent());
		data.setBring(form.getBring());
		return data;

	}

	/**
	 * 申請状態を取得する
	 * @param apply_id 申請ID
	 * @return status 
	 */
	public String selectJobHuntingStatus(String apply_id) {
		String status;
		try {
			status = jobRequestRepository.selectJobHuntingStatus(apply_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		return status;
	}

}
