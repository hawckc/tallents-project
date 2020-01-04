package ittalentss11.traveller_online.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    @JsonIgnore
    private String password;
}
