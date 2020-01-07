package ittalentss11.traveller_online.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNoSensititiveDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;

}
