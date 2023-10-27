package com.example.SprintMaster.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SprintMaster.Repository.EmployeeRepository;
import com.example.SprintMaster.model.Employee;

@Service
public class AdminService {
	@Autowired
	CacheService cacheService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public void loadAdminDataIntoCache() {
		List<Employee> list = employeeRepository.findAll();
		if (list != null && list.size() > 0) {
			cacheService.addAllAdminUsers(list);
		}
	}
	
	public boolean validateUser(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
			try {
				// Extract the Base64 encoded credentials by removing the "Basic " prefix
				String base64Credentials = authorizationHeader.substring("Basic ".length());

				// Decode the Base64 encoded credentials
				byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
				String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);

				// Extract the username and password from the decoded credentials
				String[] credentialsArray = decodedCredentials.split(":", 2);
				String username = credentialsArray[0];
				String password = credentialsArray[1];
				String encodedPassword = new String(Base64.getEncoder().encode(password.getBytes()));
				String storedPassword = cacheService.getAdminUser(username);

				// Now you have the username and password extracted from the Basic Authorization
				// header.
				// You can use them for authentication or any other processing you need.

				return encodedPassword.equals(storedPassword) ? true : false;
			} catch (Exception e) {
				// If there's an error decoding the credentials, return an error response.
				return false;
			}
		} else {
			// If the Authorization header is missing or not in the correct format, return
			// an error response.
			return false;
		}

		// Decode the stored base64 password and compare with the provided password
//        String storedPassword = new String(Base64.decode(user.getPassword().getBytes()));

	}

}
