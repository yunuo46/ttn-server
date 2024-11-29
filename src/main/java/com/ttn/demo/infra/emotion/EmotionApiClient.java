package com.ttn.demo.infra.emotion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class EmotionApiClient {
    @Value("${clovaAI.api-url}")
    private String apiUrl;

    @Value("${clovaAI.api-key}")
    private String apiKey;

    @Value("${clovaAI.api-gateway-key}")
    private String apiGatewayKey;

    public String sendRequest(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.add("X-NCP-APIGW-API-KEY", apiGatewayKey);
        headers.add("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("API 요청 실패: " + response.getStatusCode());
        }
    }
}