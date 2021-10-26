package jp.ac.hcs.GreenShower.job.request;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
	 * @param role アクターの権限
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
	 * @param role アクターの権限
	 * @return Optional<jobRequestEntity>
	 */
	public Optional<JobRequestEntity> selectStudentRequests(String name) {
		JobRequestEntity jobRequestEntity;

		try {
			jobRequestEntity = jobRequestRepository.selectStudentRequests(name);
		} catch (DataAccessException e) {
			e.printStackTrace();
			jobRequestEntity = null;
		}
		return Optional.ofNullable(jobRequestEntity);
	}

	/**
	 * 就職活動申請一覧情報をCSVファイルとしてサーバに保存する.
	 * 
	 * @param user_id ユーザID
	 * @throws DataAccessException
	 */
	public void jobRequestListCsvOut() throws DataAccessException {
		jobRequestRepository.requestlistCsvOut();
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
