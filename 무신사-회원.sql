CREATE TABLE `springbasic`.`member_info` (
  `member_number` INT NOT NULL AUTO_INCREMENT,
  `member_state_code` VARCHAR(50) NOT NULL,
  `id` VARCHAR(50) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `birth` DATE NOT NULL,
  `sex` VARCHAR(1) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `login_datetime` DATETIME NULL,
  `modify_datetime` DATETIME NULL,
  `register_datetime` DATETIME NOT NULL,
  `exit_datetime` DATETIME NOT NULL DEFAULT '9999-12-31 23:59:59',
  `recommand_id` VARCHAR(50) NULL,
  `login_count` TINYINT(2) NOT NULL DEFAULT 0,
  `is_admin` VARCHAR(1) NOT NULL DEFAULT 'N',
  `note` TEXT NULL,
  PRIMARY KEY (`member_number`));




insert into springbasic.member_info(member_state_code, id, password, name, birth, sex, phone_number, email, login_datetime, modify_datetime, register_datetime, exit_datetime ,recommand_id, is_admin)
values
('40001', 'asdf1234', 'asdf1234!', '김진철', '2000-08-22', 'M', '01012345678', 'asdf@naver.com', now(), NULL ,'2023-02-20 8:09:45', '9999-12-31 23:59:59', NULL, 'N' ),
('40001', 'qwer1234', 'qwer1234!', '우소미', '1999-09-22', 'F', '01009876543', 'qwer@naver.com', now(), NULL ,'2023-04-21 8:09:35', '9999-12-31 23:59:59', 'asdf1234', 'N' ),
('40001', 'zxcv1234', 'zxcv1234!', '조시형', '1993-03-21', 'F', '01023456789', 'zxcv@naver.com', now(), NULL ,'2023-05-03 16:04:00', '9999-12-31 23:59:59', NULL, 'N' ),
('40002', 'qwerty1234', 'qwerty1234!', '임미정', '1992-05-17', 'F', '01034567890', 'qwerty@naver.com', now(), NULL , '2023-05-17 17:08:29', '9999-12-31 23:59:59', NULL, 'N' ),
('40001', 'asdfgh1234', 'asdfgh1234!', '공경민', '2000-02-20', 'M', '01045678901', 'asdfgh@naver.com', now(), NULL ,'2023-06-25 8:00:00', '9999-12-31 23:59:59', NULL, 'N' ),
('40001', 'zxcvbn1234', 'zxcvbn1234!', '장우원', '1996-08-24', 'M', '01056789012', 'zxcvbn@naver.com', now(), NULL ,'2023-07-09 14:23:45', '9999-12-31 23:59:59', 'asdf1234', 'N' ),
('40001', 'poiu1234', 'poiu1234!', '남라미', '2001-05-04', 'F', '01067890123', 'poiu@naver.com', now(), NULL ,'2023-08-09 13:45:23', '9999-12-31 23:59:59', NULL, 'N' ),
('40001', 'lkjh1234', 'lkjh1234!', '최서윤', '2001-07-25', 'F', '01078901234', 'lkjh@naver.com', now(), NULL ,'2023-09-21 16:24:17', '9999-12-31 23:59:59', NULL, 'N' ),
('40004', 'mnbv1234', 'mnbv1234!', '홍재안', '1995-04-14', 'M', '01089012345', 'mnbv@naver.com', now(), NULL ,'2023-10-09 21:04:21', '2024-08-06 15:03:47', NULL, 'N' ),
('40001', 'ghjkl1234', 'ghjkl1234!', '이정헌', '1990-02-10', 'M', '01090123456', 'ghjkl@naver.com', now(), NULL ,'2023-11-12 8:34:23', '9999-12-31 23:59:59', NULL, 'N' ),
('40001', '20231201123', 'vbnm1234!', '문연지', '1992-11-24', 'F', '01001234567', 'yeonji@naver.com',now(), NULL , '2023-12-01 8:00:00', '9999-12-31 23:59:59', NULL, 'Y' ),
('40001', '20240101001', 'yuiop1234!', '이아윤', '1994-11-14', 'F', '01087654321', 'ayoon@naver.com', now(), NULL ,'2024-01-01 8:00:00', '9999-12-31 23:59:59', NULL, 'Y' )
;