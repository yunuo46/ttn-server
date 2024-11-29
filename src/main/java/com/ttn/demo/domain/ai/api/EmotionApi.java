package com.ttn.demo.domain.ai.api;

import com.ttn.demo.domain.ai.application.EmotionService;
import com.ttn.demo.domain.ai.dto.response.clovaAIResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmotionApi {

    private final EmotionService emotionService;

    @GetMapping("/emotional-message")
    public clovaAIResponse getEmotionalMessage() throws JSONException {
        return new clovaAIResponse(emotionService.getEmotionalMsg());
    }

}
