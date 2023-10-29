package com.example.SprintMaster.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.SprintMaster.model.Employee;

@Service
public class CacheService {
	public static Map<String, String> userNameToPasswordMap = new HashMap<>();
	public static Map<String, Integer> empIdToUserName = new HashMap<>();

	public void saveAdminUser(String username, String password) {
		userNameToPasswordMap.put(username, password);
	}

	public String getAdminUser(String username) {
		return userNameToPasswordMap.get(username);

	}

	public void addAllAdminUsers(List<Employee> list) {
		for (Employee obj : list) {
			userNameToPasswordMap.put(obj.getName(), obj.getPassword());
		}

	}

	public void mapEmpIdToUserName(List<Employee> list) {
		for (Employee obj : list) {
			empIdToUserName.put(obj.getName(), obj.getId());
		}
	}

	public int getEmployeeIdByName(String name) {
		int id = 0;
		if (name != null && name.length() > 0) {
			Integer ids = empIdToUserName.get(name);
			if(ids == null) {
				id =0;
			}else {
				return ids;
			}
		}
		return id;
	}

}
