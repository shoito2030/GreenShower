package jp.ac.hcs.GreenShower.job.csv;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.ac.hcs.GreenShower.WebConfig;
import jp.ac.hcs.GreenShower.job.request.JobRequestService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class JobCsvController {
	
	@Autowired
	private JobRequestService jobRequestService;
	
	
//	@GetMapping("/request/insert")
//	public String hoge1() {
//		return "job/request/insert";
//	}
//	
//	@GetMapping("/request/list")
//	public String hoge2() {
//		return "job/request/list";
//	}
//	
//	@GetMapping("/report/list")
//	public String hoge3() {
//		return "job/report/list";
//	}
//	
//	@GetMapping("/event-registration")
//	public String hoge4() {
//		return "job/event-registration";
//	}
	
	/**
	 * すべての就職活動申請一覧情報をCSVファイルとしてダウンロードさせる
	 * @param model
	 * @return 就職活動申請一覧情報のCSVファイル
	 */
	@GetMapping("/job/csv")
	public ResponseEntity<byte[]> getJobRequestCsv(Principal principal, Model model) {

		final String OUTPUT_FULLPATH = WebConfig.OUTPUT_PATH + WebConfig.FILENAME_JOBREQUEST_CSV;

		log.info("[" + principal.getName() + "]CSVファイル作成:" + OUTPUT_FULLPATH);

		//CSVファイルをサーバ上に作成
		jobRequestService.jobRequestListCsvOut();

		// CSVファイルをサーバから読み込み
		byte[] bytes = null;
		try {
			bytes = jobRequestService.getFile(OUTPUT_FULLPATH);
			log.info("[" + principal.getName() + "]CSVファイル読み込み成功:" + OUTPUT_FULLPATH);
		} catch (IOException e) {
			log.warn("[" + principal.getName() + "]CSVファイル読み込み失敗:" + OUTPUT_FULLPATH);
			e.printStackTrace();
		}

		// CSVファイルのダウンロード用ヘッダー情報設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", WebConfig.FILENAME_JOBREQUEST_CSV);

		// CSVファイルを端末へ送信
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}
	
	
}
