/* ユーザマスタのデータ（一般権限：学生） PASS:password */
INSERT INTO users (user_id, encrypted_password, name, role, classroom, class_number, register_user_id)
VALUES('isida@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '石田悠介', 'ROLE_STUDENT', 'S3A1', '01', 'isida@xxx.co.jp');

/* ユーザマスタのデータ（ADMIN権限：担任、事務） PASS:password */
INSERT INTO users (user_id, encrypted_password, name, role,  register_user_id)
VALUES('abe@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '安部華奈', 'ROLE_TEACHER', 'abe@xxx.co.jp');
INSERT INTO users (user_id, encrypted_password, name, role, register_user_id)
VALUES('sano@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '佐野翼', 'ROLE_STAFF', 'sano@xxx.co.jp');