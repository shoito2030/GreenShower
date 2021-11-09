package jp.ac.hcs.GreenShower.job.csv;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * 就職活動申請のCSV出力に関する処理を行うServiceクラス
 * 
 */
@Service
public class JobCsvRequestService {
	
	@Autowired
	JobCsvRequestRepository jobCsvRequestRepository;
	
	/**
	 * 就職活動申請一覧情報をCSVファイルとしてサーバに保存する.
	 * 
	 * @throws DataAccessException
	 */
	public void jobRequestListCsvOut() throws DataAccessException {
		jobCsvRequestRepository.requestListCsvOut();
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
