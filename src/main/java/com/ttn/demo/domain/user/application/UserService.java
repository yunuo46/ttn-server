package com.ttn.demo.domain.user.application;

import com.ttn.demo.domain.user.dao.UserRepository;
import com.ttn.demo.domain.user.domain.User;
import com.ttn.demo.domain.user.dto.Tokens;
import com.ttn.demo.domain.user.dto.request.UserRequest;
import com.ttn.demo.domain.user.dto.response.UserResponse;
import com.ttn.demo.domain.user.exception.UserNotFoundException;
import com.ttn.demo.domain.user.util.OauthTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OauthTokenGenerator oauthTokenGenerator;

    public Tokens createUser(UserRequest userRequest) {
        User user = User.of(userRequest.getNickname(), userRequest.getPassword(), userRequest.getLanguage()); // 변환 과정 필요
        User savedUser = userRepository.save(user);
        return oauthTokenGenerator.generate(savedUser.getId());
    }

    public Tokens loginUser(UserRequest userRequest) {
        User user = userRepository.findByNickname(userRequest.getNickname())
                .orElseThrow(UserNotFoundException::new);

        if (!userRequest.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return oauthTokenGenerator.generate(user.getId());
    }

    public Iterable<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getNickname(), user.getLanguage()))
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
