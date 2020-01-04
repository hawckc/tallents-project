package ittalentss11.traveller_online.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Post {
    private int id;
    private int userId;
    private int locationId;
    private int categoryId;
    private String videoUrl;
    private String description;
    private String otherInfo;

}
