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
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * ユーザ情報一覧画面を表示する - 処理失敗時：トップ画面を表示
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
	public String getUserInsert(UserForm form, Model model) {
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
	public String getUserInsert(@ModelAttribute @Validated UserForm form, BindingResult bindingResult,
			Principal principal, Model model) {

		// 入力チェックに引っかかった場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			log.info("[" + principal.getName() + "]さんが新しいユーザの作成に失敗しました。");
			log.info("入力情報：" + form.toString());

			model.addAttribute("errmsg", "ユーザ情報の作成に失敗しました。入力内容をお確かめください。");

			return getUserInsert(form, model);
		}

		// 追加処理実行
		userService.insert(form);

		log.info("[" + principal.getName() + "]さんが新しいユーザを作成しました。");
		log.info("新規ユーザ情報：" + form.toString());

		model.addAttribute("msg", "ユーザ情報の作成が無事完了しました。");

		return getUserList(model);
	}
}
