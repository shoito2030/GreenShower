/* ユーザマスタのデータ（一般権限：学生） PASS:password */
INSERT INTO users (user_id, encrypted_password, name, role, classroom, class_number, register_user_id)
VALUES('isida@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '石田悠介', 'ROLE_STUDENT', 'S3A1', '01', 'isida@xxx.co.jp');
INSERT INTO users (user_id, encrypted_password, name, role, classroom, class_number, register_user_id)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田啓介', 'ROLE_STUDENT', 'S3A2', '02', 'yamada@xxx.co.jp');

/* ユーザマスタのデータ（ADMIN権限：担任、事務） PASS:password */
INSERT INTO users (user_id, encrypted_password, name, role,  register_user_id)
VALUES('abe@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '安部華奈', 'ROLE_TEACHER', 'abe@xxx.co.jp');
INSERT INTO users (user_id, encrypted_password, name, role, register_user_id)
VALUES('sano@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '佐野翼', 'ROLE_STAFF', 'sano@xxx.co.jp');

/* 受入テスト用 */
INSERT INTO users (user_id, encrypted_password, name, role, classroom, class_number, register_user_id)
VALUES('s-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '受入太郎', 'ROLE_STUDENT', 'S3A3', '01', 'isida@xxx.co.jp');
INSERT INTO users (user_id, encrypted_password, name, role, classroom, class_number, dark_mode,  register_user_id)
VALUES('t-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '受入先生', 'ROLE_TEACHER', 'S3A3', '99', true, 'abe@xxx.co.jp');
INSERT INTO users (user_id, encrypted_password, name, role, classroom, class_number, dark_mode,  register_user_id)
VALUES('j-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '受入事務', 'ROLE_STAFF', '99', '99', true, 'abe@xxx.co.jp');


/* report_hogeのデータ */
INSERT INTO report_hoge (report_id, job_number, user_id, classroom, class_number, name, course_code, company_name, company_name_kana, datetime, place, entry_section, venue_section, test_section, test_summary, result_notification, result_notification_day, interview_detail, interview_number, interviewer_number, interviewer_position, interview_time, question_contents, report_status, registered_date, request_date, registered_user_id, remarks, aptitude_test_detail)
VALUES('1',12121111, 'isida@xxx.co.jp', 'S3A1', '01', '石田悠介', 'S', 'グリーンシャワー株式会社', 'グリー', '2021-7-10', '受験場所', 1, 1, 1, 6, 4,'2', 2, 3, 3, '課長', 60, '出題内容', 1, '2021-7-17', '2021-7-15', 'isida@xxx.co.jp', '備考', 1);
INSERT INTO report_hoge (report_id,job_number, user_id, classroom, class_number, name, course_code, company_name, company_name_kana, datetime, place, entry_section, venue_section, test_section, test_summary, result_notification, result_notification_day, interview_detail, interview_number, interviewer_number, interviewer_position, interview_time, question_contents, report_status, registered_date, request_date, registered_user_id, remarks,aptitude_test_detail)
VALUES('2',12314444, 'jouhou@xxx.co.jp', 'S3A1', '02', '情報太郎', 'S', 'グリーンシャワー株式会社', 'グリー', '2021-7-10', '受験場所', 1, 1, 1, 6, 4,'2', 2, 3, 3, '課長', 60, '出題内容', 1, '2021-7-17', '2021-7-15', 'isida@xxx.co.jp', '備考', 1);

/* 就職活動申請マスタ用データ */
INSERT INTO job_hunting (apply_id, applicant_id, status, apply_type, content, company_name)
VALUES('1', 'isida@xxx.co.jp', '6', '0', '2', 'グリーンシャワー株式会社');
INSERT INTO job_hunting (apply_id, applicant_id, status, apply_type, content, company_name)
VALUES('2', 'isida@xxx.co.jp', '6', '0', '2', 'ブルーシャワー株式会社');
INSERT INTO job_hunting (apply_id, applicant_id, status, apply_type, content, company_name)
VALUES('3', 'yamada@xxx.co.jp', '2', '0', '2', 'レッドシャワー株式会社');
INSERT INTO job_hunting (apply_id, applicant_id, status, apply_type, content, company_name)
VALUES('4', 'yamada@xxx.co.jp', '4', '1', '2', 'ブラックシャワー株式会社');

/* 申請マスタ用データ */
INSERT INTO requests (apply_id, date_activity_from, date_activity_to, loc, way, date_absence_from, date_absence_to, remark, register_user_id)
VALUES('1', '2021-7-17 10:00', '2021-7-17 15:00', '北海道情報専門学校', '1',  '2021-7-17 9:15', '2021-7-17 15:00', '欠席君', 'isida@xxx.co.jp');
INSERT INTO requests (apply_id, date_activity_from, date_activity_to, loc, way, leave_early_date, remark, register_user_id)
VALUES('2', '2021-7-17 10:00', '2021-7-17 15:00', '北海道情報専門学校', '2',  '2021-7-18 12:15', '早退君', 'isida@xxx.co.jp');
INSERT INTO requests (apply_id, date_activity_from, date_activity_to, loc, way, attendance_date, remark, register_user_id)
VALUES('3', '2021-7-17 10:00', '2021-7-17 15:00', '北海道情報専門学校', '3',  '2021-7-17 11:00', '遅刻君', 'yamada@xxx.co.jp');
INSERT INTO requests (apply_id, date_activity_from, date_activity_to, loc, way, attendance_date, remark, register_user_id)
VALUES('4', '2021-7-17 10:00', '2021-7-17 15:00', '北海道情報専門学校', '3',  '2021-7-17 12:00', '阿部先生が代わりに申請しました。', 'abe@xxx.co.jp');

/* 報告マスタ用データ */
INSERT INTO REPORTS(APPLY_ID,ADVANCE_OR_RETREAT,REMARK,REGISTER_USER_ID) VALUES('1', TRUE, 'テスト報告', 'isida@xxx.co.jp');
INSERT INTO REPORTS(APPLY_ID,ADVANCE_OR_RETREAT,REMARK,REGISTER_USER_ID) VALUES('2', TRUE, '報告詳細から申請詳細に遷移できるかテスト', 'isida@xxx.co.jp');