package spring_data_rest.controller;

import spring_data_rest.dao.ContactMessageRepository;
import spring_data_rest.entity.ContactMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
public class ContactMessageController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    // Получить все сообщения (например, для админки)
    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(contactMessageRepository.findAll());
    }

    // Отправить новое сообщение
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ContactMessage> sendMessage(@RequestBody ContactMessage message) {
        ContactMessage saved = contactMessageRepository.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Получить сообщение по ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactMessage> getMessageById(@PathVariable int id) {
        Optional<ContactMessage> msg = contactMessageRepository.findById(id);
        return msg.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Удалить сообщение
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        if (!contactMessageRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        }
        contactMessageRepository.deleteById(id);
        return ResponseEntity.ok("Message deleted");
    }
}
