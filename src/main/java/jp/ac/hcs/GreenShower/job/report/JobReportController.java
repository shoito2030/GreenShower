package jp.ac.hcs.GreenShower.job.report;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import jp.ac.hcs.GreenShower.job.request.JobRequestEntity;
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
	@GetMapping("/job/report/insert/{apply_id}")
	public String insertReportList(@PathVariable("apply_id") String apply_id, Principal principal, Model model) {
		
		JobRequestEntity jobRequestEntity = (JobRequestEntity) session.getAttribute("jobRequestEntity");
		
		Optional<JobHuntingData> jobRequestData = jobRequestEntity.getJobRequestList().stream()
				.filter(request -> request.getApply_id().equals(apply_id)).findAny();
		
		System.out.println("報告新規作成画面" + jobRequestData.get().getApply_id() + ", " +  jobRequestData.get().getClassroom() + ", " +  jobRequestData.get().getClass_number()
				+ ", " +  jobRequestData.get().getName());
		
		session.setAttribute(jobRequestData.get().getApply_id(), jobRequestData.get());
		System.out.println(jobRequestData.get());
		
		model.addAttribute("jobRequestData", jobRequestData.get());
		
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
		
		
		System.out.println("報告新規作成処理");

		return getReportList(principal, model);
	}
}
