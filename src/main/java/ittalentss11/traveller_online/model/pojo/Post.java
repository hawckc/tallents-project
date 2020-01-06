package ittalentss11.traveller_online.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Post {
    private long id;
    private User user;
    private Location location;
    private Category category;
    private String videoUrl;
    private String description;
    private String otherInfo;

}
