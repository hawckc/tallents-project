package ittalentss11.traveller_online.model.pojo;
import ittalentss11.traveller_online.model.dto.UserRegDTO;
import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.*;

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
    //For post likes
    @ManyToMany(mappedBy = "usersThatLiked")
    private Set<Post> likedPosts = new HashSet<>();
    //For post dislikes
    @ManyToMany(mappedBy = "usersThatDisliked")
    private Set<Post> dislikedPosts = new HashSet<>();
    //For tags
    @ManyToMany(mappedBy = "usersTagged")
    private Set<Post> taggedInPosts = new HashSet<>();
    //For comment likes
    @ManyToMany(mappedBy = "usersThatLikedComment")
    private Set<Comment> likedComments = new HashSet<>();

    //Followers
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "users_follow_users",
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();
    //Following
    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();

    public User(UserRegDTO userRegDTO){
        this.firstName = userRegDTO.getFirstName();
        this.lastName = userRegDTO.getLastName();
        this.username = userRegDTO.getUsername();
        this.email = userRegDTO.getEmail();
        this.password = userRegDTO.getPassword();
    }
    public User (long id, String firstName, String lastName, String username, String email, String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //TODO : DO WE NEED TO UNFOLLOW?
    public void addFollower(User user) {
        followers.add(user);
        user.following.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
