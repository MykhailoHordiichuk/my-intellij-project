package spring_data_rest.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String email;
    private String password;

}
