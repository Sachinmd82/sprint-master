package com.example.SprintMaster.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.SprintMaster.Repository.JiraRepository;
import com.example.SprintMaster.model.Jira;
import com.example.SprintMaster.model.JiraDataModel;
import com.kapturecrm.utilobj.CommonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class JiraService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private JiraRepository jiraRepository;

	@Autowired
	private CacheService cacheService;

	public ResponseEntity<?> getAllIssueBySprintName(String sprintName) {

		ArrayList<JiraDataModel> dataList = new ArrayList<>();

		JiraDataModel jiraDataModel = new JiraDataModel();

		if (isNotNullAndNotEmpty(sprintName)) {

			try {
				String urlStr = "https://sprintmaster007.atlassian.net/rest/api/3/search?jql=project=" + sprintName
						+ "&maxResults=1000";

				HttpHeaders headers = new HttpHeaders();
				headers.set("Content-Type", "application/json");
				headers.setBasicAuth("darshanneo07@gmail.com",
						"ATATT3xFfGF0n1mHqa-E6uHNTLsG0mAJYs7h1AqYsC8iW7-BXR53T5wSl3zQQ3Pu0AVL4Gaj54FAfbpEu5Z5RAxDX-UZguH5wbTTyLdK6C_QcCOxOnAk_Zf2h0nLy5qPmv92WMH2Rk1_3-w6CSA39bulpJXAm63v3ElWBwYYUSyYN0LFalvfAyw=AFCD39E6");
				HttpEntity<String> entity = new HttpEntity<>("", headers);

				String responseStr = restTemplate.exchange(urlStr, HttpMethod.GET, entity, String.class).getBody();

				if (isNotNullAndNotEmpty(responseStr)) {

					if (responseStr.startsWith("{") && responseStr.endsWith("}")) {
						JSONObject jqlResponseObj = JSONObject.fromObject(responseStr);
						if (jqlResponseObj != null && !jqlResponseObj.isEmpty() && jqlResponseObj.has("issues")) {

							JSONArray issuesArr = jqlResponseObj.getJSONArray("issues");
							if (issuesArr != null && issuesArr.size() > 0) {

								for (int index = 0; index < issuesArr.size(); index++) {
									JSONObject eachIssue = issuesArr.getJSONObject(index);
									if (eachIssue != null && !eachIssue.isEmpty()) {

										jiraDataModel = setFromResponseAndReturn(eachIssue.toString());
										dataList.add(jiraDataModel);
									}
								}
								if (dataList != null && !dataList.isEmpty()) {
									insertDataIntoJira(dataList);
								}
							}
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	private JiraDataModel setFromResponseAndReturn(String eachIssueObj) {

		JiraDataModel jiraDataModel = new JiraDataModel();

		if (CommonUtils.isNotNullAndNotEmpty(eachIssueObj)) {
			try {
				JSONObject eachResponseObj = JSONObject.fromObject(eachIssueObj);
				if (eachResponseObj != null && !eachResponseObj.isEmpty()) {

					String keyStr = eachResponseObj.has("key") ? eachResponseObj.getString("key") : "";
					JSONObject fieldsJSON = eachResponseObj.has("fields") ? eachResponseObj.getJSONObject("fields")
							: null;

					JSONObject priorityJSON = null;
					JSONObject assigneeJSON = null;
					JSONObject statusJSON = null;
					JSONArray sprintJSON = null;

					String module = "";
					String sprintName = "";
					String startDate = "";
					String endDate = "";
					String dueDate = "";

					Timestamp dueDateTime = null;
					Timestamp endDateTime = null;
					Timestamp startDateTime = null;

					if (fieldsJSON != null && !fieldsJSON.isEmpty()) {

						priorityJSON = fieldsJSON.has("priority") ? fieldsJSON.getJSONObject("priority") : null;
						assigneeJSON = fieldsJSON.has("assignee") ? fieldsJSON.getJSONObject("assignee") : null;
						statusJSON = fieldsJSON.has("status") ? fieldsJSON.getJSONObject("status") : null;
						module = fieldsJSON.has("customfield_10034") ? fieldsJSON.getString("customfield_10034") : "";
						startDate = fieldsJSON.has("customfield_10032") ? fieldsJSON.getString("customfield_10032")
								: "";
						endDate = fieldsJSON.has("customfield_10033") ? fieldsJSON.getString("customfield_10033") : "";

						dueDate = fieldsJSON.has("duedate") ? fieldsJSON.getString("duedate") : "";
						sprintJSON = fieldsJSON.has("customfield_10020") ? fieldsJSON.getJSONArray("customfield_10020")
								: null;

					}

					if (sprintJSON != null && !sprintJSON.isEmpty()) {
						Object object = sprintJSON.get(0);
						JSONObject indObj = JSONObject.fromObject(object);

						sprintName = indObj.has("name") ? indObj.getString("name") : "";

					}
					if (isNotNullAndNotEmpty(dueDate) && !dueDate.equalsIgnoreCase("null")) {
						String format = "yyyy-MM-dd";
						dueDateTime = timeConvertor(dueDate, format);
					}
					if (isNotNullAndNotEmpty(endDate) && !endDate.equalsIgnoreCase("null")) {
						String format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
						endDateTime = timeConvertor(endDate, format);
					}
					if (isNotNullAndNotEmpty(startDate) && !startDate.equalsIgnoreCase("null")) {
						String format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
						startDateTime = timeConvertor(startDate, format);
					}
					String priority = "";
					if (priorityJSON != null && !priorityJSON.isEmpty()) {
						priority = priorityJSON.has("name") ? priorityJSON.getString("name") : "";
					}
					String assigneeName = "";
					String assigneeId = "";

					if (assigneeJSON != null && !assigneeJSON.isEmpty()) {
						assigneeName = assigneeJSON.has("displayName") ? assigneeJSON.getString("displayName") : "";
						assigneeId = assigneeJSON.has("accountId") ? assigneeJSON.getString("accountId") : "";

					}
					String status = "";
					if (statusJSON != null && !statusJSON.isEmpty()) {
						status = statusJSON.has("name") ? statusJSON.getString("name") : "";
					}

					jiraDataModel.setAssigneeAccId(assigneeId);
					jiraDataModel.setEmployeeName(assigneeName);
					jiraDataModel.setDueDate(dueDateTime);
					jiraDataModel.setEndDate(endDateTime);
					jiraDataModel.setJiraId(keyStr);
					jiraDataModel.setPriority(priority);
					jiraDataModel.setSprint(sprintName);
					jiraDataModel.setStartDate(startDateTime);
					jiraDataModel.setStatus(status);
					jiraDataModel.setModule(module);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jiraDataModel;

	}

	private Timestamp timeConvertor(String endDate, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set the time zone to UTC
		Date parsedDate = dateFormat.parse(endDate);
		Timestamp endDateTime = new Timestamp(parsedDate.getTime());
		return endDateTime;
	}

	public static boolean isNotNullAndNotEmpty(String str) {
		if (str != null && !"".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public ResponseEntity<?> updateStoryPoint(String jiraId, int storyPoint) {
		if (storyPoint > 0 && isNotNullAndNotEmpty(jiraId)) {
			JSONObject outerObj = new JSONObject();

			JSONObject innerObj = new JSONObject();
			innerObj.put("customfield_10031", storyPoint);

			outerObj.put("fields", innerObj);

			String urlStr = "https://sprintmaster007.atlassian.net/rest/api/3/issue/" + jiraId;

			HttpHeaders headers = new HttpHeaders();

			headers.set("Content-Type", "application/json");
			headers.setBasicAuth("darshanneo07@gmail.com",
					"ATATT3xFfGF0QLsKyvng1uFfH7hFCgKR09Y9kX3GoLK4Edx6cAuyNyfbLoFBH6zvjvEQU_XNThDysn9sZ1jKrdDCNNR1r_cDkaf_Sv7SzOh9yqXVWTDz7Sorf9j5YXDFnqv7O_HJBganAMnGhymnGd8jzfKYnK5yD52-7rRID3ixd2k8T3uY170=28985837");
			HttpEntity<JSONObject> entity = new HttpEntity<>(outerObj, headers);

			try {
				String responseStr = restTemplate.exchange(urlStr, HttpMethod.PUT, entity, String.class).getBody();
			} catch (RestClientException e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
	}

	private void insertDataIntoJira(ArrayList<JiraDataModel> list) {
		ArrayList<Jira> newObjList = new ArrayList<>();
		ArrayList<Jira> oldObjList = new ArrayList<>();

		if (list != null && !list.isEmpty()) {
			for (JiraDataModel obj : list) {
				String jiraId = obj.getJiraId();
				if (isNotNullAndNotEmpty(jiraId)) {
					Jira jiraObj = jiraRepository.findByJiraId(jiraId);
					if (jiraObj != null) {
						jiraObj.setEmpId(cacheService.getEmployeeIdByName(obj.getEmployeeName()));
						jiraObj.setModule(obj.getModule());
						jiraObj.setPriority(obj.getPriority());
						jiraObj.setSprint(obj.getSprint());
						jiraObj.setStatus(obj.getStatus());
						jiraObj.setStartTime(obj.getStartDate());
						jiraObj.setEndTime(obj.getEndDate());
						oldObjList.add(jiraObj);

					} else {
						Jira jira = new Jira();
						jira.setCreateDate(getCurrentTimestamp());
						jira.setEmpId(cacheService.getEmployeeIdByName(obj.getEmployeeName()));
						jira.setJiraId(obj.getJiraId());
						jira.setModule(obj.getModule());
						jira.setPriority(obj.getPriority());
						jira.setSprint(obj.getSprint());
						jira.setStatus(obj.getStatus());
						jira.setStartTime(obj.getStartDate());
						jira.setEndTime(obj.getEndDate());
						newObjList.add(jira);
					}
				}
			}
			if (!oldObjList.isEmpty()) {
				jiraRepository.saveAll(oldObjList);
			}
			if (!newObjList.isEmpty()) {
				jiraRepository.saveAll(newObjList);
			}
		}

	}

	public static Timestamp getCurrentTimestamp() {
		Calendar calendar = Calendar.getInstance();
		return new Timestamp(calendar.getTimeInMillis());
	}

}
