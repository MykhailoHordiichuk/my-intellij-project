package spring_data_rest.controller;

import spring_data_rest.dao.PageContentRepository;
import spring_data_rest.entity.PageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageContentController {
    private static final Logger logger = LoggerFactory.getLogger(PageContentController.class);

    @Autowired
    private PageContentRepository repository;

    @GetMapping
    public List<PageContent> getAllPages() {
        logger.info("GET /api/pages - getAllPages called");
        List<PageContent> pages = repository.findAll();
        logger.debug("Found {} page(s)", pages.size());
        return pages;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageContent> getPage(@PathVariable int id) {
        logger.info("GET /api/pages/{} - getPage called", id);
        return repository.findById(id)
                .map(page -> {
                    logger.debug("Page found: {}", page);
                    return ResponseEntity.ok(page);
                })
                .orElseGet(() -> {
                    logger.warn("Page with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public PageContent createPage(@RequestBody PageContent page) {
        logger.info("POST /api/pages - createPage called");
        logger.debug("Page to create: {}", page);
        PageContent saved = repository.save(page);
        logger.info("Page created with ID {}", saved.getId());
        return saved;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable int id) {
        logger.info("DELETE /api/pages/{} - deletePage called", id);
        if (!repository.existsById(id)) {
            logger.warn("Page with ID {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        logger.info("Page with ID {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}