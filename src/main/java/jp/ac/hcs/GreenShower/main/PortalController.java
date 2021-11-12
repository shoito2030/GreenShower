package jp.ac.hcs.GreenShower.main;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.ac.hcs.GreenShower.user.UserData;

@Controller
public class PortalController {

	@Autowired
	LoginService loginService;

	/**
	 * トップ画面を表示する.
	 * @param principal
	 * @return トップ画面
	 */
	@RequestMapping("/")
	public String index(Principal principal) {

		UserData userData = loginService.getLoginUserInfo(principal);
		if (loginService.judgeUserStatus(userData)) {
			// OK
			return "index";
		} else {
			// NG
			return "login";
		}

	}

}