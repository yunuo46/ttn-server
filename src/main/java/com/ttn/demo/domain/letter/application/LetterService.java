package com.ttn.demo.domain.letter.application;

import com.ttn.demo.domain.letter.dao.LetterRepository;
import com.ttn.demo.domain.letter.domain.Letter;
import com.ttn.demo.domain.letter.dto.request.LetterCreateRequest;
import com.ttn.demo.domain.letter.dto.response.LetterResponse;
import com.ttn.demo.domain.letter.dto.response.LetterSummaryResponse;
import com.ttn.demo.domain.letter.exception.LetterNotFoundException;
import com.ttn.demo.domain.letter.exception.NoAvailableReceiverException;
import com.ttn.demo.domain.letter.exception.SenderNotFoundException;
import com.ttn.demo.domain.user.dao.UserRepository;
import com.ttn.demo.domain.user.domain.User;
import com.ttn.demo.domain.user.exception.UserNotFoundException;
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

    public void createLetter(Long id, LetterCreateRequest letterRequestDTO) {
        User sender = userRepository.findById(id)
                .orElseThrow(SenderNotFoundException::new);

        List<User> allUsers = userRepository.findAll().stream()
                .filter(user -> !user.getId().equals(sender.getId()))
                .toList();

        if (allUsers.isEmpty()) {
            throw new NoAvailableReceiverException();
        }

        User receiver = allUsers.get(new Random().nextInt(allUsers.size()));

        Letter senderLetter = Letter.of(sender, receiver, letterRequestDTO.getTitle(), letterRequestDTO.getContents(), sender.getLanguage()).build();
        letterRepository.save(senderLetter);

        if(!sender.getLanguage().equals(receiver.getLanguage())) {
            // To Do : title contents translation logic
            Letter recieverLetter = Letter.of(sender, receiver, letterRequestDTO.getTitle(), letterRequestDTO.getContents(), receiver.getLanguage()).build();
            letterRepository.save(recieverLetter);
        }
    }

    public LetterResponse getLetterById(Long id, Long letterId) {
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterNotFoundException(letterId));

        if(letter.getReceiver().getId().equals(id) && letter.getOpenedAt() != null) {
            letter.setOpenedAt(LocalDateTime.now());
            letterRepository.save(letter);
        }
        return LetterResponse.of(letter);
    }

    public List<LetterSummaryResponse> getAllSentLetters(Long id) {
        String language = userRepository.findLanguageById(id)
                .orElseThrow(UserNotFoundException::new);

        return letterRepository.findBySenderIdAndLanguage(id, language).stream()
                .map(LetterSummaryResponse::of)
                .collect(Collectors.toList());
    }

    public List<LetterSummaryResponse> getAllReceivedLetters(Long id) {
        String language = userRepository.findLanguageById(id)
                .orElseThrow(UserNotFoundException::new);

        return letterRepository.findByReceiverIdAndLanguage(id, language).stream()
                .map(LetterSummaryResponse::of)
                .collect(Collectors.toList());
    }
}
