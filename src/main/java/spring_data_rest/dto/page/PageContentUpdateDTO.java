package spring_data_rest.dto.page;

import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class PageContentUpdateDTO {
    @Size(max = 100)
    private String pageName;

    @Size(max = 255)
    private String title;

    @Size(max = 5000)
    private String content;
}