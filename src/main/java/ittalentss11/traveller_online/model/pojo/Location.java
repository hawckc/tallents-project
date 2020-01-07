package ittalentss11.traveller_online.model.pojo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ittalentss11.traveller_online.model.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Component
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String coordinates;
    @Column
    private String mapUrl;

    public Location(LocationDTO location){
        this.name = location.getName();
        this.coordinates = location.getCoordinates();
        this.mapUrl = location.getMapUrl();
    }
}
