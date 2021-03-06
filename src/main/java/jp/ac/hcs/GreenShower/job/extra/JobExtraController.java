package jp.ac.hcs.GreenShower.job.extra;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ac.hcs.GreenShower.job.request.JobRequestController;

/**
 * 就職活動申請のとりまとめ機能に関する処理を行うControllerクラス
 * 
 */
@Controller
public class JobExtraController {

	@Autowired
	private HttpSession session;
	@Autowired
	private JobRequestController jobRequestController;
	@Autowired
	private JobExtraService jobExtraService;
	
	/**
	 * 新たに学生をとりまとめ名簿に登録した旨を登録する
	 * 
	 * @param principal   ログイン情報
	 * @param model       viewへ変数を渡す
	 * @return - 就職活動申請一覧画面
	 */
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
	
	/**
	 * 就職活動に必要な書類を受け取った際にその日時を登録する
	 * @param form          JobExtraData
	 * @param principal     ログイン情報
	 * @param model         viewへ変数を渡す
	 * @return - 就職活動申請一覧画面
	 */
	@PostMapping("/job/request/status-change/document-receipt")
	public String documentReceirt(JobExtraForm form,Principal principal,Model model) {
		String apply_id = (String)session.getAttribute("apply_id");

		boolean isSuccess = jobExtraService.documentReceirt(form,apply_id,principal.getName());
//		jobExtraData = jobExtraService.selectSummaryDocuments(apply_id);
		
		// 処理失敗により就職活動申請詳細画面へ
		if (isSuccess) {
			model.addAttribute("msg", "書類の受取登録が完了しました。");
//			model.addAttribute("jobExtraData",jobExtraData.get());
			return jobRequestController.getRequestList(principal, model);

		} else {
			model.addAttribute("errmsg", "書類の受取登録に失敗しました");
//			model.addAttribute("jobExtraData",jobExtraData.get());
			return jobRequestController.getRequestList(principal, model);
		}
		
	}
}
