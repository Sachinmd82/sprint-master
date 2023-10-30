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
@Data
@Table(name = "jira")
public class Jira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="jira_id")
	private String jiraId;
	
	@Column
	private String priority;
	
	@Column
	private String status;
	
	@Column
	private String module;
	
	@Column(name="dev_id")
	private int empId;
	
	@Column
	private String sprint;
	
	@Column(name="created_time")
	private Timestamp createDate;
	
	@Column(name="start_time")
	private Timestamp startTime;
	
	@Column(name="end_time")
	private Timestamp endTime;
	
	@Column(name ="due_date")
	private Timestamp dueDate;
	
}
