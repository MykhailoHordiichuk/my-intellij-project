package spring_data_rest.dto.page;

import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class PageContentCreateDTO {

    @NotBlank @Size(max = 100)
    private String pageName;

    @NotBlank @Size(max = 255)
    private String title;

    @NotBlank @Size(max = 5000)
    private String content;
}