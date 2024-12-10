/*
 Navicat Premium Data Transfer

 Source Server         : 666
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : com_net

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 10/12/2024 21:43:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_comments
-- ----------------------------
DROP TABLE IF EXISTS `course_comments`;
CREATE TABLE `course_comments`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2128441346 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_comments
-- ----------------------------
INSERT INTO `course_comments` VALUES (-2116255742, 1, 1, 'haha', '2024-11-27 16:55:25');
INSERT INTO `course_comments` VALUES (-2002943999, 1, NULL, '这位更是糕手\n', '2024-11-24 16:24:56');
INSERT INTO `course_comments` VALUES (-1885548543, 4, 1, '这么有实力\n', '2024-12-02 22:22:52');
INSERT INTO `course_comments` VALUES (-1780649983, 3, 3, 'nbnb\n', '2024-11-29 16:05:53');
INSERT INTO `course_comments` VALUES (-1642278911, 2, NULL, '哈哈哈\n', '2024-11-25 21:58:53');
INSERT INTO `course_comments` VALUES (-1365413886, 3, 3, '我趣，这么有实力\n', '2024-11-29 16:05:59');
INSERT INTO `course_comments` VALUES (-1340239871, 2, NULL, 'nbnb\n', '2024-11-24 22:43:50');
INSERT INTO `course_comments` VALUES (-1331933183, 3, NULL, '欸嘿\n', '2024-11-25 13:12:24');
INSERT INTO `course_comments` VALUES (-1260625918, 2, NULL, '666', '2024-11-24 15:03:43');
INSERT INTO `course_comments` VALUES (-1243779071, 2, 3, 'six * 3\n', '2024-11-29 16:06:09');
INSERT INTO `course_comments` VALUES (-1201876990, 9, 1, '66\n', '2024-11-27 20:13:51');
INSERT INTO `course_comments` VALUES (-1159942142, 2, 3, '666\n', '2024-11-26 22:43:47');
INSERT INTO `course_comments` VALUES (-1159876606, 2, 1, '66\n', '2024-11-26 10:38:21');
INSERT INTO `course_comments` VALUES (-1042452478, 2, NULL, '666\n', '2024-11-25 16:51:14');
INSERT INTO `course_comments` VALUES (-1025748990, 6, NULL, '哦豁\n', '2024-11-25 13:14:56');
INSERT INTO `course_comments` VALUES (-996335614, 13, 1, '666\n', '2024-12-09 16:48:13');
INSERT INTO `course_comments` VALUES (-908279807, 3, 2, 'heihei\n', '2024-11-27 22:01:21');
INSERT INTO `course_comments` VALUES (-874700798, 3, 1, '哈哈哈\n', '2024-11-26 20:51:59');
INSERT INTO `course_comments` VALUES (-874663934, 1, 2, 'nbnb\n', '2024-11-26 10:43:38');
INSERT INTO `course_comments` VALUES (-748871679, 3, NULL, '哦豁\n', '2024-11-26 20:52:17');
INSERT INTO `course_comments` VALUES (-685977599, 1, 1, '3', '2024-11-27 20:06:18');
INSERT INTO `course_comments` VALUES (-564350974, 5, 3, '666\n', '2024-11-26 22:58:52');
INSERT INTO `course_comments` VALUES (-526602239, 9, 3, '无\n', '2024-11-26 22:44:51');
INSERT INTO `course_comments` VALUES (-514011135, 1, 1, '2\n', '2024-11-27 20:06:11');
INSERT INTO `course_comments` VALUES (-488849407, 2, 2, 'oh\n', '2024-11-27 22:01:09');
INSERT INTO `course_comments` VALUES (-413298686, 4, NULL, '嘿嘿\n', '2024-11-24 22:54:22');
INSERT INTO `course_comments` VALUES (-354635774, 11, 1, '好看\n', '2024-11-27 20:50:31');
INSERT INTO `course_comments` VALUES (-304246782, 4, NULL, '无语\n', '2024-11-24 22:54:20');
INSERT INTO `course_comments` VALUES (-253898750, 10, 2, 'what can i say\n', '2024-12-02 21:30:23');
INSERT INTO `course_comments` VALUES (-174292990, 1, 1, '1\n', '2024-11-27 17:10:13');
INSERT INTO `course_comments` VALUES (-35864575, 4, 2, '我趣\n', '2024-11-27 22:01:26');
INSERT INTO `course_comments` VALUES (1, 1, 2, '这门课很有帮助，老师讲解得很好！', '2024-11-24 14:25:46');
INSERT INTO `course_comments` VALUES (2, 1, 3, '课程内容丰富，值得学习。', '2024-11-24 14:25:46');
INSERT INTO `course_comments` VALUES (3, 2, 1, '物理实验很有趣，老师讲解得很清晰。', '2024-11-24 14:25:46');
INSERT INTO `course_comments` VALUES (4, 3, 2, '有机化学的内容很深奥，但老师讲得很明白。', '2024-11-24 14:25:46');
INSERT INTO `course_comments` VALUES (5, 4, 3, '英语课程提高了我的交流能力。', '2024-11-24 14:25:46');
INSERT INTO `course_comments` VALUES (69017601, 3, NULL, '6\n', '2024-11-26 20:52:45');
INSERT INTO `course_comments` VALUES (241008642, 5, NULL, '来早了\n', '2024-11-24 16:24:36');
INSERT INTO `course_comments` VALUES (475836417, 6, 2, '11\n', '2024-11-29 10:25:35');
INSERT INTO `course_comments` VALUES (509370369, 5, NULL, '我趣，这是我同学\n', '2024-11-24 14:41:39');
INSERT INTO `course_comments` VALUES (601665537, 5, 3, '111\n', '2024-11-26 22:55:41');
INSERT INTO `course_comments` VALUES (605913090, 3, NULL, '这么有实力\n', '2024-11-24 16:20:49');
INSERT INTO `course_comments` VALUES (656191489, 6, 2, '666\n', '2024-11-26 22:22:37');
INSERT INTO `course_comments` VALUES (656195586, 1, 2, 'damn\n', '2024-11-27 22:01:16');
INSERT INTO `course_comments` VALUES (672960513, 3, 1, '111\n', '2024-12-09 11:41:11');
INSERT INTO `course_comments` VALUES (1054629889, 2, NULL, '好哇好\n', '2024-11-24 15:03:47');
INSERT INTO `course_comments` VALUES (1071407106, 1, NULL, '赞同楼下\n', '2024-11-24 14:26:16');
INSERT INTO `course_comments` VALUES (1071476737, 6, 3, '六六六\n', '2024-11-29 16:06:21');
INSERT INTO `course_comments` VALUES (1100759041, 5, 1, '哈哈\n', '2024-11-25 23:46:52');
INSERT INTO `course_comments` VALUES (1109229569, 1, NULL, 'nbnb\n', '2024-11-24 16:30:20');
INSERT INTO `course_comments` VALUES (1113350145, 3, NULL, '难，太难了\n', '2024-11-24 15:05:08');
INSERT INTO `course_comments` VALUES (1151148034, 3, 1, '666\n', '2024-11-26 20:53:25');
INSERT INTO `course_comments` VALUES (1197285378, 9, 1, '666\n', '2024-12-09 16:14:18');
INSERT INTO `course_comments` VALUES (1314676737, 1, NULL, '好听！\n', '2024-11-24 14:26:07');
INSERT INTO `course_comments` VALUES (1318940674, 11, 3, '。。。\n', '2024-11-29 16:06:31');
INSERT INTO `course_comments` VALUES (1339867137, 9, 2, '。\n', '2024-11-27 22:01:35');
INSERT INTO `course_comments` VALUES (1406943233, 1, 1, '666\n', '2024-11-25 23:45:15');
INSERT INTO `course_comments` VALUES (1453162497, 3, NULL, 'nbnb\n', '2024-11-24 16:20:45');
INSERT INTO `course_comments` VALUES (1574760450, 9, 1, '有无id\n', '2024-11-25 23:08:16');
INSERT INTO `course_comments` VALUES (1633492993, 4, 1, '啊哈\n', '2024-11-26 20:51:01');
INSERT INTO `course_comments` VALUES (1771876354, 2, 3, 'nbnb\n', '2024-11-26 22:43:50');
INSERT INTO `course_comments` VALUES (1788682242, 13, 1, '777\n', '2024-12-09 16:48:15');
INSERT INTO `course_comments` VALUES (1809604609, 2, NULL, '支持楼下\n', '2024-11-24 15:03:54');
INSERT INTO `course_comments` VALUES (1843154946, 6, NULL, '6\n', '2024-11-25 13:14:59');
INSERT INTO `course_comments` VALUES (1880932353, 3, 1, '666\n', '2024-11-27 23:22:19');
INSERT INTO `course_comments` VALUES (1943851009, 10, 1, '无\n', '2024-11-27 20:14:03');
INSERT INTO `course_comments` VALUES (1969074177, 1, NULL, '6\n', '2024-11-26 10:59:33');
INSERT INTO `course_comments` VALUES (2019344385, 5, 2, '嘿嘿嘿\n', '2024-11-27 22:01:30');
INSERT INTO `course_comments` VALUES (2120011778, 2, 1, '777\n', '2024-11-27 20:05:52');
INSERT INTO `course_comments` VALUES (2128441345, 6, 3, '222\n', '2024-11-29 16:06:17');

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `teacher_id` int(11) NULL DEFAULT NULL,
  `syllabus` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `resources` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES (1, '数学', '芝士一本数学书', NULL, NULL, NULL, '理论', 'http://127.0.0.1:5678/1732423296473_math.png', '2024-11-24 12:41:36', '2024-11-24 12:41:36');
INSERT INTO `courses` VALUES (2, '干货课堂', '芝士干货课堂', NULL, NULL, NULL, '计算机', 'http://127.0.0.1:5678/1732423362254_ghkt.png', '2024-11-24 12:42:42', '2024-11-24 12:42:42');
INSERT INTO `courses` VALUES (3, '押题', '。。。。。。。。。', NULL, NULL, NULL, '理论', 'http://127.0.0.1:5678/1732423383109_3.png', '2024-11-24 12:43:03', '2024-11-24 12:43:03');
INSERT INTO `courses` VALUES (4, '名师', '顶顶顶顶顶顶顶顶顶顶顶顶', NULL, NULL, NULL, '计算机', 'http://127.0.0.1:5678/1732423399828_2.png', '2024-11-24 12:43:19', '2024-11-24 12:43:19');
INSERT INTO `courses` VALUES (5, '沙漠一灰', '水水水水水水水水水水水水水水水水水', NULL, NULL, NULL, '理论', 'http://127.0.0.1:5678/1732423421058_sand.png', '2024-11-24 12:43:41', '2024-11-24 12:43:41');
INSERT INTO `courses` VALUES (6, '计算机', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈', NULL, NULL, NULL, '计算机', 'http://127.0.0.1:5678/1732511689819_1.png', '2024-11-25 13:14:49', '2024-11-25 13:14:49');
INSERT INTO `courses` VALUES (9, '沙漠二灰', '如何评价', NULL, NULL, NULL, '理论', 'http://127.0.0.1:5678/1732540820036_sand.png', '2024-11-25 21:20:20', '2024-11-25 21:20:20');
INSERT INTO `courses` VALUES (10, '沙漠三灰', '有无cf？', NULL, NULL, NULL, '理论', 'http://127.0.0.1:5678/1732632257212_sand.png', '2024-11-26 22:44:17', '2024-11-26 22:44:17');
INSERT INTO `courses` VALUES (11, '低等数学', '芝士低等数学', NULL, NULL, NULL, '理论', 'http://127.0.0.1:5678/1732632282183_math.png', '2024-11-26 22:44:42', '2024-11-26 22:44:42');
INSERT INTO `courses` VALUES (12, '夕阳西下', 'what can i say , man !', NULL, NULL, NULL, '计算机', 'http://127.0.0.1:5678/1733387907198_top.png', '2024-12-05 16:38:27', '2024-12-05 16:38:27');
INSERT INTO `courses` VALUES (13, 'sky', 'fdnskfngmbfm,dn', NULL, NULL, NULL, '计算机', 'http://127.0.0.1:5678/1733493742088_sky.png', '2024-12-06 22:02:22', '2024-12-06 22:02:22');

-- ----------------------------
-- Table structure for discussion_questions
-- ----------------------------
DROP TABLE IF EXISTS `discussion_questions`;
CREATE TABLE `discussion_questions`  (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1998426115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discussion_questions
-- ----------------------------
INSERT INTO `discussion_questions` VALUES (-1327710206, NULL, NULL, '还是后端简单点，大家觉得怎么样？', '2024-11-24 22:09:57');
INSERT INTO `discussion_questions` VALUES (-136474623, NULL, NULL, '666', '2024-11-24 22:43:18');
INSERT INTO `discussion_questions` VALUES (1, 1, 2, '请问这节课的重点是什么？', '2024-11-24 12:59:09');
INSERT INTO `discussion_questions` VALUES (2, 1, 3, '这个问题能否再详细讲解一下？', '2024-11-24 12:59:09');
INSERT INTO `discussion_questions` VALUES (3, 2, 1, '如何理解牛顿三大定律？', '2024-11-24 12:59:09');
INSERT INTO `discussion_questions` VALUES (4, 3, 2, '有机化合物的命名规则是什么？', '2024-11-24 12:59:09');
INSERT INTO `discussion_questions` VALUES (5, 4, 3, '我们可以在哪些场合使用英语？', '2024-11-24 12:59:09');
INSERT INTO `discussion_questions` VALUES (39686146, NULL, NULL, '这么有实力？', '2024-11-26 21:31:07');
INSERT INTO `discussion_questions` VALUES (98353153, NULL, NULL, '前端为什么这么难呢？', '2024-11-24 22:09:13');
INSERT INTO `discussion_questions` VALUES (161320962, NULL, NULL, 'nbnb', '2024-11-24 22:49:51');
INSERT INTO `discussion_questions` VALUES (970813442, NULL, NULL, '?', '2024-11-25 16:25:12');
INSERT INTO `discussion_questions` VALUES (1713156098, NULL, 3, '如何评价？', '2024-11-26 22:56:06');
INSERT INTO `discussion_questions` VALUES (1998426114, NULL, NULL, '如何评价？', '2024-11-24 22:40:22');

-- ----------------------------
-- Table structure for discussions
-- ----------------------------
DROP TABLE IF EXISTS `discussions`;
CREATE TABLE `discussions`  (
  `discussion_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`discussion_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2128449539 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discussions
-- ----------------------------
INSERT INTO `discussions` VALUES (-2107846655, 2, 2, '同意楼下', '2024-11-27 20:25:38');
INSERT INTO `discussions` VALUES (-2086875134, 3, 1, '666', '2024-11-27 20:26:26');
INSERT INTO `discussions` VALUES (-2070130687, -136474623, NULL, '我趣', '2024-11-25 13:13:33');
INSERT INTO `discussions` VALUES (-1965182974, 161320962, NULL, '什么', '2024-11-26 10:40:15');
INSERT INTO `discussions` VALUES (-1868771326, 1, 1, '666', '2024-11-27 20:25:00');
INSERT INTO `discussions` VALUES (-1864536063, 970813442, NULL, '怎么了', '2024-11-25 16:25:24');
INSERT INTO `discussions` VALUES (-1482846207, -1327710206, NULL, '赞同楼下', '2024-11-24 22:51:21');
INSERT INTO `discussions` VALUES (-1453567998, 4, NULL, '我咋知道', '2024-11-25 13:13:51');
INSERT INTO `discussions` VALUES (-1394814974, 1, 2, '777', '2024-11-27 20:25:11');
INSERT INTO `discussions` VALUES (-1130524670, 1998426114, NULL, 'hhh', '2024-11-24 22:42:56');
INSERT INTO `discussions` VALUES (-1097019391, 1713156098, 1, '不敢评价', '2024-11-27 20:19:25');
INSERT INTO `discussions` VALUES (-983724030, 1, NULL, '没什么重点', '2024-11-24 23:00:11');
INSERT INTO `discussions` VALUES (-916615167, -1327710206, NULL, '都不会', '2024-11-24 22:51:14');
INSERT INTO `discussions` VALUES (-878866431, 5, NULL, 'America', '2024-11-24 22:59:56');
INSERT INTO `discussions` VALUES (-723726334, 3, 1, '力学好像', '2024-11-27 20:26:01');
INSERT INTO `discussions` VALUES (-711143423, 970813442, NULL, '哦豁', '2024-11-25 22:42:01');
INSERT INTO `discussions` VALUES (-685928447, -136474623, NULL, '6牛魔呢', '2024-11-24 23:01:14');
INSERT INTO `discussions` VALUES (-547565567, 5, 2, 'Of cause American', '2024-11-27 20:27:34');
INSERT INTO `discussions` VALUES (-295915518, 1713156098, 3, '不好评价', '2024-11-26 22:56:14');
INSERT INTO `discussions` VALUES (1, 1, 1, '这节课的重点是极限与连续，大家有什么问题吗？', '2024-11-24 12:59:09');
INSERT INTO `discussions` VALUES (2, 2, 1, '关于这个问题，我认为可以从多个角度来分析。', '2024-11-24 12:59:09');
INSERT INTO `discussions` VALUES (3, 3, 1, '牛顿的第一定律是指什么？', '2024-11-24 12:59:09');
INSERT INTO `discussions` VALUES (4, 4, 2, '命名有机化合物需要遵循哪些规则？', '2024-11-24 12:59:09');
INSERT INTO `discussions` VALUES (5, 5, 3, '我们可以在国际会议上使用英语。', '2024-11-24 12:59:09');
INSERT INTO `discussions` VALUES (228352001, 1713156098, 2, 'qqq', '2024-12-05 18:22:38');
INSERT INTO `discussions` VALUES (333238273, 3, 2, '运动学吧', '2024-11-27 20:26:18');
INSERT INTO `discussions` VALUES (484233217, 1713156098, 1, '666', '2024-11-27 20:20:06');
INSERT INTO `discussions` VALUES (547155970, 1, NULL, '没问题', '2024-11-26 21:48:17');
INSERT INTO `discussions` VALUES (568102913, 4, 1, '111', '2024-11-28 16:44:42');
INSERT INTO `discussions` VALUES (584892417, 5, 2, 'It is so difficult to learn English for me', '2024-11-27 22:00:15');
INSERT INTO `discussions` VALUES (786223105, 2, 1, '不会呀这', '2024-11-27 20:25:25');
INSERT INTO `discussions` VALUES (857575426, -1327710206, NULL, '哎哎哎', '2024-11-24 22:52:18');
INSERT INTO `discussions` VALUES (1184731138, 2, NULL, '楼下能不能详细解答？', '2024-11-24 23:01:00');
INSERT INTO `discussions` VALUES (1268617217, 3, NULL, '好难', '2024-11-24 22:59:45');
INSERT INTO `discussions` VALUES (1331531778, 1, NULL, '不会呀', '2024-11-24 22:59:15');
INSERT INTO `discussions` VALUES (1356648450, 5, 1, 'Is the English downstairs so good?', '2024-11-27 20:28:03');
INSERT INTO `discussions` VALUES (1369223170, 4, 2, '666', '2024-11-26 22:26:09');
INSERT INTO `discussions` VALUES (1524428801, 1, 1, 'hhh', '2024-11-26 21:49:07');
INSERT INTO `discussions` VALUES (1574793218, -1327710206, NULL, '666', '2024-11-25 16:51:31');
INSERT INTO `discussions` VALUES (1721602050, 98353153, NULL, '我也觉得难', '2024-11-24 22:59:25');
INSERT INTO `discussions` VALUES (1918734337, 1998426114, NULL, '不好评价', '2024-11-24 22:40:29');
INSERT INTO `discussions` VALUES (2015121409, -1327710206, NULL, '呜呜呜', '2024-11-25 17:34:58');
INSERT INTO `discussions` VALUES (2069676034, 1713156098, 2, '评价社么', '2024-11-27 21:59:35');
INSERT INTO `discussions` VALUES (2128449538, 3, NULL, '不太理解', '2024-11-24 22:59:38');

-- ----------------------------
-- Table structure for exam_answers
-- ----------------------------
DROP TABLE IF EXISTS `exam_answers`;
CREATE TABLE `exam_answers`  (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `score` decimal(5, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1172127746 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_answers
-- ----------------------------
INSERT INTO `exam_answers` VALUES (-367259646, -2116198399, 2, '不会写呀这', '2024-11-29 12:18:38', 6.00);
INSERT INTO `exam_answers` VALUES (1, 1, 1, '选择 A, B, C', '2024-11-28 20:23:15', 10.00);
INSERT INTO `exam_answers` VALUES (2, 1, 2, '选择 B, D', '2024-11-28 20:23:15', 20.00);
INSERT INTO `exam_answers` VALUES (3, 2, 1, '选择 A', '2024-11-28 20:23:15', 66.00);
INSERT INTO `exam_answers` VALUES (4, 3, 2, '选择 C, D', '2024-11-28 20:23:15', NULL);
INSERT INTO `exam_answers` VALUES (5, 1, 3, '选择 A, C', '2024-11-28 20:23:15', 10.00);
INSERT INTO `exam_answers` VALUES (249368578, -2116198399, 1, 'return ans.sort(ans.begin,ans.end);', '2024-11-28 22:39:00', 1.00);
INSERT INTO `exam_answers` VALUES (291360769, 2, 2, '选b', '2024-12-02 20:19:52', NULL);
INSERT INTO `exam_answers` VALUES (1172127745, -2116198399, 6, 'int c = a + b;\nreturn c;', '2024-12-08 14:28:54', NULL);

-- ----------------------------
-- Table structure for exams
-- ----------------------------
DROP TABLE IF EXISTS `exams`;
CREATE TABLE `exams`  (
  `exam_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`exam_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1929219 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exams
-- ----------------------------
INSERT INTO `exams` VALUES (-2116198399, 1, 'C++期中考试', '请写出一个冒泡排序', '2024-11-28 22:09:32');
INSERT INTO `exams` VALUES (1, 1, '数学期末考试', '本次考试包含选择题和填空题，请认真作答。', '2024-11-28 20:23:12');
INSERT INTO `exams` VALUES (2, 2, '英语听力测试', '请在规定时间内完成听力理解部分。', '2024-11-28 20:23:12');

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers`  (
  `teacher_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachers
-- ----------------------------
INSERT INTO `teachers` VALUES (6, NULL, '发哥', '无敌', 'http://127.0.0.1:5678/1732695941916_images.jpg', '2024-11-27 16:25:42', '2024-11-27 16:25:42');
INSERT INTO `teachers` VALUES (7, NULL, '发神', '这位更是小黑子', 'http://127.0.0.1:5678/1732716136112_images.jpg', '2024-11-27 22:02:16', '2024-11-27 22:02:16');
INSERT INTO `teachers` VALUES (8, NULL, '坤哥', '我只会打篮球', 'http://127.0.0.1:5678/1732716220919_Basketball.png', '2024-11-27 22:03:40', '2024-11-27 22:03:40');
INSERT INTO `teachers` VALUES (9, NULL, '坤弟', '我不会打篮球', 'http://127.0.0.1:5678/1732716241655_Basketball.png', '2024-11-27 22:04:01', '2024-11-27 22:04:01');
INSERT INTO `teachers` VALUES (40, NULL, '王老师', '我是王老师', 'http://127.0.0.1:5678/1733837850389_images.jpg', '2024-12-10 21:37:30', '2024-12-10 21:37:30');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `passwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` enum('teacher','student','admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `old_passwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'fage', '123456', '张三', 'teacher', '2024-11-25 15:52:16', '2024-11-27 21:40:35', NULL);
INSERT INTO `users` VALUES (2, 'haha', '123456', '李四', 'student', '2024-11-25 15:52:16', '2024-11-25 15:52:16', NULL);
INSERT INTO `users` VALUES (3, 'zaizi', '123456', '王五', 'student', '2024-11-25 15:52:16', '2024-11-25 15:52:16', NULL);
INSERT INTO `users` VALUES (4, 'hahaha', '123456', '哈哈哈', 'student', '2024-11-25 21:28:14', '2024-11-25 21:28:14', NULL);
INSERT INTO `users` VALUES (5, 'aaa', '123456', 'aaa', 'teacher', '2024-11-27 16:57:51', '2024-11-27 16:57:51', NULL);
INSERT INTO `users` VALUES (6, 'b', '123456', '芜湖', 'student', '2024-12-08 14:19:42', '2024-12-08 14:19:42', NULL);
INSERT INTO `users` VALUES (7, 'ccc', '123456', '桀桀桀', 'student', '2024-12-09 21:44:08', '2024-12-09 21:44:08', NULL);
INSERT INTO `users` VALUES (8, 'ddd', 'ddd', 'ddd', 'student', '2024-12-09 22:01:47', '2024-12-09 22:01:47', NULL);
INSERT INTO `users` VALUES (9, 'eee', 'eee', 'eee', 'admin', '2024-12-10 21:19:51', '2024-12-10 21:19:51', NULL);

SET FOREIGN_KEY_CHECKS = 1;
