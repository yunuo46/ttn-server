package com.ttn.demo.infra.emotionalPhrase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class clovaAIApi {

    private final clovaAIService clovaAIService;

    @GetMapping("/emotional-message")
    public String getEmotionalMessage() {
        return clovaAIService.getEmotionalMsg();
    }

}
