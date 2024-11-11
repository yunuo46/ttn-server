package com.ttn.demo.domain.letter.dao;

import com.ttn.demo.domain.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    List<Letter> findBySenderIdAndLanguage(Long senderId, String language);
    List<Letter> findByReceiverIdAndLanguage(Long receiverId, String language);
}
