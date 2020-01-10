package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.controller.controller_exceptions.AuthorizationException;
import ittalentss11.traveller_online.model.dao.CommentDAO;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dto.CommentDTO;
import ittalentss11.traveller_online.model.pojo.Comment;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

//TODO: can we avoid the logged in verification with interceptors? When we're done with the bulk
@RestController
public class CommentController {
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private CommentDAO commentDAO;

    @SneakyThrows
    @PostMapping("/comments/{id}")
    public CommentDTO comment(@RequestBody CommentDTO commentDTO, @PathVariable("id") Long id, HttpSession session){
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        Comment comment = new Comment();
        comment.setUser(u);
        //Get post ID
        Post post = postDAO.getPostById(id);
        comment.setPost(post);
        //Comment text
        comment.setText(commentDTO.getComment());
        commentDAO.addComment(comment);
        return commentDTO;
    }
}
