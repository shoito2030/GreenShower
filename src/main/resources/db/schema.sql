/* 開発用にデータ削除を追加 : リリース時は消す
DROP TABLE users;
 */

/* ユーザマスタ
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


/* 就職活動申請マスタ
 * 定義書の内容を参照
 * */
CREATE TABLE IF NOT EXISTS job_hunting (
	apply_id VARCHAR(254) PRIMARY KEY AUTO_INCREMENT,
	applicant_id VARCHAR(254) NOT NULL,
	status VARCHAR(2) NOT NULL DEFAULT '3',
	apply_type CHAR(1) NOT NULL,
	indicate VARCHAR(254),	
	FOREIGN KEY (applicant_id)
              REFERENCES users(user_id) 
);

/**
 * 申請マスタ
 * 定義書参照
 */
 CREATE TABLE IF NOT EXISTS requests (
    apply_id VARCHAR(254) PRIMARY KEY,
    date_activity_from DATETIME NOT NULL,
    date_activity_to DATETIME NOT NULL,
    loc VARCHAR(100) NOT NULL,
    content VARCHAR(2) NOT NULL,
    company_name VARCHAR(137) NOT NULL,
    
    date_absence_from DATETIME,
    date_absence_to DATETIME,
    leave_early_date DATETIME,
    attendance_date DATETIME,
    remark VARCHAR(254),
    
    register_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    register_user_id VARCHAR(254) NOT NULL,
    update_date TIMESTAMP,
    update_user_id VARCHAR(254),
    
    FOREIGN KEY (apply_id)
              REFERENCES job_hunting(apply_id),
    FOREIGN KEY (register_user_id)
              REFERENCES users(user_id),
    FOREIGN KEY (update_user_id)
              REFERENCES users(user_id) 
);


/* 報告マスタ
 * 定義書の内容を参照
 * */
CREATE TABLE IF NOT EXISTS reports (
	apply_id VARCHAR(254) PRIMARY KEY,
	advance_or_retreat BOOLEAN NOT NULL ,
	remark VARCHAR(254),	
	
	register_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    register_user_id VARCHAR(254) NOT NULL,
    update_date TIMESTAMP,
    update_user_id VARCHAR(254),
	
	FOREIGN KEY (apply_id)
              REFERENCES job_hunting(apply_id),
    FOREIGN KEY (register_user_id)
              REFERENCES users(user_id),
    FOREIGN KEY (update_user_id)
              REFERENCES users(user_id) 
);




/**
 * 前回のやつ
 */
CREATE TABLE IF NOT EXISTS report_hoge (
	report_id INT PRIMARY KEY,
	job_number VARCHAR(13),
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
    test_final BOOLEAN DEFAULT false,
    test_section_other VARCHAR(60),
    test_summary VARCHAR(6),
    test_summary_other VARCHAR(60),
    result_notification VARCHAR(2),
    success_only BOOLEAN DEFAULT false,
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