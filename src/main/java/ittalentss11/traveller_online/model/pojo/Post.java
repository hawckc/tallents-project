package ittalentss11.traveller_online.model.pojo;

import ittalentss11.traveller_online.model.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Component
public class Post {
    private long id;
    @Autowired
    private User user;
    @Autowired
    private Location location;
    @Autowired
    private Category category;
    private String videoUrl;
    private String description;
    private String otherInfo;

}
