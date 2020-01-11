package ittalentss11.traveller_online.controller;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import ittalentss11.traveller_online.controller.controller_exceptions.*;

import ittalentss11.traveller_online.model.dao.UserDAO;
import ittalentss11.traveller_online.model.dto.*;
import ittalentss11.traveller_online.model.pojo.*;

import ittalentss11.traveller_online.model.repository_ORM.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//TODO : WE NEED TO ADD MODIFICATIONS (change pass, names?, delete post, edit post)

@RestController
public class UserController {
    public static final String USER_LOGGED = "logged";
    @Autowired
    private UserDAO userDao;
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
            throw new UsernameTakenException("This username already exists, please pick another one.");
        }
        //Check if email is not used already
        if (!userDao.emailIsAvailable(user.getEmail())){
            throw new EmailTakenException("Email already exists, please pick another one.");
        }
        //Check if passwords match
        if (!user.getPassword().equals(user.getConfPassword())){
            throw new NoPassMatchException("Wrong password setup, please make sure to confirm your password.");
        }
        //Verify email validity
        if (!user.checkEmail(user.getEmail())){
            throw new RegisterCheckException("You have entered an invalid email.");
        }
        //Verify username and password
        if (!user.checkUsernameAndPass(user.getUsername(), user.getPassword())){
            throw new RegisterCheckException("Make sure that your username/password contains (3-40 characters) alphanumericals, dots, dashes or underscores");
        }
        //Verify first and last names
        if (!user.firstAndLastNames(user.getFirstName(), user.getLastName())){
            throw new RegisterCheckException("Your first and last names must contain only alphabetical characters (2-40 chars)");
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
        User u = null;
        if (!userDao.foundUsernameForLogin(userLoginDTO.getUsername())){
            throw new AuthorizationException("Wrong credentials, please verify your username and password.");
        }
        //If it does check for username/password match with argon2
        u = userDao.getUserByUsername(userLoginDTO.getUsername());
        if (!argon2.verify(u.getPassword(), userLoginDTO.getPassword())){
            throw new AuthorizationException("Wrong credentials, please verify your username and password.");
        }
        session.setAttribute(USER_LOGGED, u);
        return new UserNoSensitiveDTO(u.getFirstName(), u.getLastName(), u.getUsername(), u.getEmail());
    }
    @SneakyThrows
    @GetMapping(value = "/follow/{id}")
    public UserNoSensitiveDTO followUser (@PathVariable("id") Long id, HttpSession session){
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        //Getting and verifying user ID
        User followUser = userDao.getUserById(id);
        followUser.addFollower(u);
        userRepository.save(followUser);
        return new UserNoSensitiveDTO
                (followUser.getFirstName(), followUser.getLastName(), followUser.getUsername(), followUser.getEmail());
    }

    //FOR TESTING====================== MAKE SURE TO DELETE USERREPOSITORY AS WELL AFTER!!!!!!!!!!!!!!!!!!
    @GetMapping(value = "/test")
    public List<User> wazaa (){
        return userRepository.findAll();
    }
}
