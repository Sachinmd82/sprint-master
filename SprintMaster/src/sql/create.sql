create schema sprint_master;

CREATE TABLE `logger` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dev_id` int DEFAULT '0',
  `jira_id` int DEFAULT NULL,
  `activity_name` varchar(256) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `jira` (
  `id` int NOT NULL AUTO_INCREMENT,
  `jira_id` int DEFAULT '0',
  `priority` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `module` varchar(10) DEFAULT NULL,
  `sprint` varchar(10) DEFAULT NULL,
  `dev_id` int DEFAULT 0,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` int DEFAULT '0',
  `module` int DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `role` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

Alter table employee modify module varchar(10);


alter table sprint_master.jira modify jira_id varchar(50) default NULL;

alter table sprint_master.logger modify jira_id varchar(50) default NULL;