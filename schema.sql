CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `username` varchar(255) NOT NULL COMMENT 'username',
  `password` char(60) NOT NULL COMMENT 'password',
  `mail` VARCHAR(255) NOT NULL COMMENT 'email',
  `enable` BOOLEAN DEFAULT FALSE COMMENT 'account is enable',
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `user_username_idx` (`username` ASC) USING BTREE,
  UNIQUE INDEX `user_mail_idx` (`mail` ASC) USING BTREE
)DEFAULT CHAR SET utf8 AUTO_INCREMENT 1000;

CREATE TABLE IF NOT EXISTS `group` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `group_name` varchar(255) NOT NULL COMMENT 'group name',
  `match_id` int NOT NULL COMMENT 'match id',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `group_group_and_match_idx` (`group_name`, `match_id` ASC) USING BTREE
)DEFAULT CHAR SET utf8 AUTO_INCREMENT 1000;

CREATE TABLE IF NOT EXISTS `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `title` varchar(255) NOT NULL COMMENT 'activity title',
  `content` varchar(255) NOT NULL COMMENT 'activity content',
  PRIMARY KEY (`id`)
)DEFAULT CHAR SET utf8 AUTO_INCREMENT 1000;

CREATE TABLE IF NOT EXISTS `match` (
  `id` int(11) NOT NULL COMMENT 'primary key',
  `match_name` varchar(255) NOT NULL COMMENT 'name of match',
  `detail` varchar(255) NOT NULL COMMENT 'content of match',
  `start_time` bigint NOT NULL COMMENT 'begin time',
  `end_time` bigint NOT NULL COMMENT 'end time',
  `data_link` VARCHAR(255) DEFAULT NULL COMMENT 'data set file link',
  `data_password` VARCHAR(4) DEFAULT NULL COMMENT 'password for get data set link',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `match_end_time_idx` (`end_time` ASC) USING BTREE
)DEFAULT CHAR SET utf8 AUTO_INCREMENT 1000;

CREATE TABLE IF NOT EXISTS registration (
  `id` int(11) NOT NULL COMMENT 'primary key',
  `match_id` int(11) NOT NULL COMMENT 'match id',
  `user_id1` int(11) NOT NULL COMMENT 'id of user1',
  `user_id2` int(11) NOT NULL COMMENT 'id of user2',
  `group_id` int(11) NULL COMMENT 'group id',
  PRIMARY KEY (`id`) ,
  INDEX `mugm_match_idx` (`match_id` ASC) USING BTREE,
  INDEX `mugm_user_idx` (user_id1 ASC) USING BTREE,
  INDEX `mugm_group_idx` (`group_id` ASC) USING BTREE,
  UNIQUE INDEX `mugm_uniq` (`match_id` ASC, user_id1 ASC, user_id2 ASC) USING BTREE
)DEFAULT CHAR SET utf8 AUTO_INCREMENT 1000;

CREATE TABLE IF NOT EXISTS `score` (
  `id` int(11) NOT NULL COMMENT 'primary key',
  `regist_id` int(11) NOT NULL COMMENT 'registration id',
  `score` int NOT NULL COMMENT 'signUpUser score',
  `time` bigint NOT NULL COMMENT 'commit time',
  PRIMARY KEY (`id`)
)DEFAULT CHAR SET utf8 AUTO_INCREMENT 1000;

ALTER TABLE `group` ADD CONSTRAINT `group_match_ref` FOREIGN KEY (`match_id`) REFERENCES `match` (`id`);
ALTER TABLE registration ADD CONSTRAINT `mugm_match_ref` FOREIGN KEY (`match_id`) REFERENCES `match` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE registration ADD CONSTRAINT `mugm_user1_ref` FOREIGN KEY (user_id1) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE registration ADD CONSTRAINT `mugm_user2_ref` FOREIGN KEY (user_id2) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE registration ADD CONSTRAINT `mugm_group_ref` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE `score` ADD CONSTRAINT `score_enlist_ref` FOREIGN KEY (regist_id) REFERENCES registration (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- # insert test data

INSERT INTO activity(title, content, img, create_time, end_time) VALUES
  ('title1', 'content1', '/img/1.jpg', 1475164800, 1475337600),
  ('title2', 'content2', '/img/2.jpg', 1475337600, 1480435200),
  ('title3', 'content3', '/img/3.jpg', 1480435200, 1480694400),
  ('title4', 'content4', '/img/4.jpg', 1475424000, 1477756800),
  ('title5', 'content5', '/img/5.jpg', 1477756800, 1480694400),
  ('title6', 'content6', '/img/6.jpg', 1475424000, 1480694400);

-- # INSERT INTO signUpUser(username, password, salt, mail) VALUES
-- #   ('', '', '', ''),
-- #   ('', '', '', ''),
-- #   ('', '', '', ''),
-- #   ('', '', '', '');
-- #
-- # INSERT INTO `group`(group_name, match_id) VALUES
-- #   ('', ''),
-- #   ('', ''),
-- #   ('', '');
-- #
-- # INSERT INTO `match`(match_name, detail, start_time, end_time) VALUES
-- #   ('', '', 1, 2),
-- #   ('', '', 1, 2);
-- #
-- # INSERT INTO registration(match_id, user_id, group_id) VALUES
-- #   (1000, 1000, 1000),
-- #   (1000, 1000, 1000),
-- #   (1000, 1000, 1000),
-- #   (1000, 1000, 1000);
-- #
-- # INSERT INTO score(regist_id, score, time) VALUES
-- #   (1000, 53, 1),
-- #   (1000, 53, 1),
-- #   (1000, 53, 1),
-- #   (1000, 53, 1),
-- #   (1000, 53, 1),
-- #   (1000, 53, 1),
-- #   (1000, 53, 1),
-- #   (1000, 53, 1);