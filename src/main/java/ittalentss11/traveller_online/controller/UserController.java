package ittalentss11.traveller_online.controller;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import ittalentss11.traveller_online.controller.controller_exceptions.*;

import ittalentss11.traveller_online.model.dao.LocationDAO;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dao.UserDao;
import ittalentss11.traveller_online.model.dto.*;
import ittalentss11.traveller_online.model.pojo.*;
import ittalentss11.traveller_online.model.repository_ORM.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {
    public static final String USER_LOGGED = "logged";
    @Autowired
    private UserDao userDao;
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private LocationDAO locationDAO;
    @Autowired
    private UserRepository userRepository;

    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    //USER REGISTRATION
    @SneakyThrows
    @PostMapping(value = "/users")
    public UserNoSensitiveDTO add (@RequestBody UserRegDTO user, HttpSession session){
        //VERIFICATIONS:
        //Check if username is available
        if (!userDao.usernameIsAvailable(user.getUsername())){
            throw new UsernameTaken("This username already exists, please pick another one.");
        }
        //Check if email is not used already
        if (!userDao.emailIsAvailable(user.getEmail())){
            throw new EmailTaken("Email already exists, please pick another one.");
        }
        //Check if passwords match
        if (!user.getPassword().equals(user.getConfPassword())){
            throw new NoPassMatch("Wrong password setup, please make sure to confirm your password.");
        }
        //Verify email validity
        if (!user.checkEmail(user.getEmail())){
            throw new RegisterCheck("You have entered an invalid email.");
        }
        //Verify username and password
        if (!user.checkUsernameAndPass(user.getUsername(), user.getPassword())){
            throw new RegisterCheck("Make sure that your username/password contains (3-40 characters) alphanumericals, dots, dashes or underscores");
        }
        //Verify first and last names
        if (!user.firstAndLastNames(user.getFirstName(), user.getLastName())){
            throw new RegisterCheck("Your first and last names must contain only alphabetical characters (2-40 chars)");
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
            throw new AuthorizationError("Wrong credentials, please verify your username and password.");
        }
        //If it does check for username/password match with argon2
        if (userDao.foundUsernameForLogin(userLoginDTO.getUsername())){
            User u = userDao.getUserByUsername(userLoginDTO.getUsername());
            //String hash = userLoginDTO.getPassword();
            //boolean success = argon2.verify(u.getPassword(), userLoginDTO.getPassword());
            if (!argon2.verify(u.getPassword(), userLoginDTO.getPassword())){
                throw new AuthorizationError("Wrong credentials, please verify your username and password.");
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
        //check if location is valid and has valid json
        //check if category is valid
        post.setCategory(postDTO.getCategory());
        //check if description is valid
        post.setDescription(postDTO.getDescription());
        //first we insert location
        //take category id from categories with getbyname
        //check if post is valid json
        //insert location
        //insert post
        locationDAO.insertLocation(post.getLocation());
        post.setLocation(postDTO.getLocation());
        postDAO.post(post);
        return post;
    }
    @SneakyThrows
    @PostMapping("/comment/{id}")
    public CommentDTO comment(@RequestBody CommentDTO commentDTO, @RequestParam("id") long id, HttpSession session){
        User u = (User) session.getAttribute(USER_LOGGED);
        if (u == null){
            throw new AuthorizationError();
        }
        //select post
        Post post = postDAO.getPostById(id);
        if (post == null){
            throw new WrongRequest();
        }
        //insert new comment
        return null;



    }

    //FOR TESTING====================== MAKE SURE TO DELETE USERREPOSITORY AS WELL AFTER!!!!!!!!!!!!!!!!!!
    @GetMapping(value = "/test")
    public List<User> wazaa (){
        return userRepository.findAll();
    }
}
