package com.ttn.demo.domain.letter.application;

import com.ttn.demo.domain.letter.dao.LetterRepository;
import com.ttn.demo.domain.letter.domain.Letter;
import com.ttn.demo.domain.letter.dto.request.LetterCreateRequest;
import com.ttn.demo.domain.letter.dto.response.LetterSummaryResponse;
import com.ttn.demo.domain.user.dao.UserRepository;
import com.ttn.demo.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

    public Letter createLetter(LetterCreateRequest letterRequestDTO) {
        User sender = userRepository.findById(letterRequestDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new RuntimeException("No users available for receiver");
        }

        User receiver = allUsers.get(new Random().nextInt(allUsers.size()));

        Letter letter = Letter.of(sender, receiver, letterRequestDTO.getTitle(), letterRequestDTO.getContents()).build();
        return letterRepository.save(letter);
    }

    public Optional<Letter> getLetterById(Long id) {
        return letterRepository.findById(id);
    }

    public List<LetterSummaryResponse> getAllLetters() {
        return letterRepository.findAll().stream()
                .map(letter -> new LetterSummaryResponse(letter.getTitle(), letter.getSender().getNickName(), letter.getReceiver().getNickName()))
                .collect(Collectors.toList());
    }
}
