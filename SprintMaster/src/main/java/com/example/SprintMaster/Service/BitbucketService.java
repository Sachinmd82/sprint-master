package com.example.SprintMaster.Service;

import java.util.Map;

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

    public ResponseEntity<?> getAccessToken(){
       String accessToken =  bitbucketUtil.getRefreshToken();
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

	public ResponseEntity<?> getAllUsers() {
		String accessToken =  bitbucketUtil.getRefreshToken();
		Map<String, UserDto> val=bitbucketUtil.getUsers(accessToken);
		return new ResponseEntity<>(val, HttpStatus.OK);
	}

    public ResponseEntity<?> getAllPullRequests() {
        String accessToken =  bitbucketUtil.getRefreshToken();
        Map<String, UserDto> val=bitbucketUtil.getUsers(accessToken);
        return new ResponseEntity<>(val, HttpStatus.OK);
    }

}