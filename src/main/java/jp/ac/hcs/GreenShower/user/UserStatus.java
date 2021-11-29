package jp.ac.hcs.GreenShower.user;

/**
 * ユーザ状態を定義する.
 * 1 有効		←初期値
 * 2 ロック中
 * 3 無効
 */
public enum UserStatus {
	/** 1 有効 */
	VALID(1), 
	/** 2 ロック中 */
	LOCKED(2), 
	/** 3 無効 */
	INVALID(3);

	private final int code;

	private UserStatus(int code) {
		this.code = code;
	}
	
	/**
	 * UserStatusを取得
	 * @return code
	 */
	public int getCode() {
		return code;
	}
}
