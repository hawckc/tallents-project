package ittalentss11.traveller_online.model.pojo;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//equals and hashcode is for many to many (likes)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
@Entity
@Table(name = "posts")

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn (name = "category_id")
    private Category category;
    @Column
    private String videoUrl;
    @Column
    private String description;
    @Column
    private String otherInfo;
    @Column
    private String coordinates;
    @Column
    private String mapUrl;
    @Column
    private String locationName;

    //for likes
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usersThatLiked = new ArrayList<>();

    //TODO : make a method tha prevents stacking likes, dislikes and tags by one user
    //for dislikes
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "dislikes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usersThatDisliked = new ArrayList<>();
    //for tags
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usersTagged = new ArrayList<>();

    public void addLikeByUser(User user) {
        usersThatLiked.add(user);
        user.getLikedPosts().add(this);
    }
    public void addDislikeByUser(User user) {
        usersThatDisliked.add(user);
        user.getDislikedPosts().add(this);
    }
    public void addTaggedUser(User user) {
        usersTagged.add(user);
        user.getTaggedInPosts().add(this);
    }
}
