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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.add("X-NCP-APIGW-API-KEY", apiGatewayKey);
        headers.add("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString());
        headers.add("Accept", "text/event-stream");

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