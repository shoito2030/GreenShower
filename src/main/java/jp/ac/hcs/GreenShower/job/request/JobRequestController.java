package jp.ac.hcs.GreenShower.job.request;

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

	@Autowired
	private HttpSession session;

	/**
	 * 就職活動申請申請一覧画面を表示する - 処理失敗時：トップ画面を表示
	 * 
	 * @param model
	 * @return 就職活動申請申請一覧画面 or トップ画面
	 */
	@GetMapping("/job/request/list")
	public String getReportList(Principal principal, Model model) {

		Optional<JobRequestEntity> jobRequestEntity;
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");
		// 取得できなかった場合は空のOptionalが格納される
		if (role.equals("ROLE_STUDENT")) {
			jobRequestEntity = jobRequestService.selectStudentRequests(principal.getName());
		} else {
			jobRequestEntity = jobRequestService.selectAllRequests();
		}

		// 処理失敗によりトップ画面へ
		if (jobRequestEntity.isEmpty()) {
			return "index";
		}

		session.setAttribute("jobRequestEntity", jobRequestEntity.get());
		model.addAttribute("jobRequestEntity", jobRequestEntity.get());
		return "job/request/list";
	}

	/**
	 * 個人の申請情報を取得し就職活動申請詳細画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param apply_id 申請ID
	 * @param model
	 * @return 就職活動申請詳細画面
	 */
	@GetMapping("/job/request/detail/{apply_id}")
	public String getRequestDetail(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {
		JobRequestData sessionData = (JobRequestData) session.getAttribute(apply_id);
		
		// sessionに既に個人の申請情報が保存されているなら後続の処理は実行しない
		if(sessionData != null) {
			model.addAttribute("jobRequestData", sessionData);
			log.info("[" + sessionData.getApplicant_id() + " 申請ID:" + sessionData.getApply_id() + "]の申請情報取得済み");
			return "job/request/detail";
		}

		// sessionから申請情報の一覧を取得
		JobRequestEntity jobRequestEntity = (JobRequestEntity) session.getAttribute("jobRequestEntity");

		// sessionになければDBに問い合わせる(URL直貼り)
		if (jobRequestEntity == null) {
			jobRequestEntity = jobRequestService.selectAllRequests().get();
			log.warn("[" + principal.getName() + "]：URL直貼りアクセス");
		}

		// 合致する申請IDを持つ申請情報を取得
		Optional<JobHuntingData> jobRequestData = jobRequestEntity.getJobRequestList().stream()
				.filter(request -> request.getApply_id().equals(apply_id)).findAny();
		
		if(jobRequestData.isEmpty()) {
			return "index";
		}
		
		session.setAttribute("jobRequestEntity", jobRequestEntity);
		session.setAttribute(jobRequestData.get().getApply_id(), jobRequestData.get());
		
		model.addAttribute("jobRequestData", jobRequestData.get());
		return "job/request/detail";
	}
	
	/**
	 * 就職活動申請登録画面を表示する
	 * 
	 * @param model
	 * @return 就職活動申請登録画面
	 */
	@GetMapping("/job/request/insert")
	public String getRequestInsert(JobRequestForm form, Model model) {
		return "job/request/insert";
	}
	
	/**
	 * 新たに就職活動申請情報を登録する
	 * 
	 * @param form          登録時の入力チェック用JobRequestForm
	 * @param bindingResult 入力情報の検証結果
	 * @param principal     ログイン情報
	 * @param model
	 * @return 受験報告情報一覧画面
	 */
	@PostMapping("/job/request/insert")
	public String getJobRequestInsert(@ModelAttribute @Validated JobRequestForm form, BindingResult bindingResult,
			Principal principal, Model model) {
		jobRequestService.insert(form, principal.getName());
		return "index";
	}
	/**
	 * 個人の申請情報を取得し就職活動申請状態変更画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param apply_id 申請ID
	 * @param model 
	 * @return 就職活動申請状態変更画面
	 */
	@GetMapping("/job/request/status_change/{apply_id}")
	public String getRequestStatusChange(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {
		

		// sessionから申請情報の一覧を取得
		JobRequestEntity jobRequestEntity = (JobRequestEntity) session.getAttribute("jobRequestEntity");

		// sessionになければDBに問い合わせる(URL直貼り)
		if (jobRequestEntity == null) {
			jobRequestEntity = jobRequestService.selectAllRequests().get();
			log.warn("[" + principal.getName() + "]：URL直貼りアクセス");
		}

		// 合致する申請IDを持つ申請情報を取得
		Optional<JobHuntingData> jobRequestData = jobRequestEntity.getJobRequestList().stream()
				.filter(request -> request.getApply_id().equals(apply_id)).findAny();
		
		if(jobRequestData.isEmpty()) {
			return "index";
		}
		
		session.setAttribute("jobRequestEntity", jobRequestEntity);
		session.setAttribute(jobRequestData.get().getApply_id(), jobRequestData.get());
		
		model.addAttribute("jobRequestData", jobRequestData.get());
		return "job/request/status-change";
	}
	
	/**
	 * 就職活動申請状態変更処理を行う
	 * 
	 * @param principal ログイン情報
	 * @param apply_id 申請ID 
	 * @param model 
	 * @return 就職活動申請状態一覧画面
	 */
	@PostMapping("/job/request/ststus-change")
	public String JobRequestStatusChange(@PathVariable("apply_id") String apply_id,JobRequestForm form,Principal principal,Model model) {
		jobRequestService.updateJobStatus(apply_id,form);
		return "job/request/list";
	}
}