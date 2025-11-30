package com.api.chatop.chatop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.chatop.chatop.repository.MessagesRepository;
import com.api.chatop.chatop.model.Messages;
import lombok.Data;

@Data
@Service
public class MessagesService {

    @Autowired
    private MessagesRepository MessagesRepository;

    public Optional<Messages> getMessageById(final Long id) {
        return MessagesRepository.findById(id);
    }

    public Iterable<Messages> getAllMessages() {
        return MessagesRepository.findAll();
    }

    public void deleteMessage(final Long id) {
        MessagesRepository.deleteById(id);
    }

    public Messages saveMessage(Messages message) {
        return MessagesRepository.save(message);
    }
}
