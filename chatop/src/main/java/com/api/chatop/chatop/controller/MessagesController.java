package com.api.chatop.chatop.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.chatop.chatop.dto.request.CreateMessageRequestDTO;
import com.api.chatop.chatop.dto.response.MessageResponseDTO;
import com.api.chatop.chatop.mapper.MessageMapper;
import com.api.chatop.chatop.model.Messages;
import com.api.chatop.chatop.service.MessagesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Messages", description = "Gestion des messages")
public class MessagesController {

    private final MessagesService messagesService;
    private final MessageMapper messageMapper;

    public MessagesController(MessagesService messagesService, MessageMapper messageMapper) {
        this.messagesService = messagesService;
        this.messageMapper = messageMapper;
    }

    @Operation(summary = "Créer un message", description = "Envoie un nouveau message pour une location")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Message créé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("api/messages")
    public ResponseEntity<MessageResponseDTO> createMessage(@Valid @RequestBody CreateMessageRequestDTO requestDTO) {
        Messages message = messageMapper.toEntity(requestDTO);
        message.setCreated_at(LocalDateTime.now());

        Messages savedMessage = messagesService.saveMessage(message);
        MessageResponseDTO response = messageMapper.toResponseDTO(savedMessage);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtenir un message", description = "Retourne un message par son ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Message trouvé"),
        @ApiResponse(responseCode = "404", description = "Message non trouvé")
    })
    @GetMapping("api/messages/{id}")
    public ResponseEntity<MessageResponseDTO> getMessage(@PathVariable final Long id) {
        Messages message = messagesService.getMessageById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        MessageResponseDTO response = messageMapper.toResponseDTO(message);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lister les messages", description = "Retourne tous les messages")
    @ApiResponse(responseCode = "200", description = "Liste des messages")
    @GetMapping("api/messages")
    public ResponseEntity<List<MessageResponseDTO>> getMessages() {
        Iterable<Messages> messages = messagesService.getAllMessages();

        List<MessageResponseDTO> response = StreamSupport.stream(messages.spliterator(), false)
                .map(messageMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Modifier un message", description = "Met à jour un message existant")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Message modifié"),
        @ApiResponse(responseCode = "404", description = "Message non trouvé")
    })
    @PutMapping("api/messages/{id}")
    public ResponseEntity<MessageResponseDTO> updateMessage(@PathVariable final Long id,
                                                            @Valid @RequestBody CreateMessageRequestDTO requestDTO) {
        Messages existingMessage = messagesService.getMessageById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        existingMessage.setRental_id(requestDTO.getRentalId());
        existingMessage.setUser_id(requestDTO.getUserId());
        existingMessage.setMessage_content(requestDTO.getMessage());
        existingMessage.setUpdated_at(LocalDateTime.now());

        Messages updatedMessage = messagesService.saveMessage(existingMessage);
        MessageResponseDTO response = messageMapper.toResponseDTO(updatedMessage);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Supprimer un message", description = "Supprime un message par son ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Message supprimé"),
        @ApiResponse(responseCode = "404", description = "Message non trouvé")
    })
    @DeleteMapping("api/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable final Long id) {
        messagesService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
