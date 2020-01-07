package ittalentss11.traveller_online.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@AllArgsConstructor
public class Category {
    @JsonIgnore
    private long id;
    private String name;
}
