package com.example.SprintMaster.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
