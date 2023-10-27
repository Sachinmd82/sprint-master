package com.example.SprintMaster.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Jira {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="jira_id")
	private int jiraId;
	
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
	
}
