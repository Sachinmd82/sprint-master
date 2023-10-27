package com.example.SprintMaster.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SprintMaster.Repository.LoggerDao;
import com.example.SprintMaster.Utils.SpringUtility;
import com.example.SprintMaster.model.Logger;

@Service
public class LoggerService {

	@Autowired
	LoggerDao loggerDao;
	
	public Map<String,Map<String,Long>> getEmpSprintData(String jiraIds){
		Map<String,Map<String,Long>> map = new HashMap<>();
		Map<String,List<Logger>> jiraLogs = loggerDao.getLogsForJiraId(jiraIds);
		if(jiraLogs!=null && jiraLogs.size()>0) {
			for(String jiraId:jiraLogs.keySet()) {
				List<Logger> list = jiraLogs.get(jiraId);
				long totTime = 0,totBreakTime = 0;
				Logger start = null,end = null;
				Logger breakStartTime = null;
				for(Logger logger:list) {
					if(logger.getActivityName().equals(SpringUtility.IN_PROGRESS)) {
						start = logger;
					}else if(logger.getActivityName().equals(SpringUtility.DONE)) {
						end = logger;
					}else if(logger.getActivityName().equals(SpringUtility.BREAK_START)) {
						breakStartTime = logger;
					}else if(breakStartTime!=null && logger.getActivityName().equals(SpringUtility.BREAK_END)){
						long breakTime = logger.getTime().getTime() - breakStartTime.getTime().getTime();
						totBreakTime += breakTime;
					}
				}
				totTime = end.getTime().getTime() - start.getTime().getTime();
				Map<String,Long> colToTime = new HashMap<>();
				colToTime.put("Total Time Taken", totTime);
				colToTime.put("Total Break Time", totBreakTime);
				map.put(jiraId, colToTime);
			}
		}
		return map;
	}
}
