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
    
    public UserNoSensitiveDTO(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

}
