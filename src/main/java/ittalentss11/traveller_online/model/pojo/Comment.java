package ittalentss11.traveller_online.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
@NoArgsConstructor
@Getter
@Setter
@Component
public class Comment {
    private long id;
    private String text;
    private Post post;
    private User user;

}
