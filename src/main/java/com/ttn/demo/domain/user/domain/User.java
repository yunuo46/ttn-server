package com.ttn.demo.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String nickname;

    private String password;

    private String language;

    @Builder
    public User(String nickname, String password, String language) {
        this.nickname = nickname;
        this.password = password;
        this.language = language;
    }

    public static User of(String nickname, String password, String language) {
        return User.builder()
                .nickname(nickname)
                .password(password)
                .language(language)
                .build();
    }
}
