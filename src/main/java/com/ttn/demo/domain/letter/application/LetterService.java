package com.ttn.demo.domain.letter.application;

import com.ttn.demo.domain.letter.dao.LetterRepository;
import com.ttn.demo.domain.letter.domain.Letter;
import com.ttn.demo.domain.letter.dto.request.LetterCreateRequest;
import com.ttn.demo.domain.letter.dto.response.LetterSummaryResponse;
import com.ttn.demo.domain.letter.exception.LetterNotFoundException;
import com.ttn.demo.domain.letter.exception.NoAvailableReceiverException;
import com.ttn.demo.domain.letter.exception.SenderNotFoundException;
import com.ttn.demo.domain.user.dao.UserRepository;
import com.ttn.demo.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

    public Letter createLetter(LetterCreateRequest letterRequestDTO) {
        User sender = userRepository.findById(letterRequestDTO.getSenderId())
                .orElseThrow(SenderNotFoundException::new);

        List<User> allUsers = userRepository.findAll().stream()
                .filter(user -> !user.getId().equals(sender.getId()))
                .toList();

        if (allUsers.isEmpty()) {
            throw new NoAvailableReceiverException();
        }

        User receiver = allUsers.get(new Random().nextInt(allUsers.size()));

        Letter letter = Letter.of(sender, receiver, letterRequestDTO.getTitle(), letterRequestDTO.getContents()).build();
        return letterRepository.save(letter);
    }

    public Letter getLetterById(Long id) {
        Letter letter = letterRepository.findById(id)
                .orElseThrow(() -> new LetterNotFoundException(id));

        letter.setOpenedAt(LocalDateTime.now());
        letterRepository.save(letter);
        return letter;
    }

    public List<LetterSummaryResponse> getAllLetters() {
        return letterRepository.findAll().stream()
                .map(letter -> new LetterSummaryResponse(letter.getTitle(), letter.getSender().getNickname(), letter.getReceiver().getNickname()))
                .collect(Collectors.toList());
    }
}
