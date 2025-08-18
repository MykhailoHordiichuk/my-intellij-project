package spring_data_rest.dto.page;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class PageContentDTO {
    private Integer id;
    private String pageName;
    private String title;
    private String content;
}