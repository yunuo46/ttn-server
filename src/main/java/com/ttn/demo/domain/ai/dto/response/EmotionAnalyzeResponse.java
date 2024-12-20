package com.ttn.demo.domain.ai.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmotionAnalyzeResponse {
    private int happy;
    private int sad;
    private int loneliness;
}
