package jp.ac.hcs.GreenShower.job.api;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.ac.hcs.GreenShower.job.request.JobRequestService;
import jp.ac.hcs.GreenShower.user.UserData;
import lombok.extern.slf4j.Slf4j;

/**
 * APIリクエストを受け取るControllerクラス
 * 
 */
@Slf4j
@RestController
public class JobApiController {
	
	@Autowired
	private JobApiService jobApiService;

	@Autowired
	private JobRequestService jobRequestService;
	
	@Autowired
	private HttpSession session;

	/**
	 * リクエストを受けユーザを検索する
	 * @param json ユーザの所属クラスと出席番号のjson
	 * @param principal ログイン情報
	 * @return userData ユーザの氏名、クラス、番号
	 */
	@RequestMapping("/job/request/insert/getUserInfo")
	 public List<UserData> searchUser(Principal principal, @RequestParam(value="json") String json) {
		log.info("json = " + json);
		
		// jsonをマップに変換
		Map<String, Object> map = jobApiService.jsonToMap(json);
		
		if(String.valueOf(map.get("class_number")).equals("zero")) {
			log.info("[" + principal.getName() + "]: 番号『0』を選択");
			return null;
		}
		
		String class_room = String.valueOf(map.get("classroom"));
		String class_number = String.valueOf(map.get("class_number"));
		
		UserData userData = null;

		// 本来の申請者の情報を取得
		userData = jobRequestService.selectPersonalInfo(class_room, class_number);
		log.info("ユーザ検索API検索結果: " + userData);
		
		// 担任が不正な操作をしていないかチェック
		if(!session.getAttribute("classroom").equals(userData.getClassroom())) {
			log.info("[" + principal.getName() + "]: 担当クラスではない学生の申請を閲覧しようとしました");
			return null;
		}
		
		// 新規登録処理で本来の申請者IDを使用するため
		session.setAttribute("applicant_id", userData.getUser_id());

		return Arrays.asList(userData);
	}
}
