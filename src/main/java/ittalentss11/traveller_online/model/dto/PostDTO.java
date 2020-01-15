package ittalentss11.traveller_online.model.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import ittalentss11.traveller_online.model.pojo.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PostDTO {
    private long id;
    private long userId;
    private long categoryId;
    private String coordinates;
    private String mapUrl;
    private String locationName;
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;
    private String otherInfo;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.categoryId = post.getCategory().getId();
        this.coordinates = post.getCoordinates();
        this.mapUrl = post.getMapUrl();
        this.locationName = post.getLocationName();
        this.description = post.getDescription();
        this.otherInfo = post.getOtherInfo();
        this.dateTime = post.getDateTime();
    }

    public boolean checkCoordinates(String coordinates){
        String correct = "^[0-9]* [0-9]{2,9}$";
        Pattern pattern = Pattern.compile(correct);
        if (coordinates == null){
            return false;
        }
        else {
            return pattern.matcher(coordinates).matches();
        }
    }

}
