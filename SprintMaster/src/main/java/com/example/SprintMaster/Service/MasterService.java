package com.example.SprintMaster.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.SprintMaster.Dto.Field;
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
		if (jira != null) {
			Logger logger = new Logger();
			logger.setEmpId(jira.getEmpId());
			logger.setActivityName(dto.getType());
			logger.setJiraId(jira.getJiraId());
			Timestamp timestamp = SpringUtility.getCurrentTimestamp();
			logger.setTime(timestamp);
			logger.setType('C');
			loggerRepository.save(logger);
			return logger;
		}
		return null;
	}

	public ResponseEntity<Map<String, Integer>> getStatusWiseCount(String authorizationHeader) {
		String name = adminService.getUserName(authorizationHeader);
		Employee employee = employeeRepository.findByName(name);
		int empId = employee.getId();
		List<Jira> jiras = jiraRepository.findByEmpId(empId);
		int pending = 0;
		int inProgress = 0;
		int complete = 0;
		for (Jira jira : jiras) {
			if (jira.getStatus().equals("Pending")) {
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

	public ResponseEntity<?> getForStatus(String authorizationHeader, String status) {
		Map<String, Field> result = new LinkedHashMap<>();
		String name = adminService.getUserName(authorizationHeader);
		Employee employee = employeeRepository.findByName(name);
		int empId = employee.getId();
		List<Jira> jiras = jiraRepository.findByEmpIdAndStatus(empId, status);
		for (Jira jira : jiras) {
			Timestamp st = null;
			Timestamp ed = null;
			long duration = 0;
			List<Logger> loggers = loggerRepository.findByJiraId(jira.getJiraId());
			for (Logger logger : loggers) {
				if (logger.getActivityName().equals("Pause")) {
					st = logger.getTime();
				} else if (logger.getActivityName().equals("Start") && st != null) {
					ed = logger.getTime();
				}
				if (st != null && ed != null) {
					duration += ed.getTime() - st.getTime();
					st = null;
					ed = null;
				}
			}
			Field field = new Field();
			field.setBreakTaken(SpringUtility.getDateInHHMMSS(duration));
			Timestamp jiraSt = jira.getCreateDate();
			Timestamp jiraEt = jira.getEndTime() != null ? jira.getEndTime() : SpringUtility.getCurrentTimestamp();
			long diff = jiraSt != null && jiraEt != null ? jiraEt.getTime() - jiraSt.getTime() : 0;
			field.setWorked(SpringUtility.getDateInHHMMSS(diff));
			result.put(jira.getJiraId(), field);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	public ResponseEntity<Map<String, Integer>> getAdminStatusWiseCount() {
		List<Jira> jiras = jiraRepository.findAll();
		int pending = 0;
		int inProgress = 0;
		int complete = 0;
		for (Jira jira : jiras) {
			if (jira.getStatus().equals("Pending")) {
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

	public ResponseEntity<?> getAdminWiseForStatus(String status) {
		List<Jira> jiras = jiraRepository.findByStatus(status);
		if (jiras != null && jiras.size() > 0) {
			return new ResponseEntity<List<Jira>>(jiras, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	public ResponseEntity<?> getAllEmpDataForAdmin(){
		List<Jira> jiras = jiraRepository.findAll();
		Map<Integer,Field> result = new HashMap<>();
		for (Jira jira : jiras) {
			Timestamp st = null;
			Timestamp ed = null;
			long duration = 0;
			List<Logger> loggers = loggerRepository.findByJiraId(jira.getJiraId());
			for (Logger logger : loggers) {
				if (logger.getActivityName().equals("Pause")) {
					st = logger.getTime();
				} else if (logger.getActivityName().equals("Start") && st != null) {
					ed = logger.getTime();
				}
				if (st != null && ed != null) {
					duration += ed.getTime() - st.getTime();
					st = null;
					ed = null;
				}
			}
			
			Field field = result.get(jira.getEmpId());
			if(field == null) {
				field = new Field();
				result.put(jira.getEmpId(), field);
			}
			field.setBreakTaken(duration + field.getBreakTaken());
			Timestamp jiraSt = jira.getStartTime();
			Timestamp jiraEt = jira.getEndTime() != null ? jira.getEndTime() : SpringUtility.getCurrentTimestamp();
			long diff = jiraSt != null && jiraEt != null ? jiraEt.getTime() - jiraSt.getTime() : 0;
			field.setWorked(diff + field.getWorked());
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
