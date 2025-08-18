package spring_data_rest.service;

import spring_data_rest.entity.PageContent;
import java.util.List;

public interface PageContentService {
    PageContent create(PageContent input);
    PageContent get(Integer id);
    List<PageContent> getAll();
    PageContent update(Integer id, PageContent input);
    void delete(Integer id);
}