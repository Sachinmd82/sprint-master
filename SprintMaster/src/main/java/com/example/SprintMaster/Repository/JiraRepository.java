package com.example.SprintMaster.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SprintMaster.model.Jira;

@Repository
public interface JiraRepository extends JpaRepository<Jira, Integer> {

	Jira findByJiraId(String jiraId);

	List<Jira> findByEmpId(int empId);

	List<Jira> findByEmpIdAndStatus(int empId, String status);

	List<Jira> findByStatus(String status);

}
