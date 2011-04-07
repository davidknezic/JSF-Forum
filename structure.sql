--
-- Database structure
--

DROP DATABASE IF EXISTS forum;

CREATE DATABASE forum;

USE forum;

--
-- user
--
CREATE TABLE user (
	userId		int(10) unsigned	NOT NULL AUTO_INCREMENT,
	username	varchar(16)		NOT NULL,
	password	varchar(32)		NOT NULL,
	email		varchar(100)		NOT NULL,
	active		tinyint(1)		NOT NULL DEFAULT '1',
	PRIMARY KEY (userId),
	UNIQUE KEY username (username)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- category
--
CREATE TABLE category (
	categoryId	int(10) unsigned	NOT NULL AUTO_INCREMENT,
	title		varchar(512)		NOT NULL,
	PRIMARY KEY (categoryId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- board
--
CREATE TABLE board (
	boardId		int(10) unsigned	NOT NULL AUTO_INCREMENT,
	categoryId	int(10) unsigned	NOT NULL,
	title		varchar(512)		NOT NULL,
	description	text			NOT NULL,
	PRIMARY KEY (boardId),
	KEY categoryId (categoryId),
	CONSTRAINT board_ibfk_1 FOREIGN KEY (categoryId)
	REFERENCES category (categoryId) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- thread
--
CREATE TABLE thread (
	threadId	int(10) unsigned	NOT NULL AUTO_INCREMENT,
	boardId		int(10) unsigned	NOT NULL,
	userId		int(10) unsigned	NOT NULL,
	title		varchar(512)		NOT NULL,
	content		text			NOT NULL,
	createdOn 	datetime		NOT NULL,
	PRIMARY KEY (threadId),
	KEY boardId (boardId),
	KEY userId (userId),
	CONSTRAINT thread_ibfk_1 FOREIGN KEY (boardId) REFERENCES board (boardId) ON DELETE CASCADE,
	CONSTRAINT thread_ibfk_2 FOREIGN KEY (userId) REFERENCES user (userId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- reply
--
CREATE TABLE reply (
	replyId		int(10) unsigned	NOT NULL AUTO_INCREMENT,
	threadId	int(10) unsigned	NOT NULL,
	userId		int(10) unsigned	NOT NULL,
	content		text			NOT NULL,
	createdOn	datetime		NOT NULL,
	PRIMARY KEY (replyId),
	KEY threadId (threadId, userId),
	KEY userId (userId),
	CONSTRAINT reply_ibfk_1 FOREIGN KEY (threadId)
	REFERENCES thread (threadId) ON DELETE CASCADE,
	CONSTRAINT reply_ibfk_2 FOREIGN KEY (userId)
	REFERENCES user (userId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

