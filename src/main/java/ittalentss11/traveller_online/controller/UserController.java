package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.*;
import ittalentss11.traveller_online.model.dao.LocationDAO;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dao.UserDao;
import ittalentss11.traveller_online.model.dto.PostDTO;
import ittalentss11.traveller_online.model.dto.UserLoginDTO;
import ittalentss11.traveller_online.model.dto.UserNoSensitiveDTO;
import ittalentss11.traveller_online.model.dto.UserRegDTO;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.Location;
import ittalentss11.traveller_online.model.pojo.Post;
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
    private UserDao userDao;
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private LocationDAO locationDAO;
    //USER REGISTRATION
    @SneakyThrows
    @PostMapping(value = "/users")
    public UserNoSensitiveDTO add (@RequestBody UserRegDTO user, HttpSession session){
        //VERIFICATIONS:
        //Check if username is available
        if (!userDao.usernameIsAvailable(user.getUsername())){
            throw new UsernameTaken();
        }
        //Check if email is not used already
        if (!userDao.emailIsAvailable(user.getEmail())){
            throw new EmailTaken();
        }
        //Check if passwords match
        if (!user.getPassword().equals(user.getConfPassword())){
            throw new NoPassMatch();
        }
        //Verify email validity
        if (!user.checkEmail(user.getEmail())){
            throw new EmailRegisterCheck();
        }
        //Create user and return UserDTO as confirmation
        User created = new User(user);
        userDao.register(created);
        session.setAttribute(USER_LOGGED, created);
        UserNoSensitiveDTO userNoSensitiveDTO = new UserNoSensitiveDTO(created);
        return userNoSensitiveDTO;
    }
    @SneakyThrows
    @PostMapping(value = "/users/login")
    public UserNoSensitiveDTO login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session){
        //Check if username exists
        if (!userDao.foundUsernameForLogin(userLoginDTO.getUsername())){
            throw new AuthorizationError();
        }
        //If it does check for username/password match with BCrypt
        if (userDao.foundUsernameForLogin(userLoginDTO.getUsername())){
            User u = userDao.getUserByUsername(userLoginDTO.getUsername());
            String hash = userLoginDTO.getPassword();
            hash = BCrypt.hashpw(hash, userLoginDTO.getUsername());
            if (!BCrypt.checkpw(hash, u.getPassword())){
                throw new AuthorizationError();
            }
            session.setAttribute(USER_LOGGED, u);
            return new UserNoSensitiveDTO(u.getFirstName(), u.getLastName(), u.getUsername(), u.getEmail());
        }
        return null;
    }
    @SneakyThrows
    @PostMapping("/post")
    public Post post(@RequestBody PostDTO postDTO, @RequestBody Location location, @RequestBody Category category, HttpSession session){
        User u = (User) session.getAttribute(USER_LOGGED);
        if (u == null){
            throw new AuthorizationError();
        }
        Post post = new Post();
        post.setUser(u);
        post.setCategory(postDTO.getCategory());
        post.setDescription(postDTO.getDescription());
        //first we insert location
        locationDAO.insertLocation(post.getLocation());
        post.setLocation(postDTO.getLocation());
        postDAO.post(post);
        return post;
    }

    //FOR TESTING
    @GetMapping(value = "/test")
    public String wazaa (){
        String x = "All is fine";
        return x;
    }
}
