package com.api.chatop.chatop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="rental_id")
    private Long rental_id;
    @Column(name="user_id")
    private Long user_id;
    @Column(name="message")
    private String message_content;
    @Column(name="created_at")
    private LocalDateTime created_at;
    @Column(name="updated_at")
    private LocalDateTime updated_at;
}