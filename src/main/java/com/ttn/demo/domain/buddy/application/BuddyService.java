package com.ttn.demo.domain.buddy.application;

import com.ttn.demo.domain.buddy.domain.Buddy;
import com.ttn.demo.domain.buddy.dao.BuddyRepository;
import com.ttn.demo.domain.buddy.dto.request.BuddyCreateRequest;
import com.ttn.demo.domain.buddy.dto.request.BuddyLetterRequest;
import com.ttn.demo.domain.buddy.dto.response.BuddyListResponse;
import com.ttn.demo.domain.letter.application.LetterService;
import com.ttn.demo.domain.user.dao.UserRepository;
import com.ttn.demo.domain.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuddyService {

    private final BuddyRepository buddyRepository;
    private final UserRepository userRepository;
    private final LetterService letterService;

    public BuddyService(BuddyRepository buddyRepository, UserRepository userRepository, LetterService letterService) {
        this.buddyRepository = buddyRepository;
        this.userRepository = userRepository;
        this.letterService = letterService;
    }

    public void createBuddy(Long senderId, BuddyCreateRequest buddyCreateRequest) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        User receiver = userRepository.findByNickname(buddyCreateRequest.getBuddyName())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        Buddy buddy = Buddy.of(sender, receiver)
                .build();
        buddyRepository.save(buddy);
    }

    public List<BuddyListResponse> getBuddyList(Long userId) {
        List<Buddy> buddyList = buddyRepository.findBySenderId(userId);

        return buddyList.stream()
                .map(BuddyListResponse::from)  // Buddy 객체에서 DTO 변환
                .collect(Collectors.toList());
    }

    public void sendLetter(BuddyLetterRequest buddyLetterRequest, Long userId) {
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        User receiver = userRepository.findByNickname(buddyLetterRequest.getBuddyName())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        letterService.createLetter(sender, receiver, buddyLetterRequest.getTitle(), buddyLetterRequest.getContents());
    }
}
