package ittalentss11.traveller_online.model.pojo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ittalentss11.traveller_online.model.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
@NoArgsConstructor
@Getter
@Setter
@Component
@AllArgsConstructor
public class Location {
    private long id;
    private String name;
    private String coordinates;
    private String mapUrl;
    public Location(LocationDTO location){
        this.name = location.getName();
        this.coordinates = location.getCoordinates();
        this.mapUrl = location.getMapUrl();
    }
}
