package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.*;
import ittalentss11.traveller_online.model.dao.UserDao;
import ittalentss11.traveller_online.model.dto.UserLoginDTO;
import ittalentss11.traveller_online.model.dto.UserNoSensitiveDTO;
import ittalentss11.traveller_online.model.dto.UserRegDTO;
import ittalentss11.traveller_online.model.pojo.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    public static final String USER_LOGGED = "logged";
    @Autowired
    private UserDao dao;


    //USER REGISTRATION
    @SneakyThrows
    @PostMapping(value = "/users")
    public UserNoSensitiveDTO add (@RequestBody UserRegDTO user){
        //VERIFICATIONS:
        //Check if username is available
        if (!dao.usernameIsAvailable(user.getUsername())){
            throw new UsernameTaken();
        }
        //Check if email is not used already
        if (!dao.emailIsAvailable(user.getEmail())){
            throw new EmailTaken();
        }
        //Check if passwords match
        if (!user.getPassword().equals(user.getConfPassword())){
            throw new NoPassMatch();
        }
        if (user.checkEmail(user.getEmail()) == false){
            throw new EmailRegisterCheck();
        }
        //Create user and return it as confirmation // return a userdto which having username and email not user
        User created = new User(user);
        dao.register(created);
        UserNoSensitiveDTO userNoSensitiveDTO = new UserNoSensitiveDTO(user);
        return userNoSensitiveDTO;
    }
    @SneakyThrows
    @PostMapping(value = "/users/login")
    public UserNoSensitiveDTO login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session){
        //validate
        if (dao.foundUsernameForLogin(userLoginDTO.getUsername()) == false){
            throw new NoSuchUsername();
        }
        if (dao.foundUsernameForLogin(userLoginDTO.getUsername()) == true){
            User u = dao.getUserByUsername(userLoginDTO.getUsername());
            String hash = userLoginDTO.getPassword();
            hash = BCrypt.hashpw(hash, userLoginDTO.getUsername());
            if (BCrypt.checkpw(hash, u.getPassword()) == false){
                throw new AuthorizationError();
            }
            session.setAttribute(USER_LOGGED, u);
            return new UserNoSensitiveDTO(u.getFirstName(), u.getLastName(), u.getUsername(), u.getEmail());
        }
        return null;
    }

    //FOR TESTING
    @GetMapping(value = "/test")
    public String wazaa (){
        String x = "All is fine";
        return x;
    }
}
