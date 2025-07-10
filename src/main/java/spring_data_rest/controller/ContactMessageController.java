package spring_data_rest.controller;

import spring_data_rest.dao.ContactMessageRepository;
import spring_data_rest.entity.ContactMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
public class ContactMessageController {

    private static final Logger logger = LoggerFactory.getLogger(ContactMessageController.class);

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    // Получить все сообщения (например, для админки)
    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        logger.info("GET /api/contact - getAllMessages called");

        List<ContactMessage> messages = contactMessageRepository.findAll();
        logger.debug("Retrieved {} messages from database", messages.size());

        return ResponseEntity.ok(messages);
    }

    // Отправить новое сообщение
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ContactMessage> sendMessage(@RequestBody ContactMessage message) {
        logger.info("POST /api/contact - sendMessage called with data: {}", message);

        ContactMessage saved = contactMessageRepository.save(message);
        logger.debug("Message saved with id: {}", saved.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Получить сообщение по ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactMessage> getMessageById(@PathVariable int id) {
        logger.info("GET /api/contact/{} - getMessageById called", id);

        Optional<ContactMessage> msg = contactMessageRepository.findById(id);
        if (msg.isPresent()) {
            logger.debug("Message found: {}", msg.get());
            return ResponseEntity.ok(msg.get());
        } else {
            logger.warn("Message with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить сообщение
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        logger.info("DELETE /api/contact/{} - deleteMessage called", id);

        if (!contactMessageRepository.existsById(id)) {
            logger.warn("Message with id {} not found for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        }

        contactMessageRepository.deleteById(id);
        logger.info("Message with id {} deleted", id);

        return ResponseEntity.ok("Message deleted");
    }
}