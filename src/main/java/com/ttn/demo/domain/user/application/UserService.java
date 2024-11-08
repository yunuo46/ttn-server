package com.ttn.demo.domain.user.application;

import com.ttn.demo.domain.user.dao.UserRepository;
import com.ttn.demo.domain.user.domain.User;
import com.ttn.demo.domain.user.dto.response.UserResponse;
import com.ttn.demo.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(User user) {
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(),savedUser.getNickname());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserResponse(user.getId(), user.getNickname());
    }

    public Iterable<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getNickname()))
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
