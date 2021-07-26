package jp.ac.hcs.GreenShower.report;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ac.hcs.GreenShower.WebConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReportController {

	@Autowired
	private ReportService reportService;

	/**
	 * 受験報告情報一覧画面を表示する - 処理失敗時：トップ画面を表示
	 * 
	 * @param model
	 * @return 受験報告情報一覧画面 or トップ画面
	 */
	@GetMapping("/report/list")
	public String getReportList(Model model) {

	// 取得できなかった場合は空のOptionalが格納される
	Optional<ReportEntity> reportEntity = reportService.selectAll();

		// 処理失敗によりトップ画面へ
		if (reportEntity.isEmpty()) {
			return "index";
		}

		model.addAttribute("reportEntity", reportEntity.get());
		return "report/list";
	}

	/**
	 * 受験報告情報登録画面を表示する
	 * 
	 * @param form  登録時の入力チェック用ReportForm
	 * @param model
	 * @return 受験報告情報登録画面
	 */
	@GetMapping("report/insert")
	public String getReportInsert(ReportForm form, Model model) {
		return "report/insert";
	}

	/**
	 * 新たに受験報告情報を登録する
	 * 
	 * @param form          登録時の入力チェック用ReportForm
	 * @param bindingResult 入力情報の検証結果
	 * @param principal     ログイン情報
	 * @param model
	 * @return 受験報告情報一覧画面
	 */
	@PostMapping("report/insert")
	public String getReportInsert(@ModelAttribute @Validated ReportForm form, BindingResult bindingResult,
			Principal principal, Model model) {
		

		// コメントアウトを外すと、フォームのバリデーション機能でエラーが送出される。原因不明
//		// 入力チェックに引っかかった場合、前の画面に戻る
//		if (bindingResult.hasErrors()) {
//			log.info("[" + principal.getName() + "]さんが新しいユーザの登録に失敗しました。");
//			log.info("入力情報：" + form.toString());
//
//			model.addAttribute("errmsg", "ユーザ情報の登録に失敗しました。入力内容をお確かめください。");
//
//			return getReportInsert(form, model);
//		}
		
		log.info("入力情報（試験区分）：" + form.getTest_section());
		log.info("最終試験か：" + form.isTest_final());

		// 追加処理実行
		reportService.insert(form, principal.getName());

		log.info("[" + principal.getName() + "]さんが新しいユーザを登録しました。");
		log.info("新規ユーザ情報：" + form.toString());

		model.addAttribute("msg", "ユーザ情報の作成が無事完了しました。");

		return getReportList(model);
	}
	
	/**
	 * 受験報告詳細情報画面を表示する
	 * 
	 * @param report_id   検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return - 処理成功時：受験報告詳細情報画面 - 処理失敗時：受験報告情報一覧画面
	 */
	@GetMapping("report/detail/{id}")
	public String getReportDetail(@PathVariable("id") String report_id, Principal principal, Model model) {

		// ユーザIDに紐づく情報を取得（取得できなかった場合は空のOptionalが格納される）
		Optional<ReportData> reportData = reportService.selectOne(report_id);

		// 処理失敗により受験報告情報一覧画面へ
		if (reportData.isEmpty()) {
			return getReportList(model);
		}

		model.addAttribute("reportData", reportData.get());

		return "report/detail";
	}
	
	
	/**
	 * 受験報告編集画面を表示する
	 * 
	 * @param report_id   検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return - 処理成功時：受験報告編集画面 - 処理失敗時：受験報告情報一覧画面
	 */
	@GetMapping("report/edit/{id}")
	public String getEdit(@PathVariable("id") String report_id, Principal principal, Model model) {

		// ユーザIDに紐づく情報を取得（取得できなかった場合は空のOptionalが格納される）
		Optional<ReportData> reportData = reportService.selectOne(report_id);

		// 処理失敗によりトップ画面へ
		if (reportData.isEmpty()) {
			return getReportList(model);
		}

		model.addAttribute("reportData", reportData.get());

		return "report/edit";
	}
	
	/**
	 * 受験報告情報を更新をする
	 * 
	 * @param user_id   検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告情報一覧画面
	 */
	@PostMapping("report/update")
	public String getReportUpdate(@ModelAttribute @Validated ReportForm form,
			 Principal principal, Model model) {
		
		log.info("入力情報：" + form.toString());

//		// 入力チェックに引っかかった場合、前の画面に戻る
//		if (bindingResult.hasErrors()) {
//			log.info("[" + principal.getName() + "]さんが新しいユーザの登録に失敗しました。");
//			log.info("入力情報：" + form.toString());
//
//			model.addAttribute("errmsg", "ユーザ情報の登録に失敗しました。入力内容をお確かめください。");
//
//			return getReportInsert(form, model);
//		}
		
		// ユーザIDに紐づく情報を取得（取得できなかった場合は空のOptionalが格納される）
		boolean isSuccess = reportService.update(form);
		
		if(isSuccess) {
			log.info("[" + principal.getName() + "]さんが受験報告情報を修正しました");
		} else {
			log.info("[" + principal.getName() + "]さんが受験報告情報を修正しました");
		}
		

		return getReportList(model);
	}
	
	
	/**
	 * 受験報告情報を更新をする
	 * 
	 * @param user_id   検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return 受験報告情報一覧画面
	 */
	@PostMapping("report/change_status/{report_id}")
	public String changeStauts(@PathVariable("report_id") String report_id, String report_status,
			 Principal principal, Model model) {
		
		reportService.updateStatus(report_id, report_status);
		log.info("[" + principal.getName() + "]さんがレポートステータスを更新しました");
		return getReportList(model);
	}
	
	/**
	 * 自分の全てのタスク情報をCSVファイルとしてダウンロードさせる.
	 * 
	 * @param principal ログイン情報
	 * @param model
	 * @return タスク情報のCSVファイル
	 */
	@PostMapping("/report/csv")
	public ResponseEntity<byte[]> getReportCsv(Principal principal, Model model) {

		final String OUTPUT_FULLPATH = WebConfig.OUTPUT_PATH + WebConfig.FILENAME_TASK_CSV;

		log.info("[" + principal.getName() + "]CSVファイル作成:" + OUTPUT_FULLPATH);

		// CSVファイルをサーバ上に作成
		reportService.reportListCsvOut();

		// CSVファイルをサーバから読み込み
		byte[] bytes = null;
		try {
			bytes = reportService.getFile(OUTPUT_FULLPATH);
			log.info("[" + principal.getName() + "]CSVファイル読み込み成功:" + OUTPUT_FULLPATH);
		} catch (IOException e) {
			log.warn("[" + principal.getName() + "]CSVファイル読み込み失敗:" + OUTPUT_FULLPATH);
			e.printStackTrace();
		}

		// CSVファイルのダウンロード用ヘッダー情報設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", WebConfig.FILENAME_TASK_CSV);

		// CSVファイルを端末へ送信
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}

}
