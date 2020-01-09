package ittalentss11.traveller_online.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Component
//@Entity
//@Table(name = "comments")


//TODO make join for USER AND POST


public class Comment {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //@Column
    private String text;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
