package com.example.SprintMaster.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.SprintMaster.model.Logger;

@Repository
public class LoggerDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Map<String,List<Logger>> getLogsForJiraId(String empIds) {
		Map<String, List<Logger>> result = null;
		 Session session = sessionFactory.openSession();
		 String sql ="from Logger where ";
		 if(empIds!=null && "".equals(empIds)) {
			 sql+=" dev_id in ("+empIds+")";
		 }
		 sql+=" order by id";
		 TypedQuery<Logger> query = session.createQuery(sql, Logger.class);
		 List<Logger> logs = query.getResultList();
		 if(logs!=null && logs.size()>0) {
			 result = logs.parallelStream().collect(Collectors.groupingBy(Logger::getJiraId));
		 }
		 return result;
	}

}
