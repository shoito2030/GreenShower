package jp.ac.hcs.GreenShower.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PortalController {
	
	/**
	 * ルートアクセス時にメイン画面を表示する
	 * @return テンプレートファイル（index.html）
	 */
	@GetMapping("/")
	public String index() {
//		log.info("PortalController:" + user_id);
		return "index";
	}
}