package ittalentss11.traveller_online.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Location {
    private long id;
    private String name;
    private String coordinates;
    private String mapUrl;
}
