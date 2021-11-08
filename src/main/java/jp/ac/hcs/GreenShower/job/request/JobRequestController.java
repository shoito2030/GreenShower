package jp.ac.hcs.GreenShower.job.request;

import static java.util.stream.Collectors.*;

import java.security.Principal;
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
	private HttpSession session;

	/**
	 * 就職活動申請申請一覧画面を表示する - 処理失敗時：トップ画面を表示
	 * 
	 * @param model
	 * @return 就職活動申請申請一覧画面 or トップ画面
	 */
	@GetMapping("/job/request/list")
	public String getRequestList(Principal principal, Model model) {

		Optional<JobRequestEntity> jobRequestEntity;
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		jobRequestEntity = jobRequestService.selectAllRequests(principal.getName(), role);

		// 処理失敗によりトップ画面へ
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
	 * @param model
	 * @return 就職活動申請詳細画面
	 */
	@GetMapping("/job/request/detail/{apply_id}")
	public String getRequestDetail(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {

		Optional<JobRequestData> jobRequestData;

		jobRequestData = jobRequestService.selectOne(apply_id);
		System.out.println("就職活動申請 詳細画面表示: " + apply_id);
		if (jobRequestData.isEmpty()) {
			return "index";
		}

		model.addAttribute("jobRequestData", jobRequestData.get());
		return "job/request/detail";
	}

	/**
	 * 就職活動申請登録画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param model
	 * @return 就職活動申請登録画面
	 */
	@GetMapping("/job/request/insert")
	public String getRequestInsert(Principal principal, Model model) {
		Optional<UserData> userData = null;

		// ユーザの情報を取得
		userData = jobRequestService.selectPersonalInfo(principal.getName());

		if (userData.isEmpty()) {
			return getRequestList(principal, model);
		}

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

		model.addAttribute("userData", userData.get());
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

		if (bindingResult.hasErrors()) {
			log.info("申請の登録に失敗しました");
			model.addAttribute("errmsg", "申請の登録に失敗しました");
			return getRequestInsert(principal, model);
		}

		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		if (role.equals("ROLE_TEACHER")) {
			// JobApiController参照
			session.removeAttribute("classroom");
			String applicant_id = (String) session.getAttribute("applicant_id");

			jobRequestService.insert(form, applicant_id, principal.getName());
		} else if (role.equals("ROLE_STUDENT")) {

			// ユーザが生徒の場合、登録者のユーザIDも当該生徒のものとなる
			jobRequestService.insert(form, principal.getName(), principal.getName());
		}

		System.out.println(form);

		return "index";
	}

	/**
	 * 個人の申請情報を取得し就職活動申請状態変更画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model
	 * @return 就職活動申請状態変更画面
	 */
	@GetMapping("/job/request/status_change/{apply_id}")
	public String getRequestStatusChange(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {

		// sessionから申請情報の一覧を取得
		Optional<JobRequestData> jobRequestData;

		jobRequestData = jobRequestService.selectOne(apply_id);

		if (jobRequestData.isEmpty()) {
			return "index";
		}

		model.addAttribute("jobRequestData", jobRequestData.get());
		return "job/request/status-change";
	}

	/**
	 * 就職活動申請状態変更処理を行う
	 * 
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model
	 * @return トップ画面
	 */
	@PostMapping("/job/request/status-change/{apply_id}")
	public String JobRequestStatusChange(@PathVariable("apply_id") String apply_id, JobRequestForm form,
			Principal principal, Model model) {
		System.out.println(apply_id);
		jobRequestService.updateJobStatus(apply_id, form);
		return "index";
	}

	/**
	 * 個人の申請情報を取得し就職活動申請内容変更画面を表示する
	 * 
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model
	 * @return 就職活動申請内容変更画面
	 */
	@GetMapping("/job/request/fix/{apply_id}")
	public String getRequestContentChange(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {

		// sessionから申請情報の一覧を取得
		Optional<JobRequestData> jobRequestData;

		jobRequestData = jobRequestService.selectOne(apply_id);

		if (jobRequestData.isEmpty()) {
			return "index";
		}

		model.addAttribute("jobRequestData", jobRequestData.get());
		return "job/request/fix";
	}

	/**
	 * 就職活動申請内容変更処理を行う
	 * 
	 * @param principal ログイン情報
	 * @param apply_id  申請ID
	 * @param model
	 * @return トップ画面
	 */
	@PostMapping("/job/request/fix/{apply_id}")
	public String JobRequestContentChange(@PathVariable("apply_id") String apply_id, JobRequestForm form,
			Principal principal, Model model) {
		jobRequestService.updateJobContent(apply_id, form);
		return "index";
	}
	
	/**
	 * イベント情報登録画面を表示する
	 * @param principal
	 * @param model
	 * @return
	 */
	@GetMapping("/job/event-registration")
	public String getRequestEvent_registration(Principal principal, Model model) {
		return "job/event-registration";

	}
	
	/**
	 * イベント情報登録処理を行う
	 * @param principal
	 * @param model
	 * @return
	 */
	@PostMapping("/job/event-registration")
	public String getInsertEvent_registration(Principal principal, Model model, EventForm form) {
		jobRequestService.insertEvent(form, principal.getName());
		return "index";

	}
}