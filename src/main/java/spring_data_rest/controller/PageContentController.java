package spring_data_rest.controller;

import spring_data_rest.dto.page.*;
import spring_data_rest.entity.PageContent;
import spring_data_rest.service.PageContentService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pages")
public class PageContentController {

    private final PageContentService pages;

    public PageContentController(PageContentService pages) {
        this.pages = pages;
    }

    @PostMapping
    public PageContentDTO create(@Valid @RequestBody PageContentCreateDTO dto) {
        PageContent saved = pages.create(fromCreate(dto));
        return toDto(saved);
    }

    @GetMapping("/{id}")
    public PageContentDTO get(@PathVariable Integer id) {
        return toDto(pages.get(id));
    }

    @GetMapping
    public List<PageContentDTO> getAll() {
        return pages.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public PageContentDTO update(@PathVariable Integer id, @Valid @RequestBody PageContentUpdateDTO dto) {
        PageContent current = pages.get(id);
        applyUpdate(current, dto);
        PageContent saved = pages.update(id, current);
        return toDto(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        pages.delete(id);
    }

    /* --- mapping --- */
    private PageContentDTO toDto(PageContent e) {
        return new PageContentDTO(
                e.getId(),
                e.getPageName(),
                e.getTitle(),
                e.getContent()
        );
    }

    private PageContent fromCreate(PageContentCreateDTO dto) {
        PageContent e = new PageContent();
        e.setPageName(dto.getPageName());
        e.setTitle(dto.getTitle());
        e.setContent(dto.getContent());
        return e;
    }

    private void applyUpdate(PageContent e, PageContentUpdateDTO dto) {
        if (dto.getPageName() != null) e.setPageName(dto.getPageName());
        if (dto.getTitle() != null)    e.setTitle(dto.getTitle());
        if (dto.getContent() != null)  e.setContent(dto.getContent());
    }
}