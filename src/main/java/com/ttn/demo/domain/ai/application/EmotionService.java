package com.ttn.demo.domain.ai.application;

import com.ttn.demo.infra.emotion.EmotionApiClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class EmotionService {
    private final EmotionApiClient emotionApiClient;

    public EmotionService(EmotionApiClient emotionApiClient) {
        this.emotionApiClient = emotionApiClient;
    }

    public String getEmotionalMsg() throws JSONException {
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

        String response = emotionApiClient.sendRequest(requestBody);
        JSONObject jsonResponse = new JSONObject(response);
        String message = jsonResponse
                .getJSONObject("result") // result 객체에 접근
                .getJSONObject("message") // message 객체에 접근
                .getString("content"); // content 필드 값 추출

        return message;
    }
}