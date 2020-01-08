package ittalentss11.traveller_online.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
@Entity
@Table(name = "posts")



//TODO make join for USER, LOCATION AND CATEGORY


public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn (name = "location_id")
    private Location location;
    @ManyToOne
    @JoinColumn (name = "category_id")
    private Category category;
    @Column
    private String videoUrl;
    @Column
    private String description;
    @Column
    private String otherInfo;

}
