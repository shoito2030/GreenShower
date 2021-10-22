package jp.ac.hcs.GreenShower.job;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class JobController {
	/**
	 * ルートアクセス時にメイン画面を表示する
	 * @return テンプレートファイル（index.html）
	 */
	@GetMapping("/csv")
	public String index() {
		log.info("JobController:CSV出力機能起動");
		return "index";
	}
}
