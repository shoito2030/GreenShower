package jp.ac.hcs.GreenShower.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jp.ac.hcs.GreenShower.user.UserData.Role;

/**
 * ユーザ情報のデータを管理する. - usersテーブル
 */

@Repository
public class UserRepository {

	/** SQL 全件取得（ユーザID昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM users order by user_id";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM users WHERE user_id = ?";

	/** SQL 1件追加 enabled追加 */
	private static final String SQL_INSERT_ONE = 
			"INSERT INTO users (user_id, encrypted_password, name, role, classroom, class_number, register_user_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
	
	/** SQL 1件更新 管理者実行用 */
	private static final String SQL_UPDATE_ONE_FOR_ADMIN = 
			"UPDATE users SET name = ?, role = ?, classroom = ?, class_number = ?, enabled = ?, update_date = ?,  update_user_id = ? WHERE user_id = ?";
	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM users WHERE user_id = ?";
	
	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_ONE = "UPDATE users SET name = ?, role = ?, number_of_trials = ?, user_status = ? WHERE user_id = ?";

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * usersテーブルから全データを取得.
	 * 
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public UserEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	/**
	 * usersテーブルからユーザIDをキーにデータを1件を取得.
	 * @param user_id 検索するユーザID
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public UserData selectOne(String user_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, user_id);
		UserEntity entity = mappingSelectResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		UserData data = entity.getUserlist().get(0);
		return data;
	}

	/**
	 * usersテーブルから取得したデータをUserEntity形式にマッピングする.
	 * 
	 * @param resultList Userテーブルから取得したデータ
	 * @return UserEntity
	 */
	private UserEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		UserEntity entity = new UserEntity();

		for (Map<String, Object> map : resultList) {
			UserData data = new UserData();
			data.setUser_id((String) map.get("user_id"));
			data.setEncrypted_password((String) map.get("encrypted_password"));
			data.setName((String) map.get("name"));
			data.setDark_mode((boolean) map.get("dark_mode"));
			data.setRole(Role.valueOf((String) map.get("role")));
			data.setClassroom((String) map.get("classroom"));
			data.setClass_number((String) map.get("class_number"));
			data.setRegister_date((Date) map.get("register_date"));
			data.setRegister_user_id((String) map.get("register_user_id"));
			data.setUpdate_date((Date) map.get("update_date"));
			data.setUpdate_user_id((String) map.get("update_user_id"));
			data.setEnabled((boolean) map.get("enabled"));
			data.setNumber_of_trials((int)map.get("number_of_trials"));		
			data.setUser_status((int)map.get("user_status"));
			entity.getUserlist().add(data);
		}
		return entity;
	}

	/**
	 * usersテーブルにデータを1件追加する.
	 * 
	 * @param data 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int insertOne(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE, 
				data.getUser_id(),
				passwordEncoder.encode(data.getEncrypted_password()), 
				data.getName(), 
				data.getRole().toString(),
				data.getClassroom(),
				data.getClass_number(),
				data.getRegister_user_id());

		return rowNumber;
	}
	
	
	/**
	 * (管理用)usersテーブルのデータを1件更新する(パスワード更新有).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateOneForAdmin(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE_FOR_ADMIN,
				data.getName(),
				data.getRole().toString(),
				data.getClassroom(),
				data.getClass_number(),
				data.isEnabled(),
				data.getUpdate_date(),
				data.getUpdate_user_id(),
				data.getUser_id());
		
		return rowNumber;
	}

	/**
	 * userテーブルのデータを1件削除する.
	 * @param user_id 削除するユーザID
	 * @return 削除データ数
	 * @throws DataAccessException
	 */
	public int deleteOne(String user_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_DELETE_ONE, user_id);
		return rowNumber;
	}
	
	
	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新無).
	 * @param userData 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateOne(UserData userData) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE,
				userData.getName(),
				userData.getRole().toString(),
				userData.getNumber_of_trials(),
				userData.getUser_status(),
				userData.getUser_id());
		return rowNumber;
	}

}
