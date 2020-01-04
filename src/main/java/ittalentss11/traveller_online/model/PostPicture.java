package ittalentss11.traveller_online.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostPicture {
    private int id;
    private int postId;
    private String pictureUrl;
}
