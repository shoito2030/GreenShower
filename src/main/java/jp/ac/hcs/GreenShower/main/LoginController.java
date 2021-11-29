package jp.ac.hcs.GreenShower.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ログインに関する機能・画面を制御する
 */
@Controller
public class LoginController {
	
	/**
	 * ログイン画面を表示する
	 * @param model 変数を渡す
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String getLogin(Model model) {
		return "login";
	}

}
