package com.ttn.demo.domain.ai.application;

import com.fasterxml.jackson.core.ObjectCodec;
import com.ttn.demo.domain.ai.dto.response.EmotionAnalyzeResponse;
import com.ttn.demo.domain.letter.application.LetterService;
import com.ttn.demo.domain.letter.dto.response.LetterSummaryResponse;
import com.ttn.demo.infra.clovaStudio.ClovaApiClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClovaService {
    @Value("${clovaAI.api-url}")
    private String apiUrl;

    @Value("${clovaAI.msg-api-key}")
    private String apiKey;

    @Value("${clovaAI.msg-api-gateway-key}")
    private String apiGatewayKey;

    @Value("${clovaAI.analyze-api=key}")
    private String analyzeApiKey;

    @Value("${clovaAI.analyze-gateway-key}")
    private String analyzeApiGatewayKey;

    private final ClovaApiClient clovaApiClient;
    private final LetterService letterService;

    public ClovaService(ClovaApiClient clovaApiClient, LetterService letterService) {
        this.clovaApiClient = clovaApiClient;
        this.letterService = letterService;
    }

    public String getEmotionalMsg() throws JSONException {
        ObjectNode rootNode = getJsonNodes("사용자에게 전달할 감성적인 문구를 작성해주세요. 문구는 따뜻하고 희망적이며 긍정적인 느낌을 주어야 합니다. 한 문장으로 반환하여야 하며, 강조나 줄 바꿈 기호가 들어가서는 안됩니다.","");
        JSONObject jsonResponse = sendReqeust(rootNode, apiKey, apiGatewayKey);

        return jsonResponse
                .getJSONObject("result")
                .getJSONObject("message")
                .getString("content");
    }

    public EmotionAnalyzeResponse getEmotionAnalyze(Long userId) {
        List<LetterSummaryResponse> letters = letterService.getAllSentLetters(userId);
        String stringLetters = letters.stream()
                .map(LetterSummaryResponse::getTitle)  // 예시: LetterSummaryResponse의 getTitle() 메서드로 제목을 추출
                .collect(Collectors.joining(", "));

        ObjectNode rootNode = getJsonNodes("사용자가 입력한 텍스트를 분석하여 감정분석 json 자료를 제공하는 어시스턴트입니다. 감정은 세가지로 happy, sad, loneliness가 존재합니다. 각 감정에 대한 %를 json형태로 반환해주세요. 반드시 다른 응답은 들어가지 않아야 하고, json형태로만 반환해주면 됩니다.",stringLetters);
        JSONObject jsonResponse = sendReqeust(rootNode, analyzeApiKey, analyzeApiGatewayKey);

        String content = jsonResponse
                .getJSONObject("result")
                .getJSONObject("message")
                .getString("content");

        try {
            // Convert JSON string to DTO
            ObjectMapper  objectMapper = new ObjectMapper();
            return objectMapper.readValue(content, EmotionAnalyzeResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse emotion analysis response", e);
        }
    }

    private JSONObject sendReqeust(ObjectNode rootNode, String analyzeApiKey, String analyzeApiGatewayKey) {
        String requestBody =  rootNode.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-NCP-CLOVASTUDIO-API-KEY", analyzeApiKey);
        headers.put("X-NCP-APIGW-API-KEY", analyzeApiGatewayKey);

        String response = clovaApiClient.sendRequest(apiUrl, headers, requestBody);
        return new JSONObject(response);
    }

    private static ObjectNode getJsonNodes(String systemContent, String userContent) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Root JSON Object
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode messagesNode = objectMapper.createArrayNode();

        // Add messages array
        ObjectNode systemMessage = objectMapper.createObjectNode();
        systemMessage.put("role", "system");
        systemMessage.put("content", systemContent);
        messagesNode.add(systemMessage);

        ObjectNode userMessage = objectMapper.createObjectNode();
        userMessage.put("role", "system");
        userMessage.put("content", userContent);
        messagesNode.add(userMessage);

        rootNode.set("messages", messagesNode);

        rootNode.put("topP", 0.8);
        rootNode.put("topK", 0);
        rootNode.put("maxTokens", 80);
        rootNode.put("temperature", 0.5);
        rootNode.put("repeatPenalty", 5.0);
        rootNode.putArray("stopBefore");
        rootNode.put("includeAiFilters", false);
        rootNode.put("seed", 0);
        return rootNode;
    }
}