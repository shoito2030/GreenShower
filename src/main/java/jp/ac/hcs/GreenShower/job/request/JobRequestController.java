package jp.ac.hcs.GreenShower.job.request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.ac.hcs.GreenShower.report.ReportEntity;
import lombok.extern.slf4j.Slf4j;

/** 
 * 就職活動申請に関する処理を行うControllerクラス
 * 
 */
@Slf4j
@Controller
public class JobRequestController {
	@Autowired
	private JobRequestService jobRequestService;
	
	/**
	 * 就職活動申請一覧画面を表示する - 処理失敗時：トップ画面を表示
	 * 
	 * @param model
	 * @return 就職活動申請一覧画面 or トップ画面
	 */
	@GetMapping("/job/request/list")
	public String getJobRequestList(Model model) {

	// 取得できなかった場合は空のOptionalが格納される
	Optional<ReportEntity> jobRequesttEntity = jobRequestService.selectAll();

		// 処理失敗によりトップ画面へ
		if (jobRequesttEntity.isEmpty()) {
			return "index";
		}

		model.addAttribute("jobRequesttEntity", jobRequesttEntity.get());
		return "job/request/list";
	}
}
