package jp.ac.hcs.GreenShower.job.report;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import jp.ac.hcs.GreenShower.user.UserData;
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
	
	@Autowired
	private HttpSession session;
	
	
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
		
		jobReportEntity = jobReportService.selectAllReports(principal);
		
		
		// 処理失敗によりトップ画面へ
		if (jobReportEntity.isEmpty()) {
			return "index";
		} 

		model.addAttribute("jobReportEntity", jobReportEntity.get());
		return "job/report/list";
	}
	
	/**
	 * 個人の申請情報を取得し就職活動申請詳細画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param apply_id 申請ID
	 * @param model
	 * @return 就職活動申請詳細画面
	 */
	@GetMapping("/job/report/detail/{apply_id}")
	public String getReportDetail(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {
		Optional<JobHuntingData> jobReportData;
		
		jobReportData =  jobReportService.selectOne(apply_id);
		
		if(jobReportData.isEmpty()) {
			return getReportList(principal, model);
		}

		model.addAttribute("jobReportData", jobReportData.get());
		return "job/report/detail";
	}
	
	/**
	 * 就職活動報告新規作成画面を表示する
	 * 
	 * @param model
	 * @return 就職活動報告新規作成画面
	 */
	@GetMapping("/job/report/insert/{apply_id}")
	public String insertReportList(@PathVariable("apply_id") String apply_id, Principal principal, Model model) {
		Optional<UserData> userData;
		
		userData = jobReportService.selectPersonalInfo(apply_id);
		
		if(userData.isEmpty()) {
			return getReportList(principal, model);
		}
		
		// sessionに申請IDを保存
		session.setAttribute("apply_id", apply_id);
		
		model.addAttribute("userData", userData.get());
		
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
		
		// sessionから申請ID取得しセット
		form.setApply_id((String)session.getAttribute("apply_id"));
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("errmsg", "報告処理に失敗しました。");
			return getReportList(principal, model);
		}

		// 追加処理実行
		jobReportService.insert(form, principal.getName());

		log.info("報告新規作成処理：" + form.toString());
		model.addAttribute("msg", "報告が完了しました。");

		return getReportList(principal, model);
	}
	
	
	/**
	 * 就職活動報告修正画面を表示する
	 * 
	 * @param model
	 * @return 就職活動報告修正画面
	 */
	@GetMapping("/job/report/fix/{apply_id}")
	public String getReportFix(@PathVariable("apply_id") String apply_id, Principal principal, Model model) {
		Optional<UserData> userData;
		String id = (String)session.getAttribute("apply_id");
		
		// sessionに申請IDを保存
		session.setAttribute("apply_id", apply_id);
		
		return "/job/report/fix";
	}
	
	
	/**
	 * 就職活動報告を修正する
	 * 
	 * @param model
	 * @return 
	 */
	@GetMapping("/job/report/fix/")
	public String changeReportContent(Principal principal, Model model) {
		Optional<UserData> userData;
		String id = (String)session.getAttribute("apply_id");
		System.out.println("報告内容変更機能実行");
		
		return getReportList(principal, model);
	}
}
