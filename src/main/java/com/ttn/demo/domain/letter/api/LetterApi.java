package com.ttn.demo.domain.letter.api;

import com.ttn.demo.domain.letter.domain.Letter;
import com.ttn.demo.domain.letter.dto.request.LetterCreateRequest;
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
    public ApiResponse<Letter> createLetter(@RequestBody LetterCreateRequest letterRequestDTO) {
        Letter letter = letterService.createLetter(letterRequestDTO);
        return ApiUtils.success(letter);
    }

    @GetMapping()
    public ApiResponse<Letter> getLetterById() {
        Long id = ApiUtils.getUserIdFromAuthentication();
        Letter letter = letterService.getLetterById(id);
        return ApiUtils.success(letter);
    }

    @GetMapping("/all")
    public ApiResponse<List<LetterSummaryResponse>> getAllLetters() {
        List<LetterSummaryResponse> letters = letterService.getAllLetters();
        return ApiUtils.success(letters);
    }
}
