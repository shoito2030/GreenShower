package jp.ac.hcs.GreenShower.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザ情報を全件取得する
	 * 
	 * @return Optional<UserEntity>
	 */
	public Optional<UserEntity> selectAll() {
		UserEntity userEntity;

		try {
			userEntity = userRepository.selectAll();
		} catch (DataAccessException e) {
			e.printStackTrace();
			userEntity = null;
		}

		return Optional.ofNullable(userEntity);
	}

	/**
	 * ユーザIDに紐づいた情報を1件だけ取得する
	 * 
	 * @param user_id ユーザID
	 * @return Optional<UserData>
	 */
	public Optional<UserData> select(String user_id) {
		UserData userData;

		try {
			userData = userRepository.selectOne(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			userData = null;
		}

		return Optional.ofNullable(userData);
	}

	/**
	 * ユーザマスタに新たなユーザ情報を1件追加する
	 * 
	 * @param form    検証済み入力情報
	 * @param register_user_id 登録処理を実行したユーザのID
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean insert(UserFormForInsert form, String register_user_id) {
		int rowNumber = 0;

		try {
			// 追加処理を行い、追加できた件数を取得
			rowNumber = userRepository.insertOne(refillToUserData(form, register_user_id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber > 0;
	}

	/**
	 * ユーザマスタからidに紐づく情報を削除する
	 * 
	 * @param user_id ユーザーID
	 * @return - true：追加件数1件以上（処理成功）の場合 - false：追加件数0件（処理失敗）の場合
	 */
	public boolean delete(String user_id) {
		int rowNumber = 0;
		try {
			// 削除処理を行い削除できた件数を取得
			rowNumber = userRepository.deleteOne(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return rowNumber > 0;
	}

//	/**
//	 * ユーザマスタの情報を更新する（管理者のみが利用）
//	 * 
//	 * @param form 検証済み入力データ
//	 * @return - true：更新件数1件以上（処理成功）の場合 - false：更新件数0件（処理失敗）の場合
//	 */
//	public boolean updateForAdmin(UserFormForUpdate form) {
//		int rowNumber = 0;
//
//		// UserFormForUpdate型 → UserData型
//		UserData userData = refillToUserData(form);
//
//		if (userData.getPassword().equals("")) {
//			// パスワードの変更なし
//			try {
//				// 管理者用更新処理
//				rowNumber = userRepository.updateOne(userData);
//			} catch (DataAccessException e) {
//				e.printStackTrace();
//			}
//
//		} else {
//			// パスワードの変更あり
//			try {
//				// 管理者用更新処理
//				rowNumber = userRepository.updateOneWithPassword(userData);
//			} catch (DataAccessException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return rowNumber > 0;
//	}

	/**
	 * 入力情報をUserData型に変換する（insert用）
	 * 
	 * @param form    検証済み入力データ
	 * @param user_id 登録処理を実行したユーザのID
	 * @return UserData
	 */
	private UserData refillToUserData(UserFormForInsert form, String register_user_id) {
		UserData data = new UserData();

		data.setUser_id(form.getUser_id());
		data.setEncrypted_password(form.getEncrypted_password());
		data.setName(form.getName());
		data.setRole(Role.valueOf(form.getRole()));
		data.setClassroom(form.getClassroom());
		data.setClass_number(form.getClass_number());
		data.setRegister_user_id(register_user_id);

		// 要件に従い初期値は下記とする（UserData参照）
//		data.setRegister_date(form.getRegister_date());
//		data.setUpdate_date(null);
//		data.setUpdate_user_id(null);
//		data.setDark_mode(false);
//		data.setEnabled(true);

		return data;
	}

//	/**
//	 * 入力情報をUserData型に変換する（update用）
//	 * 
//	 * @param form 検証済み入力データ
//	 * @return UserData
//	 */
//	private UserData refillToUserData(UserFormForUpdate form) {
//		UserData data = new UserData();
//		data.setUser_id(form.getUser_id());
//		data.setPassword(form.getPassword());
//		data.setUser_name(form.getUser_name());
//		data.setRole(Role.idOf(form.getRole()));
//
//		// 要件に従い初期値は下記とする（UserData参照）
//		data.setDarkmode(false);
//		data.setEnabled(true);
//
//		return data;
//	}

}
