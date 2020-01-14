package ittalentss11.traveller_online.model.dto;

import ittalentss11.traveller_online.model.pojo.PostPicture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PictureDTO {
    private long id;
    private String pictureUrl;

    public PictureDTO(PostPicture picture) {
        this.id = picture.getId();
        this.pictureUrl = picture.getPictureUrl();
    }
}
