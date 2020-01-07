package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.*;
import ittalentss11.traveller_online.model.dao.UserDao;
import ittalentss11.traveller_online.model.dto.UserLoginDTO;
import ittalentss11.traveller_online.model.dto.UserNoSensititiveDTO;
import ittalentss11.traveller_online.model.dto.UserRegDTO;
import ittalentss11.traveller_online.model.pojo.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDao dao;


    //USER REGISTRATION
    @SneakyThrows
    @PostMapping(value = "/users")
    public UserNoSensititiveDTO add (@RequestBody UserRegDTO user){
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
        UserNoSensititiveDTO userNoSensititiveDTO = new UserNoSensititiveDTO(user);
        return userNoSensititiveDTO;
    }
    @SneakyThrows
    @PostMapping(value = "/users/login")
    public UserNoSensititiveDTO login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session){
        //validate
        if (dao.foundUsernameForLogin(userLoginDTO.getUsername()) == false){
            throw new NoSuchUsername();
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
