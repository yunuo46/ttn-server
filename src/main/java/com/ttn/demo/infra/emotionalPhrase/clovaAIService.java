package com.ttn.demo.infra.emotionalPhrase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class clovaAIService {
    @Value("${clovaAI.api-url}")
    private String apiUrl;

    @Value("${clovaAI.api-key}")
    private String apiKey;

    @Value("${clovaAI.api-gateway-key}")
    private String apiGatewayKey;


    public String getEmotionalMsg() {
        // 요청 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.add("X-NCP-APIGW-API-KEY", apiGatewayKey);
        headers.add("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString()); // 유니크한 요청 ID 생성
        headers.add("Accept", "text/event-stream");

        // 요청 본문 생성
        String requestBody = "{"
                + "\"messages\": [{\"role\": \"system\", \"content\": \""
                + "사용자에게 전달할 감성적인 문구를 작성해주세요. 문구는 따뜻하고 희망적이며 긍정적인 느낌을 주어야 합니다. 하루를 마무리하는 이들에게 따뜻한 말 한 마디를 건내주세요."
                + "\"}],"
                + "\"topP\": 0.8,"
                + "\"topK\": 0,"
                + "\"maxTokens\": 80,"
                + "\"temperature\": 0.7,"
                + "\"repeatPenalty\": 5.0,"
                + "\"stopBefore\": [],"
                + "\"includeAiFilters\": true,"
                + "\"seed\": 0"
                + "}";

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
}