package com.example.SprintMaster.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SprintMaster.model.Jira;

@Repository
public interface JiraRepository extends JpaRepository<Jira, Integer> {

	Jira findByJiraId(String jiraId);

}
