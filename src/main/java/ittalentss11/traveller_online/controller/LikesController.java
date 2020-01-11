package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.controller.controller_exceptions.AuthorizationException;
import ittalentss11.traveller_online.model.dao.CommentDAO;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.pojo.Comment;
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
    private CommentDAO commentDAO;

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
        if (post.isDislikedByUser(u)){
            post.removeDislikeByUser(u);
        }
        post.addLikeByUser(u);
        postDAO.save(post);
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
        if (post.isLikedByUser(u)){
            post.removeLikeByUser(u);
        }
        post.addDislikeByUser(u);
        postDAO.save(post);
        return "You just disliked that post!";
    }
    @SneakyThrows
    @GetMapping("/comments/{id}/likes")
    public String likeComment(@PathVariable("id") Long id, HttpSession session){
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        //Getting and verifying comment ID
        Comment comment = commentDAO.getCommentById(id);
        comment.addCommentLikeByUser(u);
        commentDAO.save(comment);
        return "You just liked that comment!";
    }
}
