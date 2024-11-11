package com.ttn.demo.domain.user.dao;

import com.ttn.demo.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String username);
    @Query("SELECT u.language FROM User u WHERE u.id = :id")
    Optional<String> findLanguageById(@Param("id") Long id);
}
