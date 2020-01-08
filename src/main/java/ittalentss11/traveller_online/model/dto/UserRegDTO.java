package ittalentss11.traveller_online.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.regex.Pattern;

@NoArgsConstructor
@Getter
@Setter
//User used for registration (2 passwords)
public class UserRegDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String confPassword;

    //Email validation
    public boolean checkEmail(String email){
        String correct = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(correct);
        if (email == null){
            return false;
        }
        else {
            return pattern.matcher(email).matches();
        }
    }

    //Hashing of password
    public static String hashSecurity(String password, String username){
        String hash = password;
        hash = BCrypt.hashpw(hash, username);
        return hash;
    }
}
