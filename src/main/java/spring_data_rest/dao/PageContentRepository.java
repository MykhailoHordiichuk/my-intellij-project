package spring_data_rest.dao;

import spring_data_rest.entity.PageContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageContentRepository extends JpaRepository<PageContent, Integer> {
}
