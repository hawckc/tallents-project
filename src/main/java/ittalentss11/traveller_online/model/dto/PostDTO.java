package ittalentss11.traveller_online.model.dto;
import ittalentss11.traveller_online.model.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PostDTO {
    private long categoryId;
    @NotNull(message = "Please fill coordinates.")
    @NotBlank(message = "Your coordinates cannot be empty.")
    private String coordinates;
    @NotNull(message = "Please fill coordinates.")
    @NotBlank(message = "Your coordinates cannot be empty.")
    private String mapUrl;
    @NotNull(message = "Please fill mapurl.")
    @NotBlank(message = "Your mapurl cannot be empty.")
    private String locationName;
    @NotNull(message = "Please fill coordinates.")
    @NotBlank(message = "Your coordinates cannot be empty.")
    @Size(min = 1, max = 200)
    private String description;
    private String videoUrl;
    private String otherInfo;

    public boolean checkCoordinates(String coordinates){
        String correct = "^[0-9]* [0-9]{2,7}$";
        Pattern pattern = Pattern.compile(correct);
        if (coordinates == null){
            return false;
        }
        else {
            return pattern.matcher(coordinates).matches();
        }
    }

}
