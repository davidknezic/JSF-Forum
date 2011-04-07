--
-- Database content
--

USE test;

--
-- user
--
INSERT INTO user (userId, username, password, email, active)
VALUES
	(1, 'david', MD5('david'), 'me@davidknezic.com', 1),
	(2, 'marcello', MD5('marcello'), 'marcel@bloeckli.ch', 1),
	(3, 'eniu', MD5('eniu'), 'forum@eniu.ch', 1),
	(4, 'blocked', MD5('blocked'), 'block@me.now', 0),
	(5, 'master', MD5('master'), 'master@master.xyz', 1);

--
-- category
--
INSERT INTO category (categoryId, title)
VALUES
	(1, 'General'),
	(2, 'Coding'),
	(3, 'Programming');

--
-- board
--
INSERT INTO board (boardId, categoryId, title, description)
VALUES
	(1, 1, 'General','General questions'),
	(2, 2, '(X)HTML','Structuring websites'),
	(4, 2, 'CSS','Styling websites'),
	(5, 3, 'PHP','Hypertext Preprocessor'),
	(6, 3, 'JSF','Java Server Faces');

--
-- thread
--
INSERT INTO thread (threadId, boardId, userId, title, content, createdOn)
VALUES
	(2, 1, 1, 'Do you like this forum?',
	'So, what do you say? Do you like the result of this project?',
	'2011-04-04 18:35:24'),
	(3, 1, 2, 'How can I write a thread???',
	'Can anybody tell me how I can write a new thread?',
	'2011-04-05 23:08:10');

--
-- reply
--
INSERT INTO reply (replyId, threadId, userId, content, createdOn)
VALUES
	(1, 2, 3, 'I like the technology behind this forum!',
	'2011-04-05 23:09:14');

