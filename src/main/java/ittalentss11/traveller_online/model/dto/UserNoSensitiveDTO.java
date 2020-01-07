package ittalentss11.traveller_online.model.dto;
import ittalentss11.traveller_online.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNoSensitiveDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    
    public UserNoSensitiveDTO(UserRegDTO userRegDTO){
        this.firstName = userRegDTO.getFirstName();
        this.lastName = userRegDTO.getLastName();
        this.username = userRegDTO.getUsername();
        this.email = userRegDTO.getEmail();
    }
    public UserNoSensitiveDTO(User u){
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.username = u.getUsername();
        this.email = u.getEmail();
    }

}
