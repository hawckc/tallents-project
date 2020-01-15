package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.controller.controller_exceptions.*;
import ittalentss11.traveller_online.model.dao.PostDAO;
import ittalentss11.traveller_online.model.dao.UserDAO;
import ittalentss11.traveller_online.model.dao.PostPictureDao;
import ittalentss11.traveller_online.model.dto.*;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.PostPicture;
import ittalentss11.traveller_online.model.pojo.User;
import ittalentss11.traveller_online.model.repository_ORM.CategoryRepository;
import ittalentss11.traveller_online.model.repository_ORM.PostRepository;
import ittalentss11.traveller_online.model.repository_ORM.UserRepository;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class PostController {
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PostPictureDao postPictureDao;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LoginVerificationController loginVerification;
    private static final int MAX_PICTURES = 3;

    //============ ADD A POST ==================//
    @SneakyThrows
    @PostMapping("/posts")
    public PostDTO addPost(@Valid @RequestBody PostDTO postDTO, HttpSession session) {
        //Is the user logged in?
        User u = loginVerification.checkIfLoggedIn(session);
        Post post = new Post();
        post.setUser(u);
        if (postDTO.getMapUrl() == null || postDTO.getMapUrl().isEmpty()){
            throw new BadRequestException("Make sure to fill your map URL.");
        }
        try {
            URL url = new URL(postDTO.getMapUrl());
            URLConnection conn = url.openConnection();
            conn.connect();
        }
        catch (MalformedURLException e){
            throw new MalformedURLException("The link you are uploading is invalid.");
        }
        post.setMapUrl(postDTO.getMapUrl());
        if (postDTO.getLocationName() == null || postDTO.getLocationName().isEmpty()){
            throw new BadRequestException("Make sure to fill your location name.");
        }
        post.setLocationName(postDTO.getLocationName());
        if (postDTO.getDescription() == null || postDTO.getDescription().isEmpty()){
            throw new BadRequestException("Make sure to add a short description of your trip.");
        }
        post.setDescription(postDTO.getDescription());
        post.setOtherInfo(postDTO.getOtherInfo());
        //validate if there is a category with id
        Category category;
        Optional<Category> optionalCategory = categoryRepository.findById(postDTO.getCategoryId());
        if (optionalCategory.isPresent()){
            category = optionalCategory.get();
        }
        else {
            throw new BadRequestException("This category does not exist!");
        }
        post.setCategory(category);
        if (!postDTO.checkCoordinates(postDTO.getCoordinates())) {
                throw new WrongCoordinatesException();
        }
        post.setCoordinates(postDTO.getCoordinates());
        post.setDateTime(LocalDateTime.now());
        postDAO.addPost(post);
        return new PostDTO(post);
    }
    //============VIEW POST BY ID ==================//
    @SneakyThrows
    @GetMapping("/posts/{id}")
    public ViewPostDTO viewPost (@PathVariable("id") long id){
        Post post = postDAO.getPostById(id);
        return new ViewPostDTO(post, postPictureDao.getPicturesByPostId(post.getId()));
    }
    //============ ADD PICTURE TO POST ==================//
    @SneakyThrows
    @PostMapping("/posts/{id}/pictures")
    public PictureDTO addPicture(@RequestPart(value = "picture") MultipartFile multipartFile, @PathVariable("id") long id,
                             HttpSession session){
        User user = loginVerification.checkIfLoggedIn(session);
        // we check if there is a post with id
        Post post = postDAO.getPostById(id);
        if (postPictureDao.getNumberOfPictures(post.getId()) >= MAX_PICTURES){
            throw new PostPicturePerPostException();
        }
        if (!post.getUser().getUsername().equals(user.getUsername())){
            throw new BadRequestException("You cannot upload a picture on someone else's post.");
        }
        String path = "C://posts//png//";
        String pictureName = PostController.getNameForUpload(multipartFile.getOriginalFilename(), user, post);
        File picture = new File(path + pictureName);
        FileOutputStream fos = new FileOutputStream(picture);
        fos.write(multipartFile.getBytes());
        fos.close();
        String mimeType = new MimetypesFileTypeMap().getContentType(picture);
        if(!mimeType.substring(0, 5).equalsIgnoreCase("image")){
            picture.delete();
            throw new BadRequestException("File format not supported, please upload pictures only");
        }
        PostPicture postPicture  = postPictureDao.addPostPicture(post, pictureName);
        return new PictureDTO(postPicture);
    }
    //============ ADD VIDEO TO POST ==================//
    @SneakyThrows
    @PostMapping("/posts/{id}/videos")
    public ViewPostDTO addVideos(@RequestPart(value = "videos") MultipartFile multipartFile, @PathVariable("id") long id,
                                 HttpSession session){
        User user = loginVerification.checkIfLoggedIn(session);
        // we check if there is a post with id
        Post post = postDAO.getPostById(id);
        if (!post.getUser().getUsername().equals(user.getUsername())){
            throw new BadRequestException("You cannot modify posts that are not yours.");
        }
        String path = "C://posts//videos//";
        String videosName = PostController.getNameForUpload(multipartFile.getOriginalFilename(), user, post);
        if (post.getVideoUrl() != null || !post.getVideoUrl().isEmpty()){
            //if a user has a video on post, we delete the old one from path folder
            File file = new File(path + post.getVideoUrl());
            file.delete();
        }
        File videos = new File(path + videosName);
        FileOutputStream fos = new FileOutputStream(videos);
        fos.write(multipartFile.getBytes());
        fos.close();
        String mimeType = new MimetypesFileTypeMap().getContentType(videos);
        if(!mimeType.substring(0, 6).contains("video")){
            videos.delete();
            throw new BadRequestException("File format not supported, please upload videos only");
        }
        post.setVideoUrl(videosName);
        postDAO.addVideos(post, videosName);
        return new ViewPostDTO(post);
    }
    public static String getNameForUpload(String name, User user, Post post){
        //name should have one . in it
        String[] all = name.split("\\.", 2);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy-MM-dd-hh-mm-ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String parse = localDateTime.format(dateTimeFormatter);
        String nameWithoutId = all[0];
        String formatForPicture = all[1];
        String nameWithId = nameWithoutId + "_" + parse + "_" + user.getId() + "_" + post.getId() +  "." + formatForPicture;
        System.out.println(name);
        return nameWithId;
    }
    //============ TAG ANOTHER USER ==================//
    @SneakyThrows
    @GetMapping("/posts/{pId}/users/{uId}")
    public TagDTO tagSomeone(@PathVariable("pId") Long pId, @PathVariable("uId") Long uId, HttpSession session){
        User u = loginVerification.checkIfLoggedIn(session);
        //Check if user is trying to tag someone on his post
        Post post = postDAO.getPostById(pId);
        if (post.getUser().getId()!=u.getId()){
            throw new AuthorizationException("You cannot tag people on someone else's post");
        }
        User taggedUser;
        Optional<User> optionalUser = userRepository.findById(uId);
        if (optionalUser.isPresent()){
            taggedUser = optionalUser.get();
        }
        else {
            throw new BadRequestException("Sorry, this user does not exist.");
        }
        post.addTaggedUser(taggedUser);
        postRepository.save(post);
        return new TagDTO(uId, pId);
    }

    //Doesnt need to be logged in
    @SneakyThrows
    @GetMapping("/posts/byDate/{dateTime}")
    public ArrayList<ViewPostsAndLikesDTO> sortByDateAndLikes(@PathVariable("dateTime") String date){
        if(date == null){
            throw new BadRequestException("Please enter a valid date.");
        }
        //will throw exception if date is not valid
        LocalDate.parse(date);
        return postDAO.getPostsSortedByDateAndLikes(date);
    }
    @SneakyThrows
    @GetMapping("/postsByUname/{user_name}")
    public ArrayList<ViewPostDTO> getPostByUsername(@PathVariable("user_name") String username){
        //If no posts are found:
        ArrayList<ViewPostDTO> posts = postDAO.getPostsByUsername(username);
        if (posts == null){
            throw new NotFoundException("We didn't find any posts matching the given username.");
        }
        return posts;
    }
    @SneakyThrows
    @GetMapping("/postsByTaggedUser/{user_id}")
    public ArrayList<ViewPostDTO> getPostByTag(@PathVariable("user_id") int userId){
        return postDAO.getPostsByUserTagged(userId);
    }

    @SneakyThrows
    @RequestMapping("/posts/{id}")
    public RedirectView viewMap(@PathVariable("id") Long id) {
        RedirectView redirectView = new RedirectView();
        Post post = postDAO.getPostById(id);
        String link = post.getMapUrl();
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.connect();
        }
        catch (MalformedURLException e){
            throw new MalformedURLException("The link you are trying to access is invalid.");
        }
        redirectView.setUrl(link);
        return redirectView;
    }
}
