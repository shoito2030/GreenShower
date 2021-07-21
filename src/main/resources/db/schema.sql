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
    role VARCHAR(12) NOT NULL,
    classroom CHAR(4),
    class_number CHAR(2),
    dark_mode BOOLEAN NOT NULL DEFAULT false,
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

CREATE TABLE IF NOT EXISTS report (
	report_id INT PRIMARY KEY,
	job_number INT,
    user_id VARCHAR(254),
    classroom CHAR(4),
    class_number CHAR(2),
    name VARCHAR(60),
    course_code CHAR(1),
    company_name VARCHAR(60),
    company_name_kana CHAR(3),
    datetime DATE,
    place VARCHAR(60),
    entry_section VARCHAR(6),
    entry_section_other VARCHAR(60),
    venue_section VARCHAR(6),
    venue_section_other VARCHAR(60),
    test_section VARCHAR(6),
    test_final BOOLEAN,
    test_section_other VARCHAR(60),
    test_summary VARCHAR(6),
    test_summary_other VARCHAR(60),
    result_notification VARCHAR(2),
    success_only BOOLEAN,
    result_notification_day INT,
    aptitude_test_detail VARCHAR(6),
    aptitude_test_detail_other VARCHAR(60),
    interview_detail VARCHAR(6),
    interview_detail_other VARCHAR(60),
    interview_number INT,
    interviewer_number INT,
    interviewer_position VARCHAR(60),
    interview_time INT,
    theme VARCHAR(60),
    question_contents VARCHAR(60),
    report_status VARCHAR(2) DEFAULT '1',
    registered_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ,
    request_date DATE,
    registered_user_id VARCHAR(254),
    remarks VARCHAR(254)
);