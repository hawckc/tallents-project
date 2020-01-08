package ittalentss11.traveller_online.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ExceptionDTO {
    private String msg;
    private int status;
    private LocalDateTime time;
    private String exceptionType;
}
