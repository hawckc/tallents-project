package ittalentss11.traveller_online.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

@NoArgsConstructor
@Getter
@Setter
//User used for registration (2 passwords)
public class UserRegDTO {
    @NotNull(message = "Please fill your first name.")
    @NotBlank(message = "Your first name cannot be empty.")
    private String firstName;
    @NotNull(message = "Please fill your last name.")
    @NotBlank(message = "Your last name cannot be empty.")
    private String lastName;
    @NotNull(message = "Please fill your username.")
    @NotBlank(message = "Your username cannot be empty.")
    private String username;
    @NotNull(message = "Please fill your email.")
    @NotBlank(message = "Your email cannot be empty.")
    private String email;
    @NotNull(message = "Please fill your password.")
    @NotBlank(message = "Your password cannot be empty.")
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
    //Username validation
    public boolean checkUsernameAndPass(String username, String password){
        //Accepts  . , _ , - , alpha-numericals, min 3 chars, max 40
        String correct = "[a-zA-Z0-9\\._\\-]{3,40}";
        Pattern pattern = Pattern.compile(correct);
        if (username == null || password == null){
            return false;
        }
        else {
            return pattern.matcher(username).matches() && pattern.matcher(password).matches();
        }
    }
    //Names validation
    public boolean firstAndLastNames(String firstName, String lastName){
        //Accepts only alphabetical chars, 2-40 chars
        String correct = "^[a-zA-Z]*${2,40}";
        Pattern pattern = Pattern.compile(correct);
        if (firstName == null || lastName == null || firstName.isEmpty() || lastName.isEmpty()){
            return false;
        }
        else {
            return pattern.matcher(firstName).matches() && pattern.matcher(lastName).matches();
        }
    }
    public boolean checkPasswordPatterns(String password){
        String correct = "^[a-zA-Z][a-zA-Z0-9]{5,}$";
        Pattern pattern = Pattern.compile(correct);
        if (password == null){
            return false;
        }
        else {
            return pattern.matcher(username).matches() && pattern.matcher(password).matches();
        }
    }
}
