package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.AuthorizationException;
import ittalentss11.traveller_online.model.dao.CategoryDAO;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dto.PostDTO;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class PostController {
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    //Posting a post:
    @SneakyThrows
    @PostMapping("/posts")
    public String addPost(@RequestBody PostDTO postDTO, HttpSession session){
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        Post post = new Post();
        //Adding to post: user, description of pictures, video url if any, info for video, categoryID, coordinates
        //mapUrl and location name
        //TODO: WE NEED VERIFICATIONS FOR: description not null and max 200 chars,
        // location verification (maybe add an API that will get us coordinates and location name from map url)
        //TODO: this code seems ugly, can we improve it?
        //TODO: how do we make sure that a user doesn't spam post the same thing? is it illegal at all?
        post.setUser(u);
        post.setDescription(postDTO.getDescription());
        post.setVideoUrl(postDTO.getVideoUrl());
        post.setOtherInfo(postDTO.getOtherInfo());
        post.setCategory(categoryDAO.getCategoryById(postDTO.getCategoryId()));
        post.setCoordinates(postDTO.getCoordinates());
        post.setMapUrl(postDTO.getMapUrl());
        post.setLocationName(postDTO.getLocationName());
        postDAO.post(post);
        //return post;
        //CHANGED THE RETURN TYPE TO STRING BECAUSE WE'RE RETURNING THE CRYPTED PASSWORD when returning
        //THE USER JSON
        return "Your post was successfully added!";
    }
}
