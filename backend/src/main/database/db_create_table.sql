USE `demo`;

-- CREATE TRIGGER increase_fame_point_insert AFTER INSERT ON like_question
-- FOR EACH ROW
-- 	UPDATE editor SET fame_point = (SELECT AVG(point) FROM like_question WHERE question_id = NEW.question_id) 
--     WHERE user_id = (SELECT user_id FROM question WHERE question_id = NEW.question_id); 
--     
-- CREATE TRIGGER increase_fame_point_update AFTER UPDATE ON like_question
-- FOR EACH ROW
-- 	UPDATE editor SET fame_point = (SELECT AVG(point) FROM like_question WHERE question_id = NEW.question_id) 
--     WHERE user_id = (SELECT user_id FROM question WHERE question_id = NEW.question_id); 

/*
CREATE TABLE user(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	email VARCHAR(64) NOT NULL UNIQUE,
	password VARCHAR(254) NOT NULL,
	name VARCHAR(32) NOT NULL,
	phone_no VARCHAR(11) NOT NULL,
	description VARCHAR(300) DEFAULT NULL,
    last_password_change TIMESTAMP DEFAULT NULL
) ENGINE= INNODB;

CREATE TABLE company(
	user_id INT PRIMARY KEY,
	FOREIGN KEY (user_id) REFERENCES user(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE job_seeker(
	user_id INT PRIMARY KEY,
    cv_url TINYTEXT DEFAULT NULL,
	FOREIGN KEY (user_id) REFERENCES user(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE editor(
	user_id INT PRIMARY KEY,
    fame_point DOUBLE DEFAULT 0,
	FOREIGN KEY (user_id) REFERENCES user(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE question(
	question_id INT PRIMARY KEY AUTO_INCREMENT,
	create_date TIMESTAMP,
    user_id INT,
	question_difficulty INT DEFAULT 0,
	question_title VARCHAR(64),
	question_content TINYTEXT,
	FOREIGN KEY (user_id) REFERENCES editor(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE like_question(
	user_id INT NOT NULL,
    question_id INT NOT NULL,
    point INT DEFAULT 0,
    PRIMARY KEY (user_id, question_id),
    FOREIGN KEY (user_id) REFERENCES job_seeker(user_id)
    ON DELETE CASCADE
	ON UPDATE RESTRICT,
    FOREIGN KEY (question_id) REFERENCES question(question_id)
    ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE= INNODB;

CREATE TABLE challenge(
	question_id INT PRIMARY KEY,
	FOREIGN KEY (question_id) REFERENCES question(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE forum(
	forum_id INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT NOT NULL,
	create_date TIMESTAMP,
	title VARCHAR(64) NOT NULL,
    FOREIGN KEY(question_id) REFERENCES challenge(question_id)
    ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE post(
	post_id INT AUTO_INCREMENT,
	user_id INT,
	forum_id INT,
	create_date TIMESTAMP,
	title VARCHAR(64) NOT NULL,
	content TEXT NOT NULL,
	PRIMARY KEY (post_id, forum_id),
	FOREIGN KEY (forum_id) REFERENCES forum(forum_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (user_id) REFERENCES user(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE job_seeker_skill(
	user_id INT,
	skill VARCHAR(32) NOT NULL,
	PRIMARY KEY (user_id, skill),
	FOREIGN KEY (user_id) REFERENCES job_seeker(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE contest(
	contest_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
	contest_name VARCHAR(64) NOT NULL,
	description VARCHAR(300) NOT NULL,
	start_time TIMESTAMP,
	duration INT DEFAULT 0,
	create_date TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES editor(user_id)
    ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE contest_category(
	contest_id INT,
	category VARCHAR(32) NOT NULL,
	PRIMARY KEY(contest_id, category),
	FOREIGN KEY (contest_id) REFERENCES contest(contest_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE sponsor(
	user_id INT,
	contest_id INT,
	money INT DEFAULT 0,
	PRIMARY KEY (user_id, contest_id),
	FOREIGN KEY (user_id) REFERENCES company(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (contest_id) REFERENCES contest(contest_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE enter_contest(
	user_id INT,
	contest_id INT,
	PRIMARY KEY (user_id, contest_id),
	FOREIGN KEY (user_id) REFERENCES job_seeker(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (contest_id) REFERENCES contest(contest_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE contest_has_question(
	question_id INT,
	contest_id INT,
	PRIMARY KEY(question_id, contest_id),
	FOREIGN KEY (question_id) REFERENCES question(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (contest_id) REFERENCES contest(contest_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE non_coding_question(
	question_id INT PRIMARY KEY,
	FOREIGN KEY (question_id) REFERENCES question(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE question_category(
	question_id INT,
	category VARCHAR(32) NOT NULL,
	PRIMARY KEY (question_id, category),
	FOREIGN KEY (question_id) REFERENCES question(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE challenge_hint(
	question_id INT,
	hint VARCHAR(300) NOT NULL,
	PRIMARY KEY (question_id, hint),
	FOREIGN KEY (question_id) REFERENCES challenge(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE test_case(
	question_id INT NOT NULL,
	test_case_id INT AUTO_INCREMENT,
	input VARCHAR(64) NOT NULL,
	output VARCHAR(64) NOT NULL,
	testcase_type VARCHAR(32) NOT NULL,
	PRIMARY KEY(test_case_id, question_id),
	FOREIGN KEY (question_id) REFERENCES challenge(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE attempt(
	attempt_id INT PRIMARY KEY AUTO_INCREMENT,
	attempt_start TIMESTAMP,
	attempt_end TIMESTAMP,
	point INT DEFAULT 0
) ENGINE = INNODB;

CREATE TABLE coding_attempt(
	attempt_id INT PRIMARY KEY,
	programming_language VARCHAR(32) DEFAULT 0,
	code TEXT,
	FOREIGN KEY (attempt_id) REFERENCES attempt(attempt_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE non_coding_attempt(
	attempt_id INT PRIMARY KEY,
	answer VARCHAR(300) DEFAULT NULL,
	FOREIGN KEY (attempt_id) REFERENCES attempt(attempt_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE test_case_attempt(
	attempt_id INT,
	test_case_id INT,
	result VARCHAR(32) NOT NULL,
	PRIMARY KEY (attempt_id, test_case_id),
	FOREIGN KEY (attempt_id) REFERENCES coding_attempt(attempt_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (test_case_id) REFERENCES test_case(test_case_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE solve_coding(
	user_id INT,
	question_id INT,
	attempt_id INT,
	PRIMARY KEY (user_id, question_id, attempt_id),
	FOREIGN KEY (user_id) REFERENCES job_seeker(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (question_id) REFERENCES challenge(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (attempt_id) REFERENCES coding_attempt(attempt_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE solve_non_coding(
	user_id INT,
	question_id INT,
	attempt_id INT,
	PRIMARY KEY (user_id, question_id, attempt_id),
	FOREIGN KEY (user_id) REFERENCES job_seeker(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (question_id) REFERENCES non_coding_question(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (attempt_id) REFERENCES non_coding_attempt(attempt_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE interview(
	interview_id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(32) NOT NULL,
	start_date TIMESTAMP,
    create_date TIMESTAMP,
	duration INT NOT NULL,
	user_id INT,
	FOREIGN KEY (user_id) REFERENCES company(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE interview_has_question(
	interview_id INT,
	question_id INT,
	PRIMARY KEY (interview_id, question_id),
	FOREIGN KEY (interview_id) REFERENCES interview(interview_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (question_id) REFERENCES question(question_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;

CREATE TABLE enter_interview(
	interview_id INT,
	user_id INT,
	interview_result VARCHAR(32) DEFAULT "ongoing",
	finished INT DEFAULT 0,
	PRIMARY KEY (interview_id, user_id),
	FOREIGN KEY (interview_id) REFERENCES interview(interview_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT,
	FOREIGN KEY (user_id) REFERENCES job_seeker(user_id)
	ON DELETE CASCADE
	ON UPDATE RESTRICT
) ENGINE = INNODB;
*/


