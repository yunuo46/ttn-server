package com.ttn.demo.domain.letter.api;

import com.ttn.demo.domain.letter.domain.Letter;
import com.ttn.demo.domain.letter.dto.request.LetterCreateRequest;
import com.ttn.demo.domain.letter.dto.response.LetterSummaryResponse;
import com.ttn.demo.domain.letter.application.LetterService;
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
    public ResponseEntity<Letter> createLetter(@RequestBody LetterCreateRequest letterRequestDTO) {
        Letter letter = letterService.createLetter(letterRequestDTO);
        return new ResponseEntity<>(letter, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Letter> getLetterById(@PathVariable Long id) {
        Optional<Letter> letter = letterService.getLetterById(id);
        return letter.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<LetterSummaryResponse>> getAllLetters() {
        List<LetterSummaryResponse> letters = letterService.getAllLetters();
        return new ResponseEntity<>(letters, HttpStatus.OK);
    }
}
