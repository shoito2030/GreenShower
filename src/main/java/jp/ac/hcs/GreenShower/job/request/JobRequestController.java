package jp.ac.hcs.GreenShower.job.request;

import static java.util.stream.Collectors.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

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

import jp.ac.hcs.GreenShower.ai.ProofreadingData;
import jp.ac.hcs.GreenShower.ai.ProofreadingService;
import jp.ac.hcs.GreenShower.user.UserData;
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
	private ProofreadingService proofreadingService;

	@Autowired
	private HttpSession session;

	/**
	 * 就職活動申請申請一覧画面を表示する
	 * @param principal ログイン情報
	 * @param model viewに変数を渡す
	 * @return - 処理成功時：就職活動申請一覧画面 - 処理失敗時：就職活動申請トップ画面
	 */
	@GetMapping("/job/request/list")
	public String getRequestList(Principal principal, Model model) {

		Optional<JobRequestEntity> jobRequestEntity;
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		jobRequestEntity = jobRequestService.selectAllRequests(principal.getName(), role);

		// 処理失敗により就職活動申請トップ画面へ
		if (jobRequestEntity.isEmpty()) {
			return "index";
		}

		model.addAttribute("jobRequestEntity", jobRequestEntity.get());
		return "job/request/list";
	}

	/**
	 * 個人の申請情報を取得し就職活動申請詳細画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model viewに変数を渡す
	 * @return - 処理成功時：就職活動申請詳細画面 - 処理失敗時：就職活動申請一覧画面
	 */
	@GetMapping("/job/request/detail/{apply_id}")
	public String getRequestDetail(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {

		Optional<JobRequestData> jobRequestData;

		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");
		jobRequestData = jobRequestService.selectOne(apply_id, principal.getName(), role);

		// 処理失敗により就職活動申請一覧画面へ
		if (jobRequestData.isEmpty()) {
			return getRequestList(principal, model);
		}

		session.setAttribute("apply_id", apply_id);
		model.addAttribute("jobRequestData", jobRequestData.get());
		return "job/request/detail";
	}

	/**
	 * 個人の申請情報を取得し就職活動申請詳細画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param model viewに変数を渡す
	 * @return - 処理成功時：就職活動申請詳細画面 - 処理失敗時：就職活動申請一覧画面
	 */
	@PostMapping("/job/request/detail/notice")
	public String noticeCourseDirector(Principal principal, Model model) {
		String apply_id = (String)session.getAttribute("apply_id");

		boolean isSuccess = jobRequestService.hasNoticedCourseDirector(apply_id);

		// 処理失敗により就職活動申請詳細画面へ
		if (isSuccess) {
			model.addAttribute("msg", "申請の報告が完了しました。");
			return getRequestList(principal, model);

		} else {
			model.addAttribute("errmsg", "コース担当の先生に報告できませんでした");
			return getRequestDetail(principal, apply_id, model);
		}
	}

	/**
	 * 就職活動申請登録画面を表示する
	 * 
	 * @param form 登録時の入力チェック用JobRequestForm
	 * @param principal ログイン情報
	 * @param model viewに変数を渡す
	 * @return - 処理成功時：就職活動申請新規作成画面 - 処理失敗時：就職活動申請一覧画面
	 */
	@GetMapping("/job/request/insert")
	public String getRequestInsert(JobRequestForm form, Principal principal, Model model) {
		Optional<UserData> userData = null;

		// ユーザの情報を取得
		userData = jobRequestService.selectPersonalInfo(principal.getName());

		// 処理失敗により就職活動申請一覧画面へ
		if (userData.isEmpty()) {
			return getRequestList(principal, model);
		}

		// ユーザの権限を取得
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		// 自身の受け持つ生徒数を取得
		if (role.equals("ROLE_TEACHER")) {
			int studentsNumber = jobRequestService.selectStudentsNumber(principal.getName());

			// 出席番号のリストを作成
			List<Integer> classNumbers = IntStream.range(1, studentsNumber + 1).boxed().collect(toList());

			Map<String, String> classNumbersMap = new LinkedHashMap<String, String>();
			classNumbers.forEach(num -> {
				classNumbersMap.put(String.format("%02d", num), String.format("%02d", num));
			});

			// 担任が他クラスの生徒の申請を閲覧するのを防ぐために使用する
			session.setAttribute("classroom", userData.get().getClassroom());

			model.addAttribute("classNumbersMap", classNumbersMap);
		}
		model.addAttribute("role", role);
		model.addAttribute("userData", userData.get());
		return "job/request/insert";
	}

	/**
	 * 新たに就職活動申請情報を登録する
	 * 
	 * @param form          登録時の入力チェック用JobRequestForm
	 * @param bindingResult 入力情報の検証結果
	 * @param principal     ログイン情報
	 * @param model viewに変数を渡す
	 * @return - 入力内容誤り：就職活動申請新規作成画面 - 処理終了時（成否に関わらず）就職活動申請トップ画面
	 */
	@PostMapping("/job/request/insert")
	public String getJobRequestInsert(@ModelAttribute @Validated JobRequestForm form, BindingResult bindingResult,
			Principal principal, Model model) {

		// 入力内容に何らかの誤りがある場合
		if (bindingResult.hasErrors()) {
			model.addAttribute("errmsg", "申請の登録に失敗しました。");

			// 就職活動申請新規作成画面へ
			return getRequestInsert(form, principal, model);
		}

		// ユーザの権限を取得
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		// 処理結果を保持する
		boolean isSuccess = false;

		if (role.equals("ROLE_TEACHER")) {
			// JobApiController参照
			session.removeAttribute("classroom");
			String applicant_id = (String) session.getAttribute("applicant_id");

			isSuccess = jobRequestService.hasInserted(form, applicant_id, principal.getName());
		} else if (role.equals("ROLE_STUDENT")) {

			// ユーザが生徒の場合、登録者のユーザIDも当該生徒のものとなる
			isSuccess = jobRequestService.hasInserted(form, principal.getName(), principal.getName());
		}

		if (isSuccess) {
			model.addAttribute("msg", "申請の登録に成功しました。");
		} else {
			model.addAttribute("errmsg", "申請の登録に失敗しました。");
		}

		return "index";
	}

	/**
	 * 個人の申請情報を取得し就職活動申請管理画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model viewに変数を渡す
	 * @return - 処理成功時：就職活動申請管理画面 - 処理失敗時：就職活動申請一覧画面
	 */
	@GetMapping("/job/request/status_change/{apply_id}")
	public String getRequestStatusChange(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {
		Optional<JobRequestData> jobRequestData;

		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		// 個人の申請情報を取得
		jobRequestData = jobRequestService.selectOne(apply_id, principal.getName(), role);

		// 処理失敗により就職活動申請一覧画面へ
		if (jobRequestData.isEmpty()) {
			return getRequestList(principal, model);
		}

		if(jobRequestData.get().getStatus().getId().equals("99")) {
			model.addAttribute("errmsg", "取消済なので状態を変更できません。");
			return getRequestList(principal, model);
		}
		
		session.setAttribute("apply_id", apply_id);
		model.addAttribute("jobRequestData", jobRequestData.get());

		// AIサービスの呼び出し
		Optional<ProofreadingData> proofreadingData = proofreadingService
				.proofreading(jobRequestData.get().getRemark());

		// 値が入っていた（検査結果が黒）だった場合に実行
		if (!proofreadingData.isEmpty() && proofreadingData.get().getResultID() != null) {
			model.addAttribute("proofreadingData", proofreadingData.get());
			log.info("校正結果： " + proofreadingData.get());
		} else {
			model.addAttribute("proofreadingDataIsNotExsist", null);
		}
		return "job/request/status-change";
	}

	/**
	 * 就職活動申請状態変更処理を行う
	 * 
	 * @param form 登録時の入力チェック用JobRequestForm
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model viewに変数を渡す
	 * @return - 処理成功時：就職活動申請一覧画面 - 入力内容誤り：就職活動申請管理画面
	 */
	@PostMapping("/job/request/status-change/{apply_id}")
	public String JobRequestStatusChange(@PathVariable("apply_id") String apply_id, JobRequestForm form,
			Principal principal, Model model) {
		String status = jobRequestService.selectJobHuntingStatus(apply_id);

		if (form.getStatus().equals("1") && form.getIndicate().equals("")) {
			model.addAttribute("errmsg", "差し戻しの場合、指摘欄は必須です。");
			return getRequestStatusChange(principal, apply_id, model);

		} else if (form.getStatus().equals("3") && status.equals("1"))  {
			model.addAttribute("errmsg", "申請作成中なため、承認できません。");
			return getRequestStatusChange(principal, apply_id, model);
		} else if (form.getStatus().isEmpty()) {
			model.addAttribute("errmsg", "状態を選択してください。");
			return getRequestStatusChange(principal, apply_id, model);

		} else if (!(form.getStatus().equals("3") || form.getStatus().equals("1") || form.getStatus().equals("99"))) {
			model.addAttribute("errmsg", "改ざんしないでください。");
			return getRequestStatusChange(principal, apply_id, model);
		}
		
		if (form.getIndicate().length() > 254){
			model.addAttribute("errmsg", "指摘コメントを再入力してください");
			model.addAttribute("indicateErrmsg", "指摘コメントが長すぎます");
			return getRequestStatusChange(principal, apply_id, model);
		}

		boolean isSuccess = jobRequestService.hasUpdateJobStatus(apply_id, form);

		if (isSuccess) {
			model.addAttribute("msg", "申請状態の変更に成功しました。");
		} else {
			model.addAttribute("errmsg", "申請状態の変更に失敗しました。");
		}

		return getRequestList(principal, model);
	}

	/**
	 * 個人の申請情報を取得し就職活動申請修正画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model viewに変数を渡す
	 * @return 就職活動申請修正画面
	 */
	@GetMapping("/job/request/fix/{apply_id}")
	public String getRequestContentChange(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {

		Optional<JobRequestData> jobRequestData;

		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		// 個人の申請情報を取得
		jobRequestData = jobRequestService.selectOne(apply_id, principal.getName(), role);

		String status = jobRequestService.selectJobHuntingStatus(apply_id);

		// 状態が『申請完了』である場合
		if (status.equals("4")) {
			model.addAttribute("errmsg", "申請が完了されているので修正できません。");
			return getRequestList(principal, model);
			// 状態が『承認待ち』である場合
		} else if(status.equals("2")) {
			model.addAttribute("errmsg", "申請の承認待ちなので修正できません。");
			return getRequestList(principal, model);
			// 状態が『承認済』である場合
		} else if(status.equals("3")) {
			model.addAttribute("errmsg", "申請の承認済なので修正できません。");
			return getRequestList(principal, model);		
			// 状態が『取消済』である場合
		} else if(status.equals("99")) {
			model.addAttribute("errmsg", "取消済なので修正できません。");
			return getRequestList(principal, model);
		}

		// 処理失敗により就職活動申請一覧画面へ
		if (jobRequestData.isEmpty()) {
			return getRequestList(principal, model);
		}

		model.addAttribute("jobRequestData", jobRequestData.get());
		session.setAttribute("applicant_id", jobRequestData.get().getApplicant_id());
		return "job/request/fix";
	}

	/**
	 * 就職活動申請内容変更処理を行う
	 * 
	 * @param form 登録時の入力チェック用JobRequestForm
	 * @param bindingResult バインディング結果を表す 
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model viewに変数を渡す
	 * @return 就職活動申請一覧画面
	 */
	@PostMapping("/job/request/fix/{apply_id}")
	public String JobRequestContentChange(@PathVariable("apply_id") String apply_id, @ModelAttribute @Validated JobRequestForm form, BindingResult bindingResult, 
			Principal principal, Model model) {
		if(bindingResult.hasErrors()) {
			Optional<UserData> userData = null;
			String applicant_id = (String)session.getAttribute("applicant_id");

			// ユーザの情報を取得
			userData = jobRequestService.selectPersonalInfo(applicant_id);
			
			form.setClassroom(userData.get().getClassroom());
			form.setClass_number(userData.get().getClass_number());
			form.setName(userData.get().getName());
			
			model.addAttribute("jobRequestForm", form);
			return "job/request/fix";
		}
		boolean isSuccess = jobRequestService.hasUpdatedJobContent(apply_id, form);

		if (isSuccess) {
			model.addAttribute("msg", "申請内容の変更に成功しました。");
			jobRequestService.updateStatusFixed(apply_id);
		} else {
			model.addAttribute("errmsg", "申請内容の変更に失敗しました。");
		}
		return getRequestList(principal, model);
	}

	/**
	 * 就職活動イベント登録画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param model viewに変数を渡す
	 * @return 就職活動イベント登録画面
	 */
	@GetMapping("/job/event-registration")
	public String getRequestEvent_registration(Principal principal, Model model) {
		return "job/event-registration";

	}

	/**
	 * イベント情報登録処理を行う
	 * 
	 * @param form 登録時の入力チェック用JobRequestForm
	 * @param principal ログイン情報
	 * @param model viewに変数を渡す
	 * @return 就職活動トップ画面
	 */
	@PostMapping("/job/event-registration")
	public String getInsertEvent_registration(Principal principal, Model model, EventForm form) {
		jobRequestService.hasInsertedEvent(form, principal.getName());
		return "index";

	}
	
	/**
	 * 通知の取得を行う
	 * 
	 * @param principal ログイン情報
	 * @param model viewに変数を渡す
	 * @return 通知一覧画面
	 */
	@GetMapping("/job/request/notification")
	public String getNotificationList(Principal principal, Model model) {
		Optional<JobRequestEntity> jobRequestEntity;
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		jobRequestEntity = jobRequestService.selectAllNotfication(principal.getName(), role);
		
		List<JobRequestData> jobRequestList = jobRequestEntity.get().getJobRequestList();
		List<JobRequestData> jobRequestNotfiList = new ArrayList<JobRequestData>();
		
		//担任の通知を取得
		if(role.equals("ROLE_TEACHER")) {
			for(JobRequestData data: jobRequestList) {	
				switch(data.getStatus().getId()) {
					case "2":
					jobRequestNotfiList.add(data);
					break;
					case "6":
					jobRequestNotfiList.add(data);
					break;
				}
			}
		}
		//学生の通知を取得
		if(role.equals("ROLE_STUDENT")) {
			for(JobRequestData data: jobRequestList) {	
				switch(data.getStatus().getId()) {
					case "1":
					jobRequestNotfiList.add(data);
					break;
					case "3":
					jobRequestNotfiList.add(data);
					break;
					case "4":
					jobRequestNotfiList.add(data);
					break;
					case "5":
					jobRequestNotfiList.add(data);
					break;
				}
			}
		}
		model.addAttribute("jobRequestList", jobRequestNotfiList);
		return "job/request/notfication";
	}
}