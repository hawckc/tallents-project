package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.controller.controller_exceptions.AuthorizationException;
import ittalentss11.traveller_online.controller.controller_exceptions.BadRequestException;
import ittalentss11.traveller_online.controller.controller_exceptions.MissingCategoryException;
import ittalentss11.traveller_online.controller.controller_exceptions.WrongCoordinatesException;

import ittalentss11.traveller_online.model.dao.CategoryDAO;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dto.PostDTO;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        post.setUser(u);
        post.setDescription(postDTO.getDescription());
        post.setVideoUrl(postDTO.getVideoUrl());
        post.setOtherInfo(postDTO.getOtherInfo());
        //validate if there is a category with id
        Category category = categoryDAO.getCategoryById(postDTO.getCategoryId());
        if (category == null){
            throw new MissingCategoryException();
        }
        post.setCategory(categoryDAO.getCategoryById(postDTO.getCategoryId()));
        if (!postDTO.checkCoordinates(postDTO.getCoordinates())){
            throw new WrongCoordinatesException();
        }
        post.setCoordinates(postDTO.getCoordinates());
        post.setMapUrl(postDTO.getMapUrl());
        post.setLocationName(postDTO.getLocationName());
        postDAO.addPost(post);
        //return post;
        //CHANGED THE RETURN TYPE TO STRING BECAUSE WE'RE RETURNING THE CRYPTED PASSWORD when returning
        //THE USER JSON
        return "Your post was successfully added!";
    }
    @SneakyThrows
    @PutMapping("/posts/{id}/upload")
    public String addPicture(@RequestPart(value = "picture") MultipartFile multipartFile, @PathVariable("id") long id,
                             HttpSession session){
        //first we check if the use is logged
        User user = (User) session.getAttribute(UserController.USER_LOGGED);
        if (user == null){
            throw new AuthorizationException();
        }
        // we check if there is a post with id
        Post post = postDAO.getPostById(id);
        if (post == null){
            throw new BadRequestException();
        }
        // create a folder with the same name d:/tallents/png
        //name the file picture_url_id.png
        //move to folder
        //write in posts_pictures post_id and picture url
        return multipartFile.getName();
    }
}
