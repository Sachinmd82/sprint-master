package com.example.SprintMaster.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.SprintMaster.model.Employee;

@Service
public class CacheService {
	public static Map<String, String> userNameToPasswordMap = new HashMap<>();
	
	public void saveAdminUser(String username, String password) {
		userNameToPasswordMap.put(username, password);		
	}

	public String getAdminUser(String username) {
		return userNameToPasswordMap.get(username);
		
	}
	
	public void addAllAdminUsers(List<Employee> list) {
		for(Employee obj : list) {
			userNameToPasswordMap.put(obj.getName(), obj.getPassword());	
		}
		
	}

}
