package com.ttn.demo.domain.buddy.dao;

import com.ttn.demo.domain.buddy.domain.Buddy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuddyRepository extends JpaRepository<Buddy, Long> {
    List<Buddy> findBySenderId(Long senderId);
}
