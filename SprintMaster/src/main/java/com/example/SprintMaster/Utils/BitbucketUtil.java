package com.example.SprintMaster.Utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONObject;

@Component
public class BitbucketUtil {

    public String getRefreshToken(){

        String responseBody = "";

        RestTemplate restTemplate = new RestTemplate();

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("tcgscnXjWKu4sV2j4x", "LjcLhF5Hx9c9cYcQcrxwymMykGx37RLf");

        // Construct the request body as a form-urlencoded string
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", "rhdTYCJ6fQ2Q96e5fd");

        // Build the request entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

        // Send the request
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(
                    "https://bitbucket.org/site/oauth2/access_token",
                    requestEntity,
                    String.class
            );
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
// Get and print the response
         responseBody = responseEntity.getBody();

        if(responseBody != null && !responseBody.isEmpty()){
            JSONObject obj = JSONObject.fromObject(responseBody);
            if(obj != null && obj.has("access_token")){
              String token =   obj.optString("access_token");
                responseBody = token;
            }

        }

        return responseBody;
    }
}