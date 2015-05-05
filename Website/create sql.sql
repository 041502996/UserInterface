CREATE DATABASE character_repository;

USE character_repository;

CREATE TABLE `character_information` (
  `char_id` 	INT(11) 		NOT NULL	PRIMARY KEY	AUTO_INCREMENT	UNIQUE,
  `char_title` 	VARCHAR(50) 	NOT NULL,
  `char_md5` 	VARCHAR(32) 	NOT NULL,
  `char_icon` 	VARCHAR(1000)	NOT NULL,
  `char_good` 	VARCHAR(1) 		NOT NULL,
  `char_vid` 	VARCHAR(1000)	NOT NULL,
)