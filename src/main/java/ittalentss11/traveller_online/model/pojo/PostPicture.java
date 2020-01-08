package ittalentss11.traveller_online.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
//@Entity
//@Table(name = "post_pictures")
public class PostPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Post post;
    @Column
    private String pictureUrl;
}
