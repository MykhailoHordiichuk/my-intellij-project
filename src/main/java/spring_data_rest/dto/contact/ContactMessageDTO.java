package spring_data_rest.dto.contact;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class ContactMessageDTO {
    private Integer id;
    private String name;
    private String email;
    private String message;
    private LocalDateTime sentAt;
}