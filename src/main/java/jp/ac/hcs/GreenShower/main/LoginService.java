package jp.ac.hcs.GreenShower.main;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ac.hcs.GreenShower.user.UserData;
import jp.ac.hcs.GreenShower.user.UserService;
import jp.ac.hcs.GreenShower.user.UserStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * ユーザ情報を操作する.
 */
@Slf4j
@Transactional
@Service
public class LoginService {

	@Autowired
	UserService userService;

	@Autowired
	HttpSession session;

	/**
	 * ログインしたユーザのユーザ情報を取得する.
	 * @param principal ログイン情報
	 * @return ログインユーザのユーザ情報
	 */
	public UserData getLoginUserInfo(Principal principal) {
		UserData userData = userService.selectOne(principal.getName()).get();
		return userData;
	}

	/**
	 * ユーザ状態を確認し、ログイン可能か判定する
	 * @param userData ユーザ情報
	 * @return ログイン可能:true, ログイン不可:false
	 */
	public boolean judgeUserStatus(UserData userData) {
		// ユーザロック判定
		if (userData.getUser_status() > UserStatus.VALID.getCode()) {
			log.info("[ユーザロック]ユーザ:" + userData.getUser_id()
					+ ", user_status:" + userData.getUser_status()
					+ ", number_of_trials:" + userData.getNumber_of_trials());

			// ログイン画面のユーザロックメッセージを設定
			session.setAttribute("UserLocked", "ユーザがロックまたは無効になっています。管理者に連絡してください。");
			return false;
		}

		// パスワードエラー回数をリセット
		userData.setNumber_of_trials(0);
		userService.updateOne(userData);

		log.info("[ログイン成功]ユーザ:" + userData.getUser_id());

		return true;
	}

}
