package com.ttn.demo.domain.user.dao;

import com.ttn.demo.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
