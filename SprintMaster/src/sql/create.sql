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

alter table sprint_master.jira ADD `end_time` timestamp , 
							   ADD `start_time` timestamp;
                               
alter table sprint_master.jira modify sprint varchar(150) default NULL,
							   modify status varchar(150) default NULL,
                               modify module varchar(150) default NULL,                               
                               modify priority varchar(150) default NULL;
                               
ALTER TABLE sprint_master.employee MODIFY NAME VARCHAR(50) DEFAULT NULL;
ALTER TABLE sprint_master.employee MODIFY module VARCHAR(50) DEFAULT NULL;

alter table sprint_master.jira ADD end_time timestamp , ADD start_time timestamp;

Alter table sprint_master add due_date timestamp default null;