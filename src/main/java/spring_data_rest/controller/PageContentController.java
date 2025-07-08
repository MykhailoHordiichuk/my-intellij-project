package spring_data_rest.controller;

import spring_data_rest.dao.PageContentRepository;
import spring_data_rest.entity.PageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageContentController {

    @Autowired
    private PageContentRepository repository;

    @GetMapping
    public List<PageContent> getAllPages() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageContent> getPage(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PageContent createPage(@RequestBody PageContent page) {
        return repository.save(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}