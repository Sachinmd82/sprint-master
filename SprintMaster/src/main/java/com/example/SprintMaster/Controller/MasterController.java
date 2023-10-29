package com.example.SprintMaster.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.SprintMaster.Dto.LogDto;
import com.example.SprintMaster.Service.MasterService;
import com.example.SprintMaster.model.Logger;

@RestController
public class MasterController {
	
	@Autowired
	MasterService masterService;
	
	@PostMapping("/add-log")
	public ResponseEntity<?> addLog(@RequestBody LogDto dto){
		Logger logger=masterService.addLog(dto);
		return new ResponseEntity<>(logger,HttpStatus.OK);
	}
	
	@GetMapping("/get-statuswise-count")
	public ResponseEntity<?> getStatusWiseCount(@RequestHeader("Authorization") String authorizationHeader){
		return  masterService.getStatusWiseCount(authorizationHeader);
	}
	
	@GetMapping("/get-statuswise/{status}")
	public ResponseEntity<?>  getForStatus(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("status") String status){
		return masterService.getForStatus(authorizationHeader, status);
	}

	@GetMapping("admin/get-statuswise-count")
	public ResponseEntity<?> getAdminStatusWiseCount(){
		return  masterService.getAdminStatusWiseCount();
	}
	
	@GetMapping("admin/get-statuswise-count/{status}")
	public ResponseEntity<?> getAdminWiseForStatus(@PathVariable("status") String status){
		return  masterService.getAdminWiseForStatus(status);
	}
	
	@GetMapping("admin/get-all-emp-efficiency")
	public ResponseEntity<?> getAllEmpData(){
		return  masterService.getAllEmpDataForAdmin();
	}
}
