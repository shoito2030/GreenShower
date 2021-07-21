package jp.ac.hcs.GreenShower.user;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * ユーザ情報一覧画面を表示する - 処理失敗時：トップ画面を表示
	 * 
	 * @param model
	 * @return ユーザ情報一覧画面 or トップ画面
	 */
	@GetMapping("/user/list")
	public String getUserList(Model model) {

		// 取得できなかった場合は空のOptionalが格納される
		Optional<UserEntity> userEntity = userService.selectAll();

		// 処理失敗によりトップ画面へ
		if (userEntity.isEmpty()) {
			return "index";
		}

		model.addAttribute("userEntity", userEntity.get());
		return "user/list";
	}

	/**
	 * ユーザ登録画面（管理者用）を表示する
	 * 
	 * @param form  登録時の入力チェック用UserForm
	 * @param model
	 * @return ユーザ登録画面（管理者用）
	 */
	@GetMapping("/user/insert")
	public String getUserInsert(UserFormForInsert form, Model model) {
		return "user/insert";
	}

	/**
	 * 新たにユーザ情報を追加する
	 * 
	 * @param form          入力情報
	 * @param bindingResult 入力情報の検証結果
	 * @param principal     ログイン情報
	 * @param model
	 * @return - 処理失敗時：ユーザ登録画面（管理者用） - 処理成功時：getUserListに処理を委譲しているのでそちらを参照すること
	 */
	@PostMapping("user/insert")
	public String getUserInsert(@ModelAttribute @Validated UserFormForInsert form, BindingResult bindingResult,
			Principal principal, Model model) {

		// 入力チェックに引っかかった場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			log.info("[" + principal.getName() + "]さんが新しいユーザの登録に失敗しました。");
			log.info("入力情報：" + form.toString());

			model.addAttribute("errmsg", "ユーザ情報の登録に失敗しました。入力内容をお確かめください。");

			return getUserInsert(form, model);
		}

		// 追加処理実行
		userService.insert(form, principal.getName());

		log.info("[" + principal.getName() + "]さんが新しいユーザを登録しました。");
		log.info("新規ユーザ情報：" + form.toString());

		model.addAttribute("msg", "ユーザ情報の作成が無事完了しました。");

		return getUserList(model);
	}

	/**
	 * ユーザ詳細情報画面を表示する
	 * 
	 * @param user_id   検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return - 処理成功時：ユーザ詳細情報画面 - 処理失敗時：トップ画面
	 */
	@GetMapping("user/detail/{id}")
	public String getUserDetail(@PathVariable("id") String user_id, Principal principal, Model model) {

		// ユーザIDに紐づく情報を取得（取得できなかった場合は空のOptionalが格納される）
		Optional<UserData> userData = userService.select(user_id);

		// 処理失敗によりトップ画面へ
		if (userData.isEmpty()) {
			return "index";
		}

		model.addAttribute("userData", userData.get());

		return "user/detail";
	}

	/**
	 * ユーザIDに紐づく登録情報を削除する
	 * 
	 * @param user_id   ユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return getUserListに処理を委譲しているのでそちらを参照すること
	 */
	@PostMapping("user/delete")
	public String deleteUser(@RequestParam(name = "user_id") String user_id, Principal principal, Model model) {
		// 実行結果を取得
		boolean isSuccess = userService.delete(user_id);

		if (isSuccess) {
			model.addAttribute("msg", "正常に削除されました");
			log.info("[" + principal.getName() + "]さんが[" + user_id + "]さんのユーザ情報の削除に成功しました。");
		} else {
			model.addAttribute("errmsg", "削除出来ませんでした。再度手順をやり直してください");
			log.info("[" + principal.getName() + "]さんがユーザ情報の削除に失敗しました。");
			log.info("削除対象：" + user_id + "さん");
		}

		return getUserList(model);
	}

	/**
	 * ユーザ情報を更新する
	 * 
	 * @param form          入力情報
	 * @param bindingResult 入力情報の検証結果
	 * @param principal     ログイン情報
	 * @param model
	 * @return getUserListに処理を委譲しているのでそちらを参照すること
	 */
	@PostMapping("user/update")
	public String updateUser(@ModelAttribute @Validated UserFormForUpdate form, BindingResult bindingResult,
			Principal principal, Model model) {

		// 入力チェックに引っかかった場合、後続の処理を実行しない
		if (bindingResult.hasErrors()) {
			log.info("データ挿入失敗：" + form.toString());
			return getUserList(model);
		}

		log.info("入力情報：" + form.toString());

		// 実行結果を取得
		boolean isSuccess = userService.updateForAdmin(form, principal.getName());

		if (isSuccess) {
			log.info("[" + principal.getName() + "]さんが[" + form.getName() + "]さんのユーザ情報の変更に成功");
		} else {
			log.warn("[" + principal.getName() + "]さんが[" + form.getName() + "]さんのユーザ情報の変更に失敗");
			model.addAttribute("errmsg", "変更出来ませんでした。再度手順をやり直してください");
		}

		return getUserList(model);
	}
}
