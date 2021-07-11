package com.toy.project.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BaseAPITest {

    private static String local = "http://localhost:8080/api";

    private static final RestTemplate restTemplate = new RestTemplate();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setAuthHeaders(HttpHeaders headers, String username, String password) {
        headers.set("Authorization", "Bearer " + getAuthenticationTest(username, password));
    }

    private static String getAuthenticationTest(String username, String password) {
        String result = null;
        try {
            String authUrl = local + "/authenticate";
            String json = "" + "{\n" + "    \"username\": \"" + username + "\",\n" + "    \"password\": \"" + password + "\"\n" + "}\n";
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-type", "application/json;charset=UTF-8");
            HttpEntity httpEntity = new HttpEntity(json, httpHeaders);
            ResponseEntity response = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Object.class);
            result = objectMapper.convertValue(response.getBody(), Map.class).get("id_token").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void callClientTest(String url, HttpMethod method, HttpEntity entity) {
        try {
            ResponseEntity result = restTemplate.exchange(local + url, method, entity, Object.class);
            System.out.println(result.getStatusCode());
            System.out.println(result.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
