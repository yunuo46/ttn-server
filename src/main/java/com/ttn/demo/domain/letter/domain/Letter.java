package com.ttn.demo.domain.letter.domain;

import com.ttn.demo.domain.letter.dto.request.LetterCreateRequest;
import com.ttn.demo.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false) // 보낸 사람
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false) // 받은 사람
    private User receiver;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(name = "sended_at")
    private LocalDateTime sendedAt;

    @Column(name = "opened_at")
    private LocalDateTime openedAt;

    public static LetterBuilder of(User sender, User receiver, String title, String contents) {
        return new LetterBuilder()
                .sender(sender)
                .receiver(receiver)
                .title(title)
                .contents(contents)
                .sendedAt(LocalDateTime.now());
    }
}
