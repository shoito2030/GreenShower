/* 開発用にデータ削除を追加 : リリース時は消す
DROP TABLE users;
 */

/* ユーザマスタ テーブル
 * 定義書の内容を参照
 * */
CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(254) PRIMARY KEY,
    encrypted_password VARCHAR(100) NOT NULL,
    name VARCHAR(60) NOT NULL,
    role VARCHAR(7) NOT NULL,
    class CHAR(4),
    class_number CHAR(2),
    dark_mode BOOLEAN NOT NULL DEFAULT false,
    user_status VARCHAR(7) NOT NULL DEFAULT 'VALID',
    register_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
     register_user_id VARCHAR(254) NOT NULL,
    update_date TIMESTAMP,
    update_user_id VARCHAR(254),
    enabled BOOLEAN DEFAULT true,
    
    CONSTRAINT fk_register_user_id
    	FOREIGN KEY (register_user_id)
    	REFERENCES users (user_id)
    	ON DELETE CASCADE,
    	
    CONSTRAINT fk_update_user_id
    	FOREIGN KEY (update_user_id)
    	REFERENCES users (user_id)
    	ON DELETE CASCADE
);