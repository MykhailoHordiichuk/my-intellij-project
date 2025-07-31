package spring_data_rest.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {

    private String email;
    private String password;

    private String firstName;
    private String lastName;

    private Integer age;
    private String phoneNumber;

}