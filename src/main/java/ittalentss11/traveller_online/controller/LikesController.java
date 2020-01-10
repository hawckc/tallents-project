package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.controller.controller_exceptions.AuthorizationException;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.User;
import ittalentss11.traveller_online.model.repository_ORM.PostRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class LikesController {
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private PostRepository postRepository;


    @SneakyThrows
    @GetMapping("/likes/{id}")
    public String likePost(@PathVariable("id") Long id, HttpSession session){
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        //Getting and verifying post ID
        Post post = postDAO.getPostById(id);
        post.addLikeByUser(u);
        postRepository.save(post);
        return "You just liked that post!";
    }

    @SneakyThrows
    @GetMapping("/dislikes/{id}")
    public String dislikePost(@PathVariable("id") Long id, HttpSession session){
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        //Getting and verifying post ID
        Post post = postDAO.getPostById(id);
        post.addDislikeByUser(u);
        postRepository.save(post);
        return "You just disliked that post!";
    }
}
