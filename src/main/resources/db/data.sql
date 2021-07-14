/* ユーザマスタのデータ（一般権限：学生） PASS:password */
INSERT INTO users (user_id, encrypted_password, name, role, class, class_number, register_user_id)
VALUES('isida@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '石田悠介', 'STUDENT', 'S3A1', '01', 'isida@xxx.co.jp');

/* ユーザマスタのデータ（ADMIN権限：担任、事務） PASS:password */
INSERT INTO users (user_id, encrypted_password, name, role,  register_user_id)
VALUES('abe@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '安部華奈', 'TEACHER', 'abe@xxx.co.jp');
INSERT INTO users (user_id, encrypted_password, name, role, register_user_id)
VALUES('sano@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '佐野翼', 'STAFF', 'sano@xxx.co.jp');