package com.example.SprintMaster.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class JiraDataModel {

	private String jiraId;
	private String priority;
	private String status;
	private String module;
	private String sprint;
	private Timestamp dueDate;
	private String assigneeAccId;
	private String employeeName;

}
