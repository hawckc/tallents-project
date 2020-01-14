package ittalentss11.traveller_online.model.dto;

import ittalentss11.traveller_online.model.pojo.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Service
public class ViewPostDTO {
    private long id;
    private long userId;
    private long categoryId;
    private List<PictureDTO> pictures;
    private String coordinates;
    private String mapUrl;
    private String locationName;
    private String description;
    private String videoUrl;
    private String otherInfo;
    private LocalDateTime dateTime;

    public ViewPostDTO(Post post, List<PictureDTO> pictures) {
        this.pictures = pictures;
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.categoryId = post.getCategory().getId();
        this.coordinates = post.getCoordinates();
        this.mapUrl = post.getMapUrl();
        this.locationName = post.getLocationName();
        this.description = post.getDescription();
        this.videoUrl = post.getVideoUrl();
        this.otherInfo = post.getOtherInfo();
        this.dateTime = post.getDateTime();
    }
    public ViewPostDTO(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.categoryId = post.getCategory().getId();
        this.coordinates = post.getCoordinates();
        this.mapUrl = post.getMapUrl();
        this.locationName = post.getLocationName();
        this.description = post.getDescription();
        this.videoUrl = post.getVideoUrl();
        this.otherInfo = post.getOtherInfo();
        this.dateTime = post.getDateTime();
    }
}
