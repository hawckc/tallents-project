package ittalentss11.traveller_online.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
//User used for registration (2 passwords)
public class UserRegDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String confPassword;
}
