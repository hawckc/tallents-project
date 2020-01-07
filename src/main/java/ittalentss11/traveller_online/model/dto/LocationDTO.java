package ittalentss11.traveller_online.model.dto;
import ittalentss11.traveller_online.model.pojo.Location;
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
public class LocationDTO {
    private String name;
    private String coordinates;
    private String mapUrl;
    public LocationDTO(Location location){
        this.name = location.getName();
        this.coordinates = location.getCoordinates();
        this.mapUrl = location.getMapUrl();
    }
}
