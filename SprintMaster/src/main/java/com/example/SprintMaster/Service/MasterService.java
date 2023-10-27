package com.example.SprintMaster.Service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SprintMaster.Dto.LogDto;
import com.example.SprintMaster.Repository.JiraRepository;
import com.example.SprintMaster.Repository.LoggerRepository;
import com.example.SprintMaster.Utils.SpringUtility;
import com.example.SprintMaster.model.Jira;
import com.example.SprintMaster.model.Logger;

@Service
public class MasterService {
	
	@Autowired
	JiraRepository jiraRepository;
	
	@Autowired
	LoggerRepository loggerRepository;

	public Logger addLog(LogDto dto) {
		Jira jira = jiraRepository.findByJiraId(dto.getJiraId());
		Logger logger = new Logger();
		logger.setEmpId(jira.getEmpId());
		logger.setActivityName(dto.getType());
		logger.setJiraId(jira.getJiraId());
		Timestamp timestamp = SpringUtility.getCurrentTimestamp();
		logger.setTime(timestamp);
		loggerRepository.save(logger);
		return logger;
	}

}
