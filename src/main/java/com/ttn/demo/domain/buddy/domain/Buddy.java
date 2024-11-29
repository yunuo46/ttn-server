package com.ttn.demo.domain.buddy.domain;

import com.ttn.demo.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Buddy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false) // 보낸 사람
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false) // 받은 사람
    private User receiver;

    public static BuddyBuilder of(User sender, User receiver) {
        return Buddy.builder()
                .sender(sender)
                .receiver(receiver);
    }
}
