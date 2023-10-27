package com.example.SprintMaster.Service;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.SprintMaster.Dto.LogDto;
import com.example.SprintMaster.Repository.EmployeeRepository;
import com.example.SprintMaster.Repository.JiraRepository;
import com.example.SprintMaster.Repository.LoggerRepository;
import com.example.SprintMaster.Utils.SpringUtility;
import com.example.SprintMaster.model.Employee;
import com.example.SprintMaster.model.Jira;
import com.example.SprintMaster.model.Logger;

@Service
public class MasterService {
	
	@Autowired
	JiraRepository jiraRepository;
	
	@Autowired
	LoggerRepository loggerRepository;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	EmployeeRepository employeeRepository;

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

	public ResponseEntity<Map<String, Integer>> getStatusWiseCount(String authorizationHeader) {
		String name = adminService.getUserName(authorizationHeader);
		Employee employee = employeeRepository.findByName(name);
		int empId = employee.getId();
		List<Jira> jiras = jiraRepository.findByEmpId(empId);
		int pending = 0;
		int inProgress = 0;
		int complete = 0;
		for(Jira jira : jiras) {
			if(jira.getStatus().equals("Pending")) {
				pending += 1;
			} else if (jira.getStatus().equals("InProgress")) {
				inProgress += 1;
			} else {
				complete += 1;
			}
		}
		
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		result.put("Pending", pending);
		result.put("InProgress", inProgress);
		result.put("Complete", complete);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	public ResponseEntity<?> getForStatus(String authorizationHeader, char status) {
		String name = adminService.getUserName(authorizationHeader);
		Employee employee = employeeRepository.findByName(name);
		int empId = employee.getId();
		return null;
	}

}
