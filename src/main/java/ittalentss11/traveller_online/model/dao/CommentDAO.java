package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.model.pojo.Comment;
import ittalentss11.traveller_online.model.repository_ORM.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentDAO {
    @Autowired
    CommentRepository commentRepository;

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }
}
