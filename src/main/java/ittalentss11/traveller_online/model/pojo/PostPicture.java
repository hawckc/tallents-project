package ittalentss11.traveller_online.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostPicture {
    private long id;
    private Post post;
    private String pictureUrl;
}
