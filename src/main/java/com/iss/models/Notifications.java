package com.iss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Sender is required")
    private String sender;

    @NotBlank(message = "Receiver is required")
    private String receiver;

    @NotBlank(message = "Notification message cannot be empty")
    @Column(length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;



    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_notifications",  // Join table name
        joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), // column for notifications
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") // column for users
    )
    private List<User> users;
    
}
