INSERT INTO "PUBLIC"."USERS" VALUES
('t-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', STRINGDECODE('\u5b89\u90e8\u83ef\u5948'), 'ROLE_TEACHER', 'S3A1', NULL, FALSE, TIMESTAMP '2021-11-30 10:07:50.67929', 't-ukeire@hcs.ac.jp', NULL, NULL, TRUE, 0, 1),
('s-ukeire@hcs.ac.jp', '$2a$10$5iJr0wNAxCEFQF2cjk5ixeZtcz.BQru3LETcoXe/IeVg2gaBlWIKW', STRINGDECODE('\u77f3\u7530\u96c4\u4ecb'), 'ROLE_STUDENT', 'S3A1', '01', FALSE, TIMESTAMP '2021-11-30 10:09:37.416439', 't-ukeire@hcs.ac.jp', NULL, NULL, TRUE, 0, 1);

INSERT INTO requests (apply_id, date_activity_from, date_activity_to, loc, way, date_absence_from, date_absence_to, remark, register_user_id)
VALUES('0', '2021-7-17 10:00', '2021-7-17 15:00', '受入テスト', '1',  '2021-7-17 9:15', '2021-7-17 15:00', 'テスト', 't-ukeire@hcs.ac.jp');