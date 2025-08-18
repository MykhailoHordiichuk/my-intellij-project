package spring_data_rest.controller;

import spring_data_rest.dto.contact.*;
import spring_data_rest.entity.ContactMessage;
import spring_data_rest.service.ContactMessageService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class ContactMessageController {

    private final ContactMessageService messages;

    public ContactMessageController(ContactMessageService messages) {
        this.messages = messages;
    }

    @PostMapping
    public ContactMessageDTO create(@Valid @RequestBody ContactMessageCreateDTO dto) {
        ContactMessage saved = messages.create(fromCreate(dto));
        return toDto(saved);
    }

    @GetMapping("/{id}")
    public ContactMessageDTO get(@PathVariable Integer id) {
        return toDto(messages.get(id));
    }

    @GetMapping
    public List<ContactMessageDTO> getAll() {
        return messages.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        messages.delete(id);
    }

    /* mapping */
    private ContactMessageDTO toDto(ContactMessage m) {
        return new ContactMessageDTO(
                m.getId(),
                m.getName(),
                m.getEmail(),
                m.getMessage(),
                m.getSentAt()
        );
    }

    private ContactMessage fromCreate(ContactMessageCreateDTO dto) {
        ContactMessage m = new ContactMessage();
        m.setName(dto.getName());
        m.setEmail(dto.getEmail());
        m.setMessage(dto.getMessage());
        // sentAt проставится @PrePersist
        return m;
    }
}