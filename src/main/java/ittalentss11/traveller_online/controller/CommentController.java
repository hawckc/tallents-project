package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.AuthorizationException;
import ittalentss11.traveller_online.controller.controller_exceptions.BadRequestException;
import ittalentss11.traveller_online.model.dto.CommentDTO;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.User;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
//TODO: comment controller is commented for now
//@RestController
//public class CommentController {
//    @SneakyThrows
//    @PostMapping("/comment/{id}")
//    public CommentDTO comment(@RequestBody CommentDTO commentDTO, @RequestParam("id") long id, HttpSession session){
//        User u = (User) session.getAttribute(USER_LOGGED);
//        if (u == null){
//            throw new AuthorizationException();
//        }
//        //select post
//        Post post = postDAO.getPostById(id);
//        if (post == null){
//            throw new BadRequestException();
//        }
//        //insert new comment
//        return null;
//    }
//}
