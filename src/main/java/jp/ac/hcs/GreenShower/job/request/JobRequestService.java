package jp.ac.hcs.GreenShower.job.request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
	public Optional<JobRequestEntity> selectAllRequests() {
		JobRequestEntity jobRequestEntity;

		try {
			jobRequestEntity = jobRequestRepository.selectAllRequests();
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobRequestEntity = null;
		}
		return Optional.ofNullable(jobRequestEntity);
	}
	
	/**
	 * 就職活動申請の申請情報を自分の分取得する
	 * 
	 * @param user_id ユーザID
	 * @return Optional<jobRequestEntity>
	 */
	public Optional<JobRequestEntity> selectStudentRequests(String user_id) {
		JobRequestEntity jobRequestEntity;

		try {
			jobRequestEntity = jobRequestRepository.selectStudentRequests(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobRequestEntity = null;
		}
		return Optional.ofNullable(jobRequestEntity);
	}
//	
//	/**
//	 * 就職活動申請の状態変更処理を行う
//	 * 
//	 * @param user_id ユーザID
//	 * @return Optional<jobRequestEntity>
//	 */
//	public boolean updateJobStatus(String apply_id,JobRequestForm form){
//		boolean success=false;
//		try {
//			success = jobRequestRepository.updateJobStatus(report_id,form);
//		}catch (DataAccessException e) {
//			e.printStackTrace();
//		}
//		return success;
//		
//	}
}
