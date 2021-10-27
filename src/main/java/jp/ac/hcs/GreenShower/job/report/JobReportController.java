package jp.ac.hcs.GreenShower.job.report;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

/** 
 * 就職活動報告に関する処理を行うControllerクラス
 * 
 */
@Slf4j
@Controller
public class JobReportController {
	@Autowired
	private JobReportService jobReportService;
	/**
	 * 就職活動申請報告一覧画面を表示する - 処理失敗時：トップ画面を表示
	 * 
	 * @param model
	 * @return 就職活動申請報告一覧画面 or トップ画面
	 */
	@GetMapping("/job/report/list")
	public String getReportList(Principal principal, Model model) {

//	// 取得できなかった場合は空のOptionalが格納される
		Optional<JobReportEntity> jobReportEntity;
		String role = ((Authentication)principal).getAuthorities().toString().replace("[","").replace("]", "");
		// 取得できなかった場合は空のOptionalが格納される
		if (role.equals("ROLE_STUDENT")) {
			jobReportEntity = jobReportService.selectStudentReports(principal.getName());
		} else {
			jobReportEntity = jobReportService.selectAllReports();
		}
		
		// 処理失敗によりトップ画面へ
		if (jobReportEntity.isEmpty()) {
			return "index";
		} 
		
		model.addAttribute("jobReportEntity", jobReportEntity.get());
		return "job/report/list";
	}
	
	/**
	 * 就職活動報告新規作成画面を表示する
	 * 
	 * @param model
	 * @return 就職活動報告新規作成画面
	 */
	@GetMapping("/job/report/insert")
	public String insertReportList(Principal principal, Model model) {
		System.out.println("報告新規作成画面");
		return "job/report/insert";
	}
	
	/**
	 * 新たに受験報告情報を登録する
	 * 
	 * @param form          登録時の入力チェック用JobReportForm
	 * @param bindingResult 入力情報の検証結果
	 * @param principal     ログイン情報
	 * @param model
	 * @return 受験報告情報一覧画面
	 */
	@PostMapping("job/report/insert")
	public String getJobReportInsert(@ModelAttribute @Validated JobReportForm form, BindingResult bindingResult,
			Principal principal, Model model) {
		

		// 追加処理実行
		jobReportService.insert(form, principal.getName());

//		log.info("[" + principal.getName() + "]さんが新しいユーザを登録しました。");
//		log.info("新規ユーザ情報：" + form.toString());
//		model.addAttribute("msg", "ユーザ情報の作成が無事完了しました。");
		
		System.out.println("報告新規作成処理");

		return getReportList(principal, model);
	}
}
