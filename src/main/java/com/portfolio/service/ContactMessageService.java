package com.portfolio.service;

import com.portfolio.dto.ContactMessageRequest;
import com.portfolio.dto.ContactMessageResponse;
import com.portfolio.entity.ContactMessage;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactMessageService {

    private final ContactMessageRepository messageRepository;
    private final EmailService emailService;

    public ContactMessageService(ContactMessageRepository messageRepository, EmailService emailService) {
        this.messageRepository = messageRepository;
        this.emailService = emailService;
    }

    // Submit public message
    @Transactional
    public ContactMessageResponse submitMessage(ContactMessageRequest request) {
        ContactMessage message = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .submittedAt(LocalDateTime.now())
                .isRead(false)
                .build();
        
        ContactMessage savedMessage = messageRepository.save(message);

        // Send email alert to admin asynchronously/safely inside EmailService
        emailService.sendContactNotification(
                savedMessage.getName(),
                savedMessage.getEmail(),
                savedMessage.getSubject(),
                savedMessage.getMessage()
        );

        return mapToResponse(savedMessage);
    }

    // List messages for admin dashboard
    @Transactional(readOnly = true)
    public List<ContactMessageResponse> getAllMessages() {
        return messageRepository.findAllByOrderBySubmittedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Mark message as read
    @Transactional
    public ContactMessageResponse markAsRead(Long id) {
        ContactMessage message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with ID: " + id));
        
        message.setIsRead(true);
        ContactMessage updatedMessage = messageRepository.save(message);
        return mapToResponse(updatedMessage);
    }

    // Delete message
    @Transactional
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Message not found with ID: " + id);
        }
        messageRepository.deleteById(id);
    }

    private ContactMessageResponse mapToResponse(ContactMessage message) {
        return ContactMessageResponse.builder()
                .id(message.getId())
                .name(message.getName())
                .email(message.getEmail())
                .subject(message.getSubject())
                .message(message.getMessage())
                .submittedAt(message.getSubmittedAt())
                .isRead(message.getIsRead())
                .build();
    }
}
