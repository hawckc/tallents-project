package ittalentss11.traveller_online.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Comment {
    private int id;
    private String text;
    private int postId;
    private int userId;

}
