package ittalentss11.traveller_online.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ittalentss11.traveller_online.model.dto.UserRegDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    
    public User(UserRegDTO userRegDTO){
        this.firstName = userRegDTO.getFirstName();
        this.lastName = userRegDTO.getLastName();
        this.username = userRegDTO.getUsername();
        this.email = userRegDTO.getEmail();
        this.password = userRegDTO.getPassword();
        
    }
}
