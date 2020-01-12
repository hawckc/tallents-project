package ittalentss11.traveller_online.model.dto;

import ittalentss11.traveller_online.model.pojo.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewPostDTO {
    private long userId;
    private long categoryId;
    private String coordinates;
    private String mapUrl;
    private String locationName;
    private String description;
    private String videoUrl;
    private String otherInfo;

    public ViewPostDTO(Post post) {
        this.userId = post.getUser().getId();
        this.categoryId = post.getCategory().getId();
        this.coordinates = post.getCoordinates();
        this.mapUrl = post.getMapUrl();
        this.locationName = post.getLocationName();
        this.description = post.getDescription();
        this.videoUrl = post.getVideoUrl();
        this.otherInfo = post.getOtherInfo();
    }
}
