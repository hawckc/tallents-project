package ittalentss11.traveller_online.model.dto;
import ittalentss11.traveller_online.model.pojo.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ViewPostsAndLikesDTO {
    private long id;
    private long userId;
    private long categoryId;
    private String coordinates;
    private String mapUrl;
    private String locationName;
    private String description;
    private String videoUrl;
    private String otherInfo;
    private LocalDateTime dateTime;
    private int likes;
}
