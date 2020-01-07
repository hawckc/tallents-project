package ittalentss11.traveller_online.controller;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
@RestController
public class UserController {
    public static final String USER_LOGGED = "logged";
    @Autowired
    private UserDao dao;
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private LocationDAO locationDAO;
    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    //USER REGISTRATION
    @SneakyThrows
    @PostMapping(value = "/users")
    public UserNoSensitiveDTO add (@RequestBody UserRegDTO user, HttpSession session){
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
        session.setAttribute(USER_LOGGED, created);
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
            //boolean success = argon2.verify(u.getPassword(), userLoginDTO.getPassword());
            if (argon2.verify(u.getPassword(), userLoginDTO.getPassword()) == false){
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
