package com.ttn.demo.infra.clovaStudio;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Component
public class ClovaApiClient {

    public String sendRequest(String apiUrl, Map<String, String> headersMap, String requestBody) {
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headersMap.forEach(headers::add); // Add all custom headers
        headers.add("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString()); // Add unique request ID

        // Create HTTP Entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("API 요청 실패: " + response.getStatusCode());
        }
    }
}