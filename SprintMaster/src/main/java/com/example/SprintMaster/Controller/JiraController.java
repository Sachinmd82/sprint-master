package com.example.SprintMaster.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SprintMaster.Service.JiraService;

@RestController
@CrossOrigin(origins ="*")
public class JiraController {
	
	@Autowired 
	private JiraService jiraService;
	
	@GetMapping("/get-all-issue-by-sprint-name")
	private ResponseEntity<?> getAllIssueBySprintname(@RequestParam String sprintName, @RequestParam String apiToken){
		return jiraService.getAllIssueBySprintName(sprintName, apiToken);
	}
	
	@PutMapping("/update-story-point")
	private ResponseEntity<?> updateStoryPoint(@RequestParam String jiraId, @RequestParam int storyPoint, @RequestParam String apiToken){
		return jiraService.updateStoryPoint(jiraId,storyPoint, apiToken);
	}

}
