package jp.ac.hcs.GreenShower.job.report;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/** 
 * 就職活動報告に関する処理を行うControllerクラス
 * 
 */
@Slf4j
@Controller
public class JobReportController {
	
	/**
	 * 就職活動申請報告一覧画面を表示する - 処理失敗時：トップ画面を表示
	 * 
	 * @param model
	 * @return 就職活動申請報告一覧画面 or トップ画面
	 */
	@GetMapping("/job/report/list")
	public String getReportList(Principal principal, Model model) {

//	// 取得できなかった場合は空のOptionalが格納される
//	Optional<JobRequestEntity> jobRequestEntity = jobRequestService.selectAllRequests();
//
//		// 処理失敗によりトップ画面へ
//		if (jobRequestEntity.isEmpty()) {
//			return "index";
//		} 
//		
//		model.addAttribute("jobRequestEntity", jobRequestEntity.get());
		return "job/report/list";
	}
}
