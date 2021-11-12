package jp.ac.hcs.GreenShower.main;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import jp.ac.hcs.GreenShower.user.UserData;
import jp.ac.hcs.GreenShower.user.UserService;
import jp.ac.hcs.GreenShower.user.UserStatus;
import lombok.extern.slf4j.Slf4j;

/**
 *  認証に関するイベントを制御する
 */
@Slf4j
@Component
public class AuthenticationEventListeners {

	/** ロックを行うパスワード誤りの回数 */
	private static final int ERROR_LIMIT = 3;

	@Autowired
	UserService userService;

	@Autowired
	HttpSession session;

	/**
	 * ログイン失敗時イベント
	 * @param event ログイン失敗時
	 */
	@EventListener
	public void loginFailureEventHandler(AuthenticationFailureBadCredentialsEvent event) {

		// 前回のロックメッセージを削除
		session.setAttribute("UserLocked", null);

		String username = (String) event.getAuthentication().getPrincipal();
		//log.info("[ログイン失敗]入力ユーザ名:" + username);

		try {
			UserData userData = userService.selectOne(username).get();

			// パスワードエラー回数をインクリメント
			userData.setNumber_of_trials(userData.getNumber_of_trials() + 1);
			// ロック
			if (userData.getNumber_of_trials() >= ERROR_LIMIT) {
				userData.setUser_status(UserStatus.LOCKED.getCode());
			}
			userService.updateOne(userData);

			log.info("[ログイン失敗]入力ユーザ:" + username
					+ ", user_status:" + userData.getUser_status()
					+ ", number_of_trials:" + userData.getNumber_of_trials());

		} catch (IndexOutOfBoundsException e) {
			log.info("[ログイン失敗]入力ユーザ:" + username + ", 存在しないユーザ");
		}
	}
}
