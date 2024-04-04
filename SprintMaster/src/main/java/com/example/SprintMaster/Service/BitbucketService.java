package com.example.SprintMaster.Service;

import com.example.SprintMaster.Utils.BitbucketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BitbucketService {

    @Autowired
    BitbucketUtil bitbucketUtil;

    public ResponseEntity<?> getAccessToken(){
       String accessToken =  bitbucketUtil.getRefreshToken();
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}