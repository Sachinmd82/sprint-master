package com.example.SprintMaster.Controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.SprintMaster.Service.AdminService;

@Controller
@CrossOrigin(origins ="*")
public class LoginController {
	@Autowired
	AdminService adminService;
	
	@GetMapping("/validate-user")
    public ResponseEntity<?> validateUser(@RequestHeader("Authorization") String authorizationHeader) {
    	Map<String, Object> map = new LinkedHashMap<String, Object>();
//        boolean isValidUser = adminService.validateUser(username, password);
        if (adminService.validateUser(authorizationHeader)) {
        	map.put("Status", "Success");
        	map.put("Message", "Valid User!");
        	return new ResponseEntity<>(map,HttpStatus.OK);
        } 
        map.put("Status", "Success");
    	map.put("Message", "Invalid username or password.");
    	return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);        
    }


}
