package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.model.dao.DBDao;
import ittalentss11.traveller_online.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private DBDao dao;


    //USER REGISTRATION
    @PostMapping(value = "/users")
    public User add (@RequestBody User user){
        return user;
    }

//    @ExceptionHandler(value = IOException.class)
//    public String exceptions (){
//        return "Can't add user";
//    }

    //getting all users from db
    @GetMapping(value = "/allUsers")
    public List<User> users (){
        return dao.getAllUsers();
    }

    //testing to see if app is running
    @GetMapping(value = "/test")
    public String wazaa (){
        String x = "All is fine";
        return x;
    }
}
