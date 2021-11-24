package jp.ac.hcs.GreenShower.job.extra;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ac.hcs.GreenShower.job.request.JobRequestController;
import jp.ac.hcs.GreenShower.job.request.JobRequestForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class JobExtraController {

	@Autowired
	private HttpSession session;
	@Autowired
	private JobRequestController jobRequestController;
	@Autowired
	private JobExtraService jobExtraService;
	
	@PostMapping("/job/request/status-change/list-registration")
	public String listRegistion(Principal principal,Model model) {
		String apply_id = (String)session.getAttribute("apply_id");

		boolean isSuccess = jobExtraService.listRegistion(apply_id,principal.getName());

		// 処理失敗により就職活動申請詳細画面へ 
		if (isSuccess) {
			model.addAttribute("msg", "学生の名簿登録が完了しました。");
			return jobRequestController.getRequestList(principal, model);

		} else {
			model.addAttribute("errmsg", "学生の名簿登録に失敗しました");
			return jobRequestController.getRequestList(principal, model);
		}
		
	}
	
	@PostMapping("/job/request/status-change/document-receipt")
	public String documentReceirt(JobRequestForm form,Principal principal,Model model) {
		String apply_id = (String)session.getAttribute("apply_id");

		boolean isSuccess = jobExtraService.listRegistion(apply_id,principal.getName());

		// 処理失敗により就職活動申請詳細画面へ
		if (isSuccess) {
			model.addAttribute("msg", "書類の受取登録が完了しました。");
			return jobRequestController.getRequestList(principal, model);

		} else {
			model.addAttribute("errmsg", "書類の受取登録に失敗しました");
			return jobRequestController.getRequestList(principal, model);
		}
		
	}
}
