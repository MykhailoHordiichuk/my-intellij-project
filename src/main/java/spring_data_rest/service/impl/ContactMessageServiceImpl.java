package spring_data_rest.service.impl;

import spring_data_rest.dao.ContactMessageRepository;
import spring_data_rest.entity.ContactMessage;
import spring_data_rest.service.ContactMessageService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository repo;

    public ContactMessageServiceImpl(ContactMessageRepository repo) {
        this.repo = repo;
    }

    @Override
    public ContactMessage create(ContactMessage input) {
        log.info("Creating contact message from email={}", input.getEmail());
        // sentAt автоматически выставится @PrePersist
        return repo.save(input);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactMessage get(Integer id) {
        log.info("Fetching contact message id={}", id);
        return repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactMessage> getAll() {
        log.info("Fetching all contact messages");
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting contact message id={}", id);
        if (!repo.existsById(id)) {
            log.error("Message id={} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
        repo.deleteById(id);
        log.info("Message id={} deleted", id);
    }
}