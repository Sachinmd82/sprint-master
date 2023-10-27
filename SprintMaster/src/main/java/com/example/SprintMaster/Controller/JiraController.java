package com.example.SprintMaster.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SprintMaster.Service.JiraService;

@RestController
public class JiraController {
	
	@Autowired 
	private JiraService jiraService;
	
	@GetMapping("/get-all-issue-by-sprint-name")
	private ResponseEntity<?> getAllIssueBySprintname(@RequestParam String sprintName){
		return jiraService.getAllIssueBySprintName(sprintName);
	}
	
	@PutMapping("/update-story-point")
	private ResponseEntity<?> updateStoryPoint(@RequestParam String jiraId, @RequestParam int storyPoint){
		return jiraService.updateStoryPoint(jiraId,storyPoint);
	}

}
