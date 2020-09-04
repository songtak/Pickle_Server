CREATE TABLE user
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_code` INT NOT NULL,
    `school_code` INT NOT NULL,
    `school_name` VARCHAR(60) NULL,
    `cur_grade` INT NULL,
    `home_class` INT NULL,
    `name` VARCHAR(9) NOT NULL,
    `user_id` VARCHAR(20) NOT NULL,
    `user_pw` VARCHAR(20) NOT NULL,
    `email` VARCHAR(50) NULL,
    `phone` VARCHAR(13) NULL,
    `position_checker` int NOT NULL,
    PRIMARY KEY (id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;


CREATE TABLE file
(
    `file_id` INT NOT NULL AUTO_INCREMENT,
    `file_name` VARCHAR(50) NULL,
    `extension` VARCHAR(10) NULL,
    `article_no` INT NULL,
    `subject_id` INT NULL,
    PRIMARY KEY (file_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;


CREATE TABLE chat
(
    `chat_id` INT NOT NULL AUTO_INCREMENT,
    `insert_date` DATETIME NOT NULL,
    `update_date` DATETIME NOT NULL,
    `chat_body` TEXT NOT NULL,
    `user_code` INT NOT NULL,
    PRIMARY KEY (chat_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;


CREATE TABLE chathistory
(
    `history_id` INT NOT NULL AUTO_INCREMENT,
    `user_info` CHAR(255) NOT NULL,
    `chat_kind` CHAR(1) NOT NULL,
    `chat_body` TEXT NOT NULL,
    `insert_date` DATETIME NOT NULL,
    `update_date` DATETIME NOT NULL,
    `chat_id` INT NOT NULL,
    PRIMARY KEY (history_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;


CREATE TABLE exam
(
    `exam_id` INT NULL AUTO_INCREMENT,
    `exam_kind` INT NOT NULL,
    `subject_code` VARCHAR(10) NOT NULL,
    `exam_num` INT NOT NULL,
    `exam_question` VARCHAR(1000) NOT NULL,
    `exam_division` CHAR(1) NOT NULL,
    `exam_choice1` INT NULL,
    `exam_choice2` INT NULL,
    `exam_choice3` INT NULL,
    `exam_choice4` INT NULL,
    `exam_choice5` INT NULL,
    `exam_answer` VARCHAR(200) NOT NULL,
    `exam_content` VARCHAR(5000) NOT NULL,
    `insert_date` DATETIME NULL,
    PRIMARY KEY (exam_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;


CREATE TABLE exam_analysis
(
    `analysis_id` INT NULL AUTO_INCREMENT,
    `wrong_answer` VARCHAR(200) NOT NULL,
    `subject_code` VARCHAR(200) NOT NULL,
    `user_code` INT NOT NULL,
    `exam_kind` INT NOT NULL,
    `exam_num` INT NOT NULL,
    `insert_date` DATETIME NULL,
    PRIMARY KEY (analysis_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;

create table codeExamKind
(
    code_id int unsigned not null auto_increment,
    code_exam int not null,
    code_exam_kor varchar(50) not null,
    PRIMARY KEY (code_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;

create table codeSubjectKind
(
    code_id int unsigned not null auto_increment,
    code_subject char(5) not null,
    code_subject_kor char(10) not null,
    PRIMARY KEY (code_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;


  CREATE TABLE notice
(
    `article_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `category` VARCHAR(30) NULL,
    `title` VARCHAR(200) NULL,
    `password` VARCHAR(30) NULL,
    `contents` text NULL,
    `create_date` DATETIME NULL,
    `id` int null)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;

  CREATE TABLE notice_comment
(
    `comment_no` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `comment_contents` text NULL,
    `id` int null,
    `article_no` int null)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;


CREATE TABLE `attendance` (
   `attendance_id` INT(11) NOT NULL AUTO_INCREMENT,
   `date` DATETIME NOT NULL,
   `present` INT(11) NULL DEFAULT NULL,
   `absent` INT(11) NULL DEFAULT NULL,
   `tardy` INT(11) NULL DEFAULT NULL,
   `user_code` INT(11) NOT NULL,
   PRIMARY KEY (`attendance_id`)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;


CREATE TABLE `subject` (
	`subject_id` INT NOT NULL AUTO_INCREMENT,
	`subject_code` VARCHAR(7) NOT NULL,
	`subject_name` VARCHAR(16) NOT NULL,
	PRIMARY KEY (`subject_id`)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;

CREATE TABLE `subject_detail` (
    `subjectdetail_id` INT NOT NULL AUTO_INCREMENT,
    `lesson_no` INT(3) NOT NULL,
    `lesson_title` VARCHAR(100) NULL DEFAULT NULL,
    `lesson_detail` VARCHAR(2000) NULL DEFAULT NULL ,
    `user_code` INT(11) NOT NULL,
    `subject_id` INT NOT NULL,
    PRIMARY KEY (`subjectdetail_id`)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;



CREATE TABLE schedule
(
    `schedule_id` INT NOT NULL AUTO_INCREMENT,
    `period` INT NOT NULL,
    `mon` VARCHAR(6) NULL,
    `tue` VARCHAR(6) NULL,
    `wed` VARCHAR(6) NULL,
    `thu` VARCHAR(6) NULL,
    `fri` VARCHAR(6) NULL,
    `checker` VARCHAR(2) NOT NULL,
PRIMARY KEY (schedule_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;

CREATE TABLE grade
(
    `grade_id` INT NOT NULL AUTO_INCREMENT,
    `semester_code` INT NOT NULL,
    `score` INT NOT NULL,
    `user_code` INT NOT NULL,
    `subject_id` int NOT NULL,
    PRIMARY KEY (grade_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;

CREATE TABLE mock
(
    `mock_id` INT NOT NULL AUTO_INCREMENT,
    `semester_code` INT NOT NULL,
    `score` INT NOT NULL,
    `standard` INT NOT NULL,
    `percent` INT NOT NULL,
    `subject` varchar(6) NOT NULL,
    `user_code` INT NOT NULL,
    PRIMARY KEY (mock_id)
)ENGINE=InnoDB
 DEFAULT CHARSET=utf8
 COLLATE=utf8_general_ci;