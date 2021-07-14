package jp.ac.hcs.GreenShower.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * ユーザ情報一覧画面を表示する - 処理失敗時：トップ画面を表示
	 * 
	 * @param principal ログイン情報
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
		return "user/userList";
	}
}
