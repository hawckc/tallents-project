package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.controller.controller_exceptions.*;
import ittalentss11.traveller_online.model.dao.CategoryDAO;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dao.UserDAO;
import ittalentss11.traveller_online.model.dao.PostPictureDao;
import ittalentss11.traveller_online.model.dto.PostDTO;
import ittalentss11.traveller_online.model.dto.ViewPostDTO;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.PostPicture;
import ittalentss11.traveller_online.model.pojo.User;
import ittalentss11.traveller_online.model.repository_ORM.PostRepository;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
public class PostController {
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private PostPictureDao postPictureDao;
    private static final int MAX_PICTURES = 3;

    //Posting a post:
    @SneakyThrows
    @PostMapping("/posts")
    public String addPost(@RequestBody PostDTO postDTO, HttpSession session) {
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null) {
            throw new AuthorizationException();
        }
        Post post = new Post();
        //Adding to post: user, description of pictures, video url if any, info for video, categoryID, coordinates
        //mapUrl and location name
        post.setUser(u);
        post.setDescription(postDTO.getDescription());
        post.setVideoUrl(postDTO.getVideoUrl());
        post.setOtherInfo(postDTO.getOtherInfo());
        //validate if there is a category with id
        Category category = categoryDAO.getByName(postDTO.getCategoryName());
        if (category == null) {
            throw new MissingCategoryException();
        }
        post.setCategory(category);
        if (postDTO.checkCoordinates(postDTO.getCoordinates()) == false) {
                throw new WrongCoordinatesException();
        }
        post.setCoordinates(postDTO.getCoordinates());
        post.setMapUrl(postDTO.getMapUrl());
        post.setLocationName(postDTO.getLocationName());
        postDAO.addPost(post);
        return "Your post was successfully added!";
    }
    //View post (don't need to be logged in I think?)
    @SneakyThrows
    @GetMapping("/posts/{id}")
    public ViewPostDTO viewPost (@PathVariable("id") long id){
        Post post = postDAO.getPostById(id);
        ViewPostDTO viewPostDTO = new ViewPostDTO(post);
        return viewPostDTO;
    }
    //Changed postmapping url from /posts/{id}/upload to /posts/{id}/pictures
    @SneakyThrows
    @PostMapping("/posts/{id}/pictures")
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
            //TODO this is unreachable because getPostById throws the exception already
            throw new BadRequestException();
        }
        if (postPictureDao.getAllPictures((int) post.getId()) >= MAX_PICTURES){
            throw new PostPicturePerPostException();
        }
        if (post.getUser().getUsername().equals(user.getUsername()) == false){
            throw new BadRequestException();
        }
        //TODO: this is not reached, if i dont attach anything it says 500 in postman:
        // "Current request is not a multipart request"
        if (multipartFile == null){
            throw new BadRequestException();
        }
        String path = "C://posts//png//";
        String pictureName = PostController.getNameForUpload(multipartFile.getOriginalFilename(), path, user);
        if (pictureName.split("\\.")[1].equals("png") == false){
            throw new BadRequestException();
        }
        File picture = new File(pictureName);
        FileOutputStream fos = new FileOutputStream(picture);
        //TODO writing of the file should be done in another thread
        fos.write(multipartFile.getBytes());
        fos.close();
        PostPicture postPicture = new PostPicture();
        postPictureDao.addPostPicture(post, pictureName);
        postPicture.setPost(post);
        postPicture.setPictureUrl(pictureName);
        //check if there are no more than 3 pictures
        //check if the file is a picture
        // create a folder with the same name d:/tallents/png
        //name the file picture_url_id.png
        //move to folder
        //write in posts_pictures post_id and picture url
        return picture.getName();
    }
    @SneakyThrows
    @PutMapping("/posts/{id}/videos")
    public String addVideos(@RequestPart(value = "videos") MultipartFile multipartFile, @PathVariable("id") long id,
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
        if (post.getUser().getUsername().equals(user.getUsername()) == false){
            throw new BadRequestException();
        }
        if (multipartFile == null){
            throw new BadRequestException();
        }
        String path = "C://posts//videos//";
        String videosName = PostController.getNameForUpload(multipartFile.getOriginalFilename(), path, user);
        //System.out.println(namesWhole);
        if (videosName.split("\\.")[1].equals("avi") == false){
            throw new BadRequestException();
        }
        File videos = new File(videosName);
        FileOutputStream fos = new FileOutputStream(videos);
        fos.write(multipartFile.getBytes());
        fos.close();
        postDAO.addVideos(post, videosName);
        return videos.getName();
    }
    public static String getNameForUpload(String name, String path, User user){
        String[] all = name.split("\\.", 2);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy-MM-dd-hh-mm-ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String parse = localDateTime.format(dateTimeFormatter);
        String nameWithoutId = all[0];
        String formatForPicture = all[1];
        String nameWithId = nameWithoutId + "_" + parse + "_" + user.getId() + "." + formatForPicture;
        System.out.println(name);
        return path + nameWithId;
    }

    @SneakyThrows
    @GetMapping("/posts/{pId}/users/{uId}")
    public String tagSomeone(@PathVariable("pId") Long pId, @PathVariable("uId") Long uId, HttpSession session){
        //Is the user logged in?
        User u = (User) session.getAttribute(UserController.USER_LOGGED);
        if (u == null){
            throw new AuthorizationException();
        }
        //Check if user is trying to tag someone on his post
        Post post = postDAO.getPostById(pId);
        if (post.getUser().getId()!=u.getId()){
            throw new AuthorizationException("You cannot tag people on someone else's post");
        }
        User taggedUser = userDAO.getUserById(uId);
        post.addTaggedUser(taggedUser);
        postDAO.save(post);
        return "You just tagged someone!";
    }
    @SneakyThrows
    @GetMapping("/postsByUname/{user_name}")
    public ArrayList<ViewPostDTO> getPostByUsername(@PathVariable("user_name") String username){
        ArrayList<ViewPostDTO> all = postDAO.getPostsByUsername(username);
        return all;
    }
}
