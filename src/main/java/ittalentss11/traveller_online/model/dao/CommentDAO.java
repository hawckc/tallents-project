package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.controller.controller_exceptions.BadRequestException;
import ittalentss11.traveller_online.model.pojo.Comment;
import ittalentss11.traveller_online.model.repository_ORM.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommentDAO {
    @Autowired
    CommentRepository commentRepository;

    public long addComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }

    public Comment getCommentById(Long id) throws BadRequestException {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()){
            return optionalComment.get();
        }
        throw new BadRequestException("Sorry, that post doesn't exist");
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
