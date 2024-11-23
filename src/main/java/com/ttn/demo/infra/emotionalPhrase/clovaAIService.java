package com.ttn.demo.infra.emotionalPhrase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class clovaAIService {
    @Value("${clovaAI.api-url}")
    private String apiUrl;

    @Value("${clovaAI.api-gateway-key}")
    private String apiGatewayKey;


    public String getEmotionalMsg(String prompt) {
        HttpHeaders headers = createHeaders(apiUrl, apiGatewayKey);

        String requestBody = "{"
                + "\"messages\": [{\"role\": \"system\", \"content\": \"" + prompt + "\"}],"
                + "\"topP\": 0.8,"
                + "\"temperature\": 0.7,"
                + "\"maxTokens\": 80"
                + "}";

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-NCP-CLOVASTUDIO-API-KEY", apiUrl);
        headers.add("X-NCP-APIGW-API-KEY", apiGatewayKey);

        // HttpEntity 생성
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // RestTemplate으로 POST 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        // 성공 시 응답 반환, 실패 시 예외 처리
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // 응답 데이터 반환
        } else {
            throw new RuntimeException("API 요청 실패: " + response.getStatusCode());
        }
    }
    

    private HttpHeaders createHeaders(String apiUrl, String apiGatewayKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-NCP-CLOVASTUDIO-API-KEY", apiUrl);
        headers.add("X-NCP-APIGW-API-KEY", apiGatewayKey);
        return headers;
    }
}
