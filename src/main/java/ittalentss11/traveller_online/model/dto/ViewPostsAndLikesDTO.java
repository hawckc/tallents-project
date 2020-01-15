package ittalentss11.traveller_online.model.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;
    private int likes;

    public ViewPostsAndLikesDTO(ViewPostDTO viewPostDTO, int likes) {
        this.id = viewPostDTO.getId();
        this.userId = viewPostDTO.getUserId();
        this.categoryId = viewPostDTO.getCategoryId();
        this.coordinates = viewPostDTO.getCoordinates();
        this.mapUrl = viewPostDTO.getMapUrl();
        this.locationName = viewPostDTO.getLocationName();
        this.description = viewPostDTO.getDescription();
        this.videoUrl = viewPostDTO.getVideoUrl();
        this.otherInfo = viewPostDTO.getOtherInfo();
        this.dateTime = viewPostDTO.getDateTime();
        this.likes = likes;
    }
}
