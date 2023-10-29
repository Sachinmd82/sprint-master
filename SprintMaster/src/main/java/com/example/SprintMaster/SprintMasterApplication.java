package com.example.SprintMaster;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.SprintMaster.Repository.JiraRepository;
import com.example.SprintMaster.model.Jira;

@SpringBootApplication
@ComponentScan("com.example.SprintMaster")
public class SprintMasterApplication implements CommandLineRunner {
	
	@Autowired
	JiraRepository jiraRepo;

	public static void main(String[] args) {
		SpringApplication.run(SprintMasterApplication.class, args);
		System.out.println("sdffvdfdfvdvdv");
	}

	@Override
	public void run(String... args) throws Exception {
		for(int i=1;i<=50;i++) {
			Jira jira = new Jira();
			jira.setCreateDate(new Timestamp(System.currentTimeMillis()));
			jira.setPriority("p1");
			jira.setJiraId("jira_"+i);
			jira.setModule("module"+(i%3));
			jira.setSprint("sprint_"+((i+1)%3));
			jira.setEmpId(i%2);
			if(i%3 == 1) {
				jira.setStatus("Pending");
			}else if(i%3==2) {
				jira.setStatus("InProgress");
			}else {
				jira.setStatus("Complete");
			}
			jiraRepo.save(jira);
		}
	}
	

}
