package jp.ac.hcs.GreenShower.report;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
	 * @param form  登録時の入力チェック用UserForm
	 * @param model
	 * @return 受験報告情報登録画面
	 */
	@GetMapping("report/insert")
	public String getReportInsert(ReportForm form, Model model) {
		return "report/insert";
	}

	/**
	 * 新たにユーザ情報を追加する
	 * 
	 * @param form          入力情報
	 * @param bindingResult 入力情報の検証結果
	 * @param principal     ログイン情報
	 * @param model
	 * @return - 処理失敗時：ユーザ登録画面（管理者用） - 処理成功時：getUserListに処理を委譲しているのでそちらを参照すること
	 */
	@PostMapping("report/insert")
	public String getReportInsert(@ModelAttribute @Validated ReportForm form, BindingResult bindingResult,
			Principal principal, Model model) {

		// 入力チェックに引っかかった場合、前の画面に戻る
//		if (bindingResult.hasErrors()) {
//			log.info("[" + principal.getName() + "]さんが新しいユーザの登録に失敗しました。");
//			log.info("入力情報：" + form.toString());
//
//			model.addAttribute("errmsg", "ユーザ情報の登録に失敗しました。入力内容をお確かめください。");
//
//			return getReportInsert(form, model);
//		}

		// 追加処理実行
		reportService.insert(form, principal.getName());

		log.info("[" + principal.getName() + "]さんが新しいユーザを登録しました。");
		log.info("新規ユーザ情報：" + form.toString());

		model.addAttribute("msg", "ユーザ情報の作成が無事完了しました。");

		return getReportList(model);
	}
	
	/**
	 * ユーザ詳細情報画面を表示する
	 * 
	 * @param user_id   検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return - 処理成功時：ユーザ詳細情報画面 - 処理失敗時：トップ画面
	 */
	@GetMapping("report/detail/{id}")
	public String getUserDetail(@PathVariable("id") String report_id, Principal principal, Model model) {

		// ユーザIDに紐づく情報を取得（取得できなかった場合は空のOptionalが格納される）
		Optional<ReportData> reportData = reportService.selectOne(report_id);

		// 処理失敗によりトップ画面へ
		if (reportData.isEmpty()) {
			return "index";
		}

		model.addAttribute("reportData", reportData.get());

		return "report/detail";
	}
	
	
	/**
	 * ユーザ詳細情報画面を表示する
	 * 
	 * @param user_id   検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return - 処理成功時：ユーザ詳細情報画面 - 処理失敗時：トップ画面
	 */
	@GetMapping("report/edit/{id}")
	public String getEdit(@PathVariable("id") String report_id, Principal principal, Model model) {

//		// ユーザIDに紐づく情報を取得（取得できなかった場合は空のOptionalが格納される）
//		Optional<ReportData> reportData = reportService.selectOne(report_id);
//
//		// 処理失敗によりトップ画面へ
//		if (reportData.isEmpty()) {
//			return "index";
//		}
//
//		model.addAttribute("reportData", reportData.get());

		return "report/edit";
	}

}
