/* ユーザマスタのデータ（一般権限：学生） PASS:password */
INSERT INTO users (user_id, encrypted_password, name, role, classroom, class_number, register_user_id)
VALUES('isida@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '石田悠介', 'ROLE_STUDENT', 'S3A1', '01', 'isida@xxx.co.jp');

/* ユーザマスタのデータ（ADMIN権限：担任、事務） PASS:password */
INSERT INTO users (user_id, encrypted_password, name, role,  register_user_id)
VALUES('abe@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '安部華奈', 'ROLE_TEACHER', 'abe@xxx.co.jp');
INSERT INTO users (user_id, encrypted_password, name, role, register_user_id)
VALUES('sano@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '佐野翼', 'ROLE_STAFF', 'sano@xxx.co.jp');

/* reportのデータ */
INSERT INTO report (report_id, user_id, classroom, class_number, name, course_code, company_name, company_name_kana, datetime, place, entry_section, venue_section, test_section, test_summary, result_notification, result_notification_day, interview_detail, interview_number, interviewer_number, interviewer_position, interview_time, question_contents, report_status, registered_date, request_date, registered_user_id, remarks)
VALUES('1', 'isida@xxx.co.jp', 'S3A1', '01', '石田悠介', 'S', 'グリーンシャワー株式会社', 'グリー', '2021-7-10', '受験場所', 1, 1, 1, 6, 4,'2', 2, 3, 3, '課長', 60, '出題内容', 1, '2021-7-17', '2021-7-15', 'isida@xxx.co.jp', '備考')