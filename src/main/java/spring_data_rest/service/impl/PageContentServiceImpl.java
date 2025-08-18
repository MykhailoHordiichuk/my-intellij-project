package spring_data_rest.service.impl;

import spring_data_rest.dao.PageContentRepository;
import spring_data_rest.entity.PageContent;
import spring_data_rest.service.PageContentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class PageContentServiceImpl implements PageContentService {

    private final PageContentRepository repo;

    public PageContentServiceImpl(PageContentRepository repo) {
        this.repo = repo;
    }

    @Override
    public PageContent create(PageContent input) {
        log.info("Creating page content for pageName={}", input.getPageName());
        return repo.save(input);
    }

    @Override
    @Transactional(readOnly = true)
    public PageContent get(Integer id) {
        log.info("Fetching page content id={}", id);
        return repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PageContent not found")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageContent> getAll() {
        log.info("Fetching all page contents");
        return repo.findAll();
    }

    @Override
    public PageContent update(Integer id, PageContent input) {
        log.info("Updating page content id={}", id);
        PageContent existing = get(id);
        BeanUtils.copyProperties(input, existing, "id");
        return repo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting page content id={}", id);
        if (!repo.existsById(id)) {
            log.error("PageContent id={} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PageContent not found");
        }
        repo.deleteById(id);
    }
}