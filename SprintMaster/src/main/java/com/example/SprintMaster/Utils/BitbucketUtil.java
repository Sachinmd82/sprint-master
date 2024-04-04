package com.example.SprintMaster.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.SprintMaster.Dto.UserDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

@Component
public class BitbucketUtil {

	public String getRefreshToken() {

		String responseBody = "";

		RestTemplate restTemplate = new RestTemplate();

		// Set request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth("tcgscnXjWKu4sV2j4x", "LjcLhF5Hx9c9cYcQcrxwymMykGx37RLf");

		// Construct the request body as a form-urlencoded string
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "refresh_token");
		map.add("refresh_token", "rhdTYCJ6fQ2Q96e5fd");

		// Build the request entity
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

		// Send the request
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = restTemplate.postForEntity("https://bitbucket.org/site/oauth2/access_token", requestEntity,
					String.class);
		} catch (RestClientException e) {
			throw new RuntimeException(e);
		}
// Get and print the response
		responseBody = responseEntity.getBody();

		if (responseBody != null && !responseBody.isEmpty()) {
			JSONObject obj = JSONObject.fromObject(responseBody);
			if (obj != null && obj.has("access_token")) {
				String token = obj.optString("access_token");
				responseBody = token;
			}

		}

		return responseBody;
	}

	public Map<String, UserDto> getUsers(String accessToken) {
		Map<String, UserDto>  resultMap = new HashMap<String, UserDto>();
		String apiUrl = "https://api.bitbucket.org/2.0/workspaces/kap-hack/members";

		// Access token
//    String accessToken = "3RWEkY8cWXsZDv-2xYqB5a1oCTY6b-Dh6-xdrDZBomnv8Rhdepp5WpEmyClhAF-zaYG--8HXL7YpYFFJlYWZ8fT_bJqt0tVNYXO0ZPMBiIBfg-Q8YcilIfA0hxYoCg9l";

		// Create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.set("Accept", "application/json");

		// Create request entity with headers
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		// Create RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Send GET request and retrieve response
		ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

		if (response != null) {

			ObjectMapper mapper = new ObjectMapper();
	        try {
	        	String res = response.getBody();
	            JsonNode root = mapper.readTree(res);
	            JsonNode valuesNode = root.get("values");
	            if (valuesNode != null && valuesNode.isArray()) {
	                for (JsonNode valueNode : valuesNode) {
	                    JsonNode userNode = valueNode.get("user");
	                    if (userNode != null) {
	                        String displayName = userNode.get("display_name").asText();
	                        String avatarHref = userNode.get("links").get("avatar").get("href").asText();
	                        JsonNode workspaceNode = valueNode.get("workspace");
	                        String workspaceName = workspaceNode != null ? workspaceNode.get("name").asText() : "";
	                        UserDto dto = new UserDto(displayName, avatarHref, workspaceName);
	                        resultMap.put(displayName, dto);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

		}

		// Print response
		System.out.println("Response status code: " + response.getStatusCode());
		System.out.println("Response body: " + response.getBody());
		return resultMap;
	}
}