package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.EmailTaken;
import ittalentss11.traveller_online.controller.controller_exceptions.NoPassMatch;
import ittalentss11.traveller_online.controller.controller_exceptions.UsernameTaken;
import ittalentss11.traveller_online.model.dao.UserDao;
import ittalentss11.traveller_online.model.dto.UserRegDTO;
import ittalentss11.traveller_online.model.pojo.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDao dao;


    //USER REGISTRATION
    @SneakyThrows
    @PostMapping(value = "/users")
    public UserRegDTO add (@RequestBody UserRegDTO user){
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
        //Create user and return it as confirmation
        dao.register(user);
        return user;
    }

    //FOR TESTING
    @GetMapping(value = "/test")
    public String wazaa (){
        String x = "All is fine";
        return x;
    }
}
