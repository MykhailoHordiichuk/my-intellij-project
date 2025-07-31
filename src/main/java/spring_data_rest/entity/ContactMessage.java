package spring_data_rest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "contact_messages")
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    @Column(length = 1000)
    private String message;

    private LocalDateTime sentAt;

    @PrePersist
    public void onCreate() {
        sentAt = LocalDateTime.now();
    }
}
