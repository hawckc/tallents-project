package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.AuthorizationException;
import ittalentss11.traveller_online.model.pojo.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class LoginVerificationController {
    public User checkIfLoggedIn(HttpSession session) throws AuthorizationException {
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        return u;
    }
}
