package ittalentss11.traveller_online.model.dto;
import ittalentss11.traveller_online.model.pojo.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PostDTO {
    private Location location;
    private Category category;
    private String description;

}
