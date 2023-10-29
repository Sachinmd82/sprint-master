package com.example.SprintMaster.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "logger")
@Data
public class Logger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "dev_id")
	private int empId;
	
	@Column(name = "jira_id")
	private String jiraId;
	
	@Column(name = "activity_name")
	private String activityName;
	
	@Column(name = "time")
	private Timestamp time;
	
	@Column(name = "type")
	private char type;
	
}
