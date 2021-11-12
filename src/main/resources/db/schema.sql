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
    number_of_trials int NOT NULL DEFAULT 0,
  	user_status int NOT NULL DEFAULT 1,
    
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
	apply_id VARCHAR(254) PRIMARY KEY,
	applicant_id VARCHAR(254) NOT NULL,
	content VARCHAR(2) NOT NULL,
    company_name VARCHAR(137) NOT NULL,
	status VARCHAR(2) NOT NULL DEFAULT '2',
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
    
    way CHAR(1) NOT NULL,
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
 * とりまとめ企業マスタ
 */
CREATE TABLE IF NOT EXISTS summary_companies (
	company_id VARCHAR(254) PRIMARY KEY,
	job_no VARCHAR(254),
	company_name VARCHAR(137),
	resume BOOLEAN  NOT NULL DEFAULT false,
	university_tranriptsscripts BOOLEAN NOT NULL DEFAULT false,
	university_diploma BOOLEAN NOT NULL DEFAULT false,
	hcs_transcript BOOLEAN NOT NULL DEFAULT false,
	hcs_diploma BOOLEAN NOT NULL DEFAULT false,
	health_certificate BOOLEAN NOT NULL DEFAULT false,
	high_school_transcript BOOLEAN NOT NULL DEFAULT false,
	recommendation BOOLEAN NOT NULL DEFAULT false,
	personal_information_agreement BOOLEAN NOT NULL DEFAULT false
);

/**
 * とりまとめ書類マスタ
 */
CREATE TABLE IF NOT EXISTS summary_documents (
	apply_id VARCHAR(254) PRIMARY KEY,
	company_id VARCHAR(254),
	receipt_of_tresume TIMESTAMP,
	receipt_of_university_tranriptsscripts TIMESTAMP,
	receipt_of_university_diploma TIMESTAMP,
	receipt_of_hcs_transcript TIMESTAMP,
	receipt_of_hcs_diploma TIMESTAMP,
	receipt_of_health_certificate TIMESTAMP,
	receipt_of_high_school_transcript TIMESTAMP,
	receipt_of_recommendation TIMESTAMP,
	receipt_of_personal_information_agreement TIMESTAMP,
	receipt_of_all_required  TIMESTAMP,
	
	FOREIGN KEY (apply_id)
    	REFERENCES job_hunting(apply_id),
    FOREIGN KEY (company_id)
    	REFERENCES summary_companies(company_id)
);

/**
 * 就活イベントマスタ
 */
CREATE TABLE IF NOT EXISTS events (
	event_id VARCHAR(254) PRIMARY KEY,
	company_name VARCHAR(137) NOT NULL,
	datetime TIMESTAMP NOT NULL,
	loc VARCHAR(100) NOT NULL,
	content VARCHAR(254) NOT NULL,
	bring VARCHAR(254),
	register_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
	register_user_id VARCHAR(254) NOT NULL,
	update_date TIMESTAMP DEFAULT NULL,
	update_user_id VARCHAR(254) DEFAULT NULL,
	
	FOREIGN KEY (register_user_id)
    	REFERENCES users(user_id),
    FOREIGN KEY (update_user_id)
    	REFERENCES users(user_id)	
);

/**
 * とりまとめ名簿管理マスタ
 */
CREATE TABLE IF NOT EXISTS summary_list_management (
	apply_id VARCHAR(254) PRIMARY KEY,
	register_date TIMESTAMP,
	register_user_id VARCHAR(254) NOT NULL,
	
	FOREIGN KEY (apply_id)
    	REFERENCES job_hunting(apply_id),
    FOREIGN KEY (register_user_id)
    	REFERENCES users(user_id)
);

/* メッセージマスタ
 * 定義書の内容を参照
 * */
CREATE TABLE IF NOT EXISTS messages (
	message_id VARCHAR(254) PRIMARY KEY,
	user_id VARCHAR(254) NOT NULL,
	event_id VARCHAR(254),
        subject VARCHAR(254) NOT NULL,
	content VARCHAR(254),
	is_checked boolean NOT NULL DEFAULT FALSE,
	sender_id VARCHAR(254) NOT NULL,	
	received_date TIMESTAMP,
	
	FOREIGN KEY (user_id)
              REFERENCES users(user_id) ,
        FOREIGN KEY (event_id)
              REFERENCES events(event_id) ,
        FOREIGN KEY (sender_id)
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