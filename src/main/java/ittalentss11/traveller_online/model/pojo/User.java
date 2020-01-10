package ittalentss11.traveller_online.model.pojo;
import ittalentss11.traveller_online.model.dto.UserRegDTO;
import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//equals and hashcode is for many to many (likes)
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Component
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
    //TODO: for likes
    @ManyToMany(mappedBy = "usersThatLiked")
    private List<Post> likedPosts = new ArrayList<>();
    //
    public User(UserRegDTO userRegDTO){
        this.firstName = userRegDTO.getFirstName();
        this.lastName = userRegDTO.getLastName();
        this.username = userRegDTO.getUsername();
        this.email = userRegDTO.getEmail();
        this.password = userRegDTO.getPassword();
    }
    //TODO: for likes
    public User (long id, String firstName, String lastName, String username, String email, String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
