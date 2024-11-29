package com.ttn.demo.domain.ai.api;

import com.ttn.demo.domain.ai.application.ClovaService;
import com.ttn.demo.domain.ai.dto.response.EmotionAnalyzeResponse;
import com.ttn.demo.domain.ai.dto.response.EmotionMsgResponse;
import com.ttn.demo.domain.letter.application.LetterService;
import com.ttn.demo.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emotion")
@RequiredArgsConstructor
public class ClovaApi {

    private final ClovaService clovaService;
    private final LetterService letterService;

    @GetMapping("/message")
    public EmotionMsgResponse getEmotionalMessage() throws JSONException {
        return new EmotionMsgResponse(clovaService.getEmotionalMsg());
    }

    @GetMapping("/analyze")
    public EmotionAnalyzeResponse getEmotionAnalyze()  {
        Long userId = ApiUtils.getUserIdFromAuthentication();
        return clovaService.getEmotionAnalyze(userId);
    }
}
