drop database if exists music;
create database music default charset=utf8mb4;
use music;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `headaddr` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `pwd` varchar(255)  DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '123', 'Cuzz', '123456');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `addtime` datetime(6) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('1', '2020-03-13 23:26:35.000000', '摇滚');
INSERT INTO `tag` VALUES ('2', '2020-03-13 23:26:58.000000', '悲情');
INSERT INTO `tag` VALUES ('3', '2020-03-13 23:27:17.000000', '古典');
INSERT INTO `tag` VALUES ('4', '2020-03-13 23:27:50.000000', '流行');
INSERT INTO `tag` VALUES ('5', '2020-03-13 23:29:08.000000', '民谣');
INSERT INTO `tag` VALUES ('6', '2020-03-13 23:29:20.000000', '外语');

-- ----------------------------
-- Table structure for music
-- ----------------------------
DROP TABLE IF EXISTS `music`;
CREATE TABLE `music` (
  `id` int NOT NULL AUTO_INCREMENT,
  `info` varchar(255) DEFAULT NULL,
  `mvresaddr` varchar(255) DEFAULT NULL,
  `resaddr` varchar(255) NOT NULL,
  `zuozhe` varchar(255) DEFAULT NULL,
  `imgresaddr` varchar(255) DEFAULT NULL,
  `ischeckd` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tag_id` int default NULL,
  `comment_num` int default NULL,
  `play_num` int default NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tag_id` (`tag_id`),
  CONSTRAINT `FKyx1tlldkd4fgyldyi0l8ew99` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)  on delete cascade on update cascade
) ENGINE=InnoDB AUTO_INCREMENT=4;

-- ----------------------------
-- Records of music
-- ----------------------------
INSERT INTO `music` VALUES ('1', 'info', 'dawhd14k1n2krn12nrk.mp4', 'd57dd334a2c5427ba92805e672f1e98c.flac', '周杰伦', '214125idjqijoqwdjodjwqodj.jpg', '', '告白气球', '2', '0', '10', '');
INSERT INTO `music` VALUES ('2', '暂无', '0d4943eca09d453eaafec24cbfd42969.mp4', '69349254e53f42908b466d3fe095a12a.flac', '周杰伦', 'c58e6cfe8d034666830f6d95c9fcf541.jpg', '', '说好不哭', '1', '1', '24', '');
INSERT INTO `music` VALUES ('3', '暂无', '7ba6cd77a1704a85979d4269e0a8753a.mp4', '1f045699f4c04eb5a390a8bc05058816.mp3', '陈奕迅', '807ff2fc6dd14761991e1f899874dfcf.jpg', '', '十年', '3', '1', '49', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `headresaddr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `xb` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '463264977llx@gmail.com', 'cf4bca9b56ca468b852e49db59cb9176.jpg', 'liang', '18817291774', '123456', '女', '你好，我是李磊鑫');
INSERT INTO `user` VALUES ('2', '463264977@qq.com', null, 'GH', '18817291775', '123456', 'female', null);
INSERT INTO `user` VALUES ('5', '2719748852@qq.com', null, '晏', '18817291772', '49+461541', 'male', null);
INSERT INTO `user` VALUES ('6', '124912502@163.com', null, 'qwrqwr', '13855826413', 'qwrwqrqwr', 'male', null);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `creat_time` datetime DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `music_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `music_id` (`music_id`),
  CONSTRAINT `FK1_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) on delete cascade on update cascade,
  CONSTRAINT `FK2_music_id` FOREIGN KEY (`music_id`) REFERENCES `music` (`id`) on delete cascade on update cascade
) ENGINE=InnoDB AUTO_INCREMENT=11;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '真好听，', '2020-03-14 00:00:00', '1', '2');
INSERT INTO `comment` VALUES ('2', '我的最爱', '2020-02-09 00:00:00', '1', '2');
INSERT INTO `comment` VALUES ('3', '厉害了，word哥', '2020-02-09 00:00:00', '1', '2');
INSERT INTO `comment` VALUES ('4', '<p>123</p>', '2020-03-14 06:44:18', '1', '2');
INSERT INTO `comment` VALUES ('5', '<p>213&nbsp;<img src=\"http://img.baidu.com/hi/jx2/j_0002.gif\"/></p>', '2020-03-14 06:45:20', '1', '2');
INSERT INTO `comment` VALUES ('6', '<p><img src=\"http://img.baidu.com/hi/jx2/j_0062.gif\"/></p>', '2020-03-14 07:10:05', '1', '2');
INSERT INTO `comment` VALUES ('7', '<p>231</p>', '2020-03-14 08:15:42', '1', '3');
INSERT INTO `comment` VALUES ('8', '<p>test</p>', '2020-03-14 08:40:19', '1', '2');
INSERT INTO `comment` VALUES ('9', '<p>请问</p>', '2020-03-14 08:40:50', '1', '2');
INSERT INTO `comment` VALUES ('10', '<p><img src=\"http://img.baidu.com/hi/jx2/j_0002.gif\"/></p>', '2020-03-16 12:51:38', '1', '3');

-- ----------------------------
-- Table structure for musiccol
-- ----------------------------
DROP TABLE IF EXISTS `musiccol`;
CREATE TABLE `musiccol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `creat_time` datetime DEFAULT NULL,
  `music_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `music_id` (`music_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `FK1_music_id` FOREIGN KEY (`music_id`) REFERENCES `music` (`id`) on delete cascade on update cascade,
  CONSTRAINT `FK2_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) on delete cascade on update cascade
) ENGINE=InnoDB AUTO_INCREMENT=4;

-- ----------------------------
-- Records of musiccol
-- ----------------------------
INSERT INTO `musiccol` VALUES ('1', '2020-03-14 19:40:55', '1', '1');
INSERT INTO `musiccol` VALUES ('2', '2020-03-14 19:42:50', '2', '1');
INSERT INTO `musiccol` VALUES ('3', null, '3', '1');

-- ----------------------------
-- Table structure for userlog
-- ----------------------------
DROP TABLE IF EXISTS `userlog`;
CREATE TABLE `userlog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) DEFAULT NULL,
  `add_time` datetime(6) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) on delete cascade on update cascade
) ENGINE=InnoDB AUTO_INCREMENT=2;

-- ----------------------------
-- Records of userlog
-- ----------------------------
INSERT INTO `userlog` VALUES ('1', '0:0:0:0:0:0:0:1', null, '1');
