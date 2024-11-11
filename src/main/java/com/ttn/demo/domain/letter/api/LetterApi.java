package com.ttn.demo.domain.letter.api;

import com.ttn.demo.domain.letter.domain.Letter;
import com.ttn.demo.domain.letter.dto.request.LetterCreateRequest;
import com.ttn.demo.domain.letter.dto.response.LetterResponse;
import com.ttn.demo.domain.letter.dto.response.LetterSummaryResponse;
import com.ttn.demo.domain.letter.application.LetterService;
import com.ttn.demo.global.util.ApiResponse;
import com.ttn.demo.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/letters")
@RequiredArgsConstructor
public class LetterApi {

    private final LetterService letterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createLetter(@RequestBody LetterCreateRequest letterRequestDTO) {
        Long id = ApiUtils.getUserIdFromAuthentication();
        letterService.createLetter(id, letterRequestDTO);
        return ApiUtils.success(null);
    }

    @GetMapping("/{letterId}")
    public ApiResponse<LetterResponse> getLetterById(@PathVariable Long letterId) {
        Long id = ApiUtils.getUserIdFromAuthentication();
        LetterResponse letterResponse = letterService.getLetterById(id, letterId);
        return ApiUtils.success(letterResponse);
    }

    @GetMapping("/sent")
    public ApiResponse<List<LetterSummaryResponse>> getAllSentLetters() {
        Long id = ApiUtils.getUserIdFromAuthentication();
        List<LetterSummaryResponse> letters = letterService.getAllSentLetters(id);
        return ApiUtils.success(letters);
    }

    @GetMapping("/received")
    public ApiResponse<List<LetterSummaryResponse>> getAllReceivedLetters() {
        Long id = ApiUtils.getUserIdFromAuthentication();
        List<LetterSummaryResponse> letters = letterService.getAllReceivedLetters(id);
        return ApiUtils.success(letters);
    }
}
