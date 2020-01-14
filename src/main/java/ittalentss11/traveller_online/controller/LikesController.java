package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.controller.controller_exceptions.AuthorizationException;
import ittalentss11.traveller_online.model.dao.CommentDAO;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dto.CommentLikesDTO;
import ittalentss11.traveller_online.model.dto.LikeAndDislikeDTO;
import ittalentss11.traveller_online.model.pojo.Comment;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.User;
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
    public LikeAndDislikeDTO likePost(@PathVariable("id") Long id, HttpSession session){
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
        if (post.isLikedByUser(u)){
            //if post is already liked, remove like
            post.removeLikeByUser(u);
            postDAO.save(post);
            return null;
        }
        post.addLikeByUser(u);
        postDAO.save(post);
        return new LikeAndDislikeDTO(u.getId(), post.getId());
    }

    @SneakyThrows
    @GetMapping("/dislikes/{id}")
    public LikeAndDislikeDTO dislikePost(@PathVariable("id") Long id, HttpSession session){
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
        if (post.isDislikedByUser(u)){
            //if post is already disliked, remove dislike
            post.removeDislikeByUser(u);
            postDAO.save(post);
            return null;
        }
        post.addDislikeByUser(u);
        postDAO.save(post);
        return new LikeAndDislikeDTO(u.getId(), post.getId());
    }
    @SneakyThrows
    @GetMapping("/comments/{id}/likes")
    public CommentLikesDTO likeComment(@PathVariable("id") Long id, HttpSession session){
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        //Getting and verifying comment ID
        Comment comment = commentDAO.getCommentById(id);
        if (comment.isLikedByUser(u)){
            comment.removeCommentLikeByUser(u);
            commentDAO.save(comment);
            return null;
        }
        comment.addCommentLikeByUser(u);
        commentDAO.save(comment);
        return new CommentLikesDTO(u.getId(), comment.getId());
    }
}
