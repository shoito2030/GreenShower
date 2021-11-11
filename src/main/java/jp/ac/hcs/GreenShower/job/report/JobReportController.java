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

import jp.ac.hcs.GreenShower.ai.ProofreadingData;
import jp.ac.hcs.GreenShower.ai.ProofreadingService;
import jp.ac.hcs.GreenShower.job.request.JobRequestController;
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
	private ProofreadingService proofreadingService;
	
	@Autowired
	private JobRequestController jobRequestController;

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

		// 取得できなかった場合は空のOptionalが格納される
		Optional<JobReportEntity> jobReportEntity;

		// アクターの権限を取得
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		jobReportEntity = jobReportService.selectAllReports(principal.getName(), role);

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
	 * @param apply_id  申請ID
	 * @param model
	 * @return 就職活動申請詳細画面
	 */
	@GetMapping("/job/report/detail/{apply_id}")
	public String getReportDetail(Principal principal, @PathVariable("apply_id") String apply_id, Model model) {
		Optional<JobReportData> jobReportData;

		jobReportData = jobReportService.selectOne(apply_id);

		if (jobReportData.isEmpty()) {
			return getReportList(principal, model);
		}

		session.setAttribute("apply_id", apply_id);
		log.info("申請報告詳細画面 jobReportData: " + jobReportData.get().toString());

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
	public String getReportInert(@PathVariable("apply_id") String apply_id, Principal principal, Model model) {
		String status = jobReportService.selectJobHuntingStatus(apply_id);

		// 状態が『申請完了』ではない場合
		if (status == null || Integer.parseInt(status) != 4) {
			model.addAttribute("errmsg", "申請が完了されていません。");
			return jobRequestController.getRequestList(principal, model);
		}

		Optional<UserData> userData;

		userData = jobReportService.selectPersonalInfoApply(apply_id);

		if (userData.isEmpty()) {
			return getReportList(principal, model);
		}

		// 報告処理時に不正な値が使用されてしまうのを防ぐために使用
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
		form.setApply_id((String) session.getAttribute("apply_id"));

		if (bindingResult.hasErrors()) {
			model.addAttribute("errmsg", "報告処理に失敗しました。");
			session.removeAttribute("apply_id");
			return getReportList(principal, model);
		}

		// 追加処理実行
		jobReportService.insert(form, principal.getName());

		log.info("[" + "申請ID：" + session.getAttribute("apply_id") + "]" + " 報告新規登録実行");
		session.removeAttribute("apply_id");

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
		Optional<JobReportData> jobReportData;

		// 申請IDに紐づく報告情報を取得
		jobReportData = jobReportService.selectOne(apply_id);

		if (jobReportData.isEmpty()) {
			return getReportList(principal, model);
		}

		// sessionに申請IDを保存
		session.setAttribute("apply_id", apply_id);

		model.addAttribute("jobReportData", jobReportData.get());

		return "/job/report/fix";
	}

	/**
	 * 就職活動報告を修正する
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping("/job/report/fix")
	public String fixReportContent(Principal principal, Model model, @ModelAttribute @Validated JobReportForm form,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("msg", "報告内容の変更に失敗しました。");
			return getReportList(principal, model);
		}

		String apply_id = (String) session.getAttribute("apply_id");
		form.setApply_id(apply_id);

		Optional<JobReportData> jobReportData;

		jobReportData = jobReportService.selectOne(apply_id);

		if (jobReportData.isEmpty()) {
			model.addAttribute("msg", "報告内容の変更に失敗しました。");
			return getReportList(principal, model);
		}

		// 入力内容がDBの情報と変わらない場合は実行しない
		if (jobReportData.get().isAdvance_or_retreat() != form.isAdvance_or_retreat()) {
			jobReportService.updateAdvance_or_retreat(form);
		}

		if (!(jobReportData.get().getRemark().equals(form.getRemark()))) {
			jobReportService.updateRemark(form);
		}

		session.removeAttribute("apply_id");

		log.info("[" + "申請ID：" + apply_id + "]" + " 報告内容変更機能実行");
		model.addAttribute("msg", "報告内容の変更が完了しました。");

		return getReportList(principal, model);
	}

	/**
	 * 就職活動報告承認画面を表示する
	 * 
	 * @param model
	 * @return 就職活動報告承認画面
	 */
	@GetMapping("/job/report/status_change/{apply_id}")
	public String getReportStatus(@PathVariable("apply_id") String apply_id, Principal principal, Model model) {

		Optional<JobReportData> jobReportData;

		jobReportData = jobReportService.selectOne(apply_id);

		if (jobReportData.isEmpty()) {
			return getReportList(principal, model);
		}

		// AIサービスの呼び出し
		Optional<ProofreadingData> proofreadingData = proofreadingService.proofreading(jobReportData.get().getRemark());

		// 値が入っていた（検査結果が黒）だった場合に実行
		if (!proofreadingData.isEmpty()) {
			model.addAttribute("proofreadingData", proofreadingData.get());
			log.info("校正結果： " + proofreadingData.get());
		}

		model.addAttribute("jobReportData", jobReportData.get());

		// sessionに申請IDを保存
		session.setAttribute("apply_id", apply_id);

		return "/job/report/status-change";
	}

	/**
	 * 報告の状態を任意の状態に変更する
	 * 
	 * @param form          登録時の入力チェック用JobReportForm
	 * @param bindingResult 入力情報の検証結果
	 * @param principal     ログイン情報
	 * @param model
	 * @return 受験報告情報一覧画面
	 */
	@PostMapping("job/report/status_change")
	public String getJobReportStatus(@ModelAttribute @Validated JobReportForm form, BindingResult bindingResult,
			Principal principal, Model model) {

		// sessionから申請ID取得しセット
		form.setApply_id((String) session.getAttribute("apply_id"));

		if (form.getStatus().equals("5") && form.getIndicate().equals("")) {
			model.addAttribute("errmsg", "差し戻しの場合、備考は必須です。");
			return getReportStatus(form.getApply_id(), principal, model);
		} else if (form.getStatus().isEmpty()) {
			model.addAttribute("errmsg", "状態を選択してください");
			return getReportStatus(form.getApply_id(), principal, model);
		} else if (!(form.getStatus().equals("5") || form.getStatus().equals("7") || form.getStatus().equals("99"))) {
			model.addAttribute("errmsg", "改ざんしないでください。");
			return getReportStatus(form.getApply_id(), principal, model);
		}

		// 報告状態変更処理実行
		jobReportService.updateStatus(form);

		log.info("報告状態変更処理： [申請ID:" + form.getApply_id() + "・状態:" + form.getStatus() + "・備考:" + form.getIndicate()
				+ "]");
		model.addAttribute("msg", "状態変更が完了しました。");

		return getReportList(principal, model);
	}
}