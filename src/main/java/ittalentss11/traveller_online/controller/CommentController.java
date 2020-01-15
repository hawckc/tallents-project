package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dto.CommentDTO;
import ittalentss11.traveller_online.model.pojo.Comment;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.User;
import ittalentss11.traveller_online.model.repository_ORM.CommentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
public class CommentController {
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LoginVerificationController loginVerification;

    @SneakyThrows
    @PostMapping("/comments/{id}")
    public CommentDTO comment(@RequestBody CommentDTO commentDTO, @PathVariable("id") Long id, HttpSession session){
        User u = loginVerification.checkIfLoggedIn(session);
        Post post = postDAO.getPostById(id);
        Comment comment = new Comment();
        comment.setUser(u);
        comment.setPost(post);
        comment.setText(commentDTO.getComment());
        commentRepository.save(comment);
        commentDTO.setId(comment.getId());
        return commentDTO;
    }
}
