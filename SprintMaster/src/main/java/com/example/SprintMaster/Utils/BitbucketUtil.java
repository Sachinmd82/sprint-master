package com.example.SprintMaster.Utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.SprintMaster.Dto.CommitHistory;
import net.sf.json.JSONArray;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

@Component
public class BitbucketUtil {

    public String getRefreshToken() {

        String responseBody = "";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("tcgscnXjWKu4sV2j4x", "LjcLhF5Hx9c9cYcQcrxwymMykGx37RLf");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", "rhdTYCJ6fQ2Q96e5fd");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity("https://bitbucket.org/site/oauth2/access_token", requestEntity,
                    String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
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
        Map<String, UserDto> resultMap = new HashMap<String, UserDto>();
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
							String accountId = userNode.get("account_id").asText();
                            String workspaceName = workspaceNode != null ? workspaceNode.get("name").asText() : "";
                            UserDto dto = new UserDto(displayName, avatarHref, workspaceName, accountId);
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

    public JSONArray getAllPullRequest(String accessToken) {
        JSONArray responseObject = new JSONArray();
        JSONObject indResponse = new JSONObject();
        String apiUrl = "https://api.bitbucket.org/2.0/repositories/kap-hack/kap-hack-repo/pullrequests";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        if (response != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String res = response.getBody();
                JsonNode root = mapper.readTree(res);
                JsonNode valuesNode = root.get("values");
                if (valuesNode != null && valuesNode.isArray()) {
                    String sourceBranch = "";
                    String author = "";
                    String prLink = "";
                    String destinationBranch = "";
                    for (JsonNode valueNode : valuesNode) {

                        int prId = valueNode.get("id").asInt();
                        String title = valueNode.get("title").asText();
                        String status = valueNode.get("state").asText();
                        String createdDate = valueNode.get("created_on").asText();
                        String lastUpdatedDate = valueNode.get("updated_on").asText();
                        JsonNode authorNode = valueNode.get("author");
                        int commentCount = valueNode.get("comment_count").asInt();
                        if (authorNode != null) {
                            author = authorNode.get("display_name").asText();
                        }
                        JsonNode destinationNode = valueNode.get("destination");
                        if (destinationNode != null) {
                            JsonNode branchNode = destinationNode.get("branch");
                            if (branchNode != null) {
                                destinationBranch = branchNode.get("name").asText();
                            }
                        }

                        JsonNode sourceNode = valueNode.get("source");
                        if (sourceNode != null) {
                            JsonNode branchNode = sourceNode.get("branch");
                            if (branchNode != null) {
                                sourceBranch = branchNode.get("name").asText();
                            }
                        }

                        JsonNode linkNode = valueNode.get("links");
                        if (linkNode != null) {
                            JsonNode htmlNode = linkNode.get("html");
                            if (htmlNode != null) {
                                prLink = htmlNode.get("href").asText();
                            }
                        }
                        indResponse.put("id", prId);
                        indResponse.put("title", title);
                        indResponse.put("status", status);
                        indResponse.put("createdDate", createdDate);
                        indResponse.put("lastUpdatedDate", lastUpdatedDate);
                        indResponse.put("commentsCount", commentCount);
                        indResponse.put("author", author);
                        indResponse.put("sourceBranch", sourceBranch);
                        indResponse.put("destinationBranch", destinationBranch);
                        indResponse.put("prLink", prLink);
                        responseObject.add(indResponse);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseObject;
    }

    public JSONArray getAllBranches(String accessToken) {
        JSONArray responseObject = new JSONArray();
        JSONObject indResponse = new JSONObject();
        String apiUrl = "https://api.bitbucket.org/2.0/repositories/kap-hack/kap-hack-repo/refs/branches";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        if (response != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String res = response.getBody();
                JsonNode root = mapper.readTree(res);
                JsonNode valuesNode = root.get("values");
                if (valuesNode != null && valuesNode.isArray()) {
                    String author = "";
					String createdDate = "";
                    for (JsonNode valueNode : valuesNode) {
                        String branchName = valueNode.get("name").asText();
						JsonNode targetNode = valueNode.get("target");
						if(targetNode != null){
							 createdDate = targetNode.get("date").asText();
							JsonNode authorNode = targetNode.get("author");
							if (authorNode != null) {
								author = authorNode.get("raw").asText();
							}
						}
                        indResponse.put("branchName", branchName);
                        indResponse.put("lastCommittedDate", createdDate);
                        indResponse.put("author", author);
                        responseObject.add(indResponse);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseObject;
    }

	public JSONArray getAllCommitsByUser(String accessToken, String accountId) {

        JSONArray responseObject = new JSONArray();
        JSONObject indResponse = new JSONObject();

        if(accountId == null ){
            return  responseObject;
        }
        String apiUrl = "https://api.bitbucket.org/2.0/pullrequests/"+accountId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        if (response != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String res = response.getBody();
                JsonNode root = mapper.readTree(res);
                JsonNode valuesNode = root.get("values");
                if (valuesNode != null && valuesNode.isArray()) {
                    String author = "";
                    String createdDate = "";
                    for (JsonNode valueNode : valuesNode) {
                        String branchName = valueNode.get("name").asText();
                        JsonNode targetNode = valueNode.get("target");
                        if(targetNode != null){
                            createdDate = targetNode.get("date").asText();
                            JsonNode authorNode = targetNode.get("author");
                            if (authorNode != null) {
                                author = authorNode.get("raw").asText();
                            }
                        }
                        indResponse.put("branchName", branchName);
                        indResponse.put("lastCommittedDate", createdDate);
                        indResponse.put("author", author);
                        responseObject.add(indResponse);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseObject;
	}

    public JSONObject getAllCommits(String accessToken) {
        JSONObject responseObject = new JSONObject();
        JSONArray responseArray = new JSONArray();
        JSONObject indResponse = new JSONObject();
        List<CommitHistory> commitList = new ArrayList<>();

        String apiUrl = "https://api.bitbucket.org/2.0/repositories/kap-hack/kap-hack-repo/commits";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        if (response != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String res = response.getBody();
                JsonNode root = mapper.readTree(res);
                JsonNode valuesNode = root.get("values");
                if (valuesNode != null && valuesNode.isArray()) {
                    String author = "";
                    String createdDate = "";
                    for (JsonNode valueNode : valuesNode) {
                         createdDate = valueNode.get("date").asText("");
                        String commitId = valueNode.get("hash").asText("");
                        author = valueNode.get("author").get("raw").asText("");
                        String message = valueNode.get("message").asText("");
                        String commitLink = valueNode.get("links").get("html").get("href").asText("");

                        CommitHistory data = new CommitHistory(commitId, author, createdDate, commitLink);
                        commitList.add(data);
                        indResponse.put("commitId",commitId);
                        indResponse.put("createDate", createdDate);
                        indResponse.put("author", author);
                        indResponse.put("message", message);
                        indResponse.put("commitLink", commitLink);
                        responseArray.add(indResponse);
                        responseObject.put("commitsList", responseArray);
                    }
                    if (!commitList.isEmpty()) {
                        Map<String, Long> userNameCountMap = commitList.stream()
                                .collect(Collectors.groupingBy(CommitHistory::getAuthor, Collectors.counting()));
                        responseObject.put("userNameToCommitsCountMap", userNameCountMap);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        List<String> dateList = commitList.stream().map(CommitHistory::getCreatedDate).collect(Collectors.toList());
                        Map<LocalDate, Long> entriesPerDay = dateList.stream()
                                .map(s -> LocalDate.parse(s.substring(0, 10), formatter))
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                        responseObject.put("commitsPerDay", entriesPerDay.toString());

                        Map<String, List<String>> commitsByUser = commitList.stream()
                                .collect(Collectors.groupingBy(CommitHistory::getAuthor,
                                        Collectors.mapping(CommitHistory::getCommitId, Collectors.toList())));

                        responseObject.put("userIdToCommitIdMap", commitsByUser);


                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseObject;
    }

	public String getAllLinesForCommit(String commitId, String accessToken) {
		String apiUrl = "https://api.bitbucket.org/2.0/repositories/kap-hack/kap-hack-repo/diff/" + commitId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/json");
        
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
        
        if(response!= null) {
        	String body = response.getBody();
        	int count = 0;
//            boolean consecutive = false;
            boolean previousWasPlus = false;
            for (char c : body.toCharArray()) {
            	if (c == ' ' && previousWasPlus) {
                    count++;
                    previousWasPlus = false;
                } else if (c == '+') {
                    if (!previousWasPlus) {
                        previousWasPlus = true;
                    }
                } else {
                    previousWasPlus = false;
                }
            }
            return count +"";
        }
        
		return null;
	}

	public String getAllCommitsforUser(String accessToken, String empCode) {
		String apiUrl = "https://api.bitbucket.org/2.0/pullrequests/" + empCode;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.set("Accept", "application/json");

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

		if (response != null) {
			ObjectMapper mapper = new ObjectMapper();
			String res = response.getBody();
			JsonNode root;
			try {
				root = mapper.readTree(res);
				String count = root.get("size").asText();
				return count;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		}
		return null;
	}
}