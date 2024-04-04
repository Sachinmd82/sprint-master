package com.example.SprintMaster.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SprintMaster.Service.BitbucketService;

@RestController
@CrossOrigin(origins = "*")
public class BitbucketController {

    @Autowired
    BitbucketService bitbucketService;

    @GetMapping("/get-access-token")
    private ResponseEntity<?> getAccessToken() {
        return bitbucketService.getAccessToken();
    }
    
    @GetMapping("/get-all-users")
    private ResponseEntity<?> getAllUsers() {
    	return bitbucketService.getAllUsers();    	
    }

    @GetMapping("/get-all-pr")
    private ResponseEntity<?> getAllPrs() {
        return bitbucketService.getAllPullRequests();
    }

    @GetMapping("/get-all-branch")
    private ResponseEntity<?> getAllBranches() {
        return bitbucketService.getAllBranches();
    }
    /* Not Implemented*/

    @GetMapping("/get-commits-by-user")
    private ResponseEntity<?> getCommitsByUser(@RequestParam String accountId){
        return bitbucketService.getCommitsByUser(accountId);
    }

    @GetMapping("/get-all-commits")
    private ResponseEntity<?> getAllCommits(){
        return bitbucketService.getAllCommits();
    }


}
