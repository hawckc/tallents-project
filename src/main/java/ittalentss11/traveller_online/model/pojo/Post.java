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
    private User user;
    private Location location;
    private Category category;
    @Column
    private String videoUrl;
    @Column
    private String description;
    @Column
    private String otherInfo;

}
