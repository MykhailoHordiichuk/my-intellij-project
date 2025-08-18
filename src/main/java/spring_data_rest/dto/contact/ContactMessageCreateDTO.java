package spring_data_rest.dto.contact;

import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ContactMessageCreateDTO {

    @NotBlank
    private String name;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(max = 1000) // синхронизировано с @Column(length=1000)
    private String message;
}