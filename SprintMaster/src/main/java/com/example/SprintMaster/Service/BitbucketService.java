package com.example.SprintMaster.Service;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.SprintMaster.Dto.UserDto;
import com.example.SprintMaster.Utils.BitbucketUtil;

@Service
public class BitbucketService {

    @Autowired
    BitbucketUtil bitbucketUtil;

    public ResponseEntity<?> getAccessToken() {
        String accessToken = bitbucketUtil.getRefreshToken();
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllUsers() {
        String accessToken = bitbucketUtil.getRefreshToken();
        Map<String, UserDto> val = bitbucketUtil.getUsers(accessToken);
        return new ResponseEntity<>(val, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllPullRequests() {
        String accessToken = bitbucketUtil.getRefreshToken();
        JSONArray val = bitbucketUtil.getAllPullRequest(accessToken);
        return new ResponseEntity<>(val, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllBranches() {
        String accessToken = bitbucketUtil.getRefreshToken();
        JSONArray val = bitbucketUtil.getAllBranches(accessToken);
        return new ResponseEntity<>(val, HttpStatus.OK);
    }
    public ResponseEntity<?> getCommitsByUser(String accountId) {
        String accessToken = bitbucketUtil.getRefreshToken();
        JSONArray val = bitbucketUtil.getAllCommitsByUser(accessToken, accountId);
        return new ResponseEntity<>(val, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCommits() {
        String accessToken = bitbucketUtil.getRefreshToken();
        JSONObject val = bitbucketUtil.getAllCommits(accessToken);
        return new ResponseEntity<>(val, HttpStatus.OK);
    }

	public ResponseEntity<?> getAllLinesForCommit(String commitId) {
		String accessToken = bitbucketUtil.getRefreshToken();
		String lines = bitbucketUtil.getAllLinesForCommit(commitId, accessToken);
		return new ResponseEntity<>(lines, HttpStatus.OK);
	}

	public ResponseEntity<?> getEmpEfficiency(String empCode) {
		String accessToken = bitbucketUtil.getRefreshToken();
		//get All commits for user
		String totalCommits = bitbucketUtil.getAllCommitsforUser(accessToken,empCode);
		//get All Pull reg for user
		
		return null;
	}
}