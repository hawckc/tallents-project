package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.controller.controller_exceptions.BadRequestException;
import ittalentss11.traveller_online.model.dto.ViewPostDTO;
import ittalentss11.traveller_online.model.dto.ViewPostsAndLikesDTO;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.repository_ORM.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class PostDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    PostRepository postRepository;

    private static final String INSERT_POST =
            "INSERT INTO final_project.posts " +
                    "(user_id, video_url, description , other_info, category_id, coordinates, map_url, location_name, date_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_POST_FOR_VIDEOS = "UPDATE final_project.posts SET video_url = ? WHERE id = ?;";
    private static final String GET_POSTS_BY_DATE_AND_LIKES = "SELECT COUNT(l.post_id) AS likes, p.* FROM posts AS p " +
            "LEFT JOIN likes AS l ON p.id = l.post_id " +
            "GROUP BY p.id " +
            "HAVING DATE (p.date_time) = ? " +
            "ORDER BY likes DESC, " +
            "date_time DESC";
    private static final String GET_POSTS_BY_USERNAME = "SELECT p.* FROM final_project.posts AS p " +
            "JOIN users AS un ON p.user_id = un.id " +
            "WHERE un.username LIKE ?;";
    private static final String GET_POSTS_BY_TAG = "SELECT p.* FROM final_project.tags AS t" +
            " JOIN final_project.posts AS p ON t.post_id = p.id WHERE t.user_id = ? ORDER BY p.id;";

    //User post a post
    public void addPost(Post post) throws SQLException {
        try ( Connection connection = jdbcTemplate.getDataSource().getConnection();
              PreparedStatement ps = connection.prepareStatement(INSERT_POST, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, (int) post.getUser().getId());
            ps.setString(2, post.getVideoUrl());
            ps.setString(3, post.getDescription());
            ps.setString(4, post.getOtherInfo());
            ps.setInt(5, (int) post.getCategory().getId());
            ps.setString(6, post.getCoordinates());
            ps.setString(7, post.getMapUrl());
            ps.setString(8, post.getLocationName());
            ps.setTimestamp(9, Timestamp.valueOf(post.getDateTime()));
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            post.setId(keys.getLong(1));
        }
    }

    public void addVideos(Post post, String name) throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_POST_FOR_VIDEOS)) {
            ps.setString(1, name);
            ps.setInt(2, (int) post.getId());
            ps.executeUpdate();
        }
    }

    public Post getPostById(long id) throws BadRequestException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return optionalPost.get();
        }
        throw new BadRequestException("Sorry, that post doesn't exist");
    }

    public ArrayList<ViewPostsAndLikesDTO> getPostsSortedByDateAndLikes(String date) throws SQLException {
        //get all posts by given date and ordered by likes
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_POSTS_BY_DATE_AND_LIKES)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(date));
            ResultSet set = preparedStatement.executeQuery();
            ArrayList<ViewPostsAndLikesDTO> postsByDateAndLikes = new ArrayList<>();
            while (set.next()) {
                ViewPostsAndLikesDTO viewPostsAndLikesDTO =
                        new ViewPostsAndLikesDTO(getPostDtoFromResultSet(set), set.getInt("likes"));
                postsByDateAndLikes.add(viewPostsAndLikesDTO);
            }
            return postsByDateAndLikes;
        }
    }
    public ArrayList<ViewPostDTO> getPostsByUsername(String username) throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_POSTS_BY_USERNAME)) {
            ps.setString(1, "%" + username + "%");
            ResultSet set = ps.executeQuery();
            ArrayList<ViewPostDTO> arr = new ArrayList<>();
            while (set.next()) {
                ViewPostDTO postDTO = getPostDtoFromResultSet(set);
                arr.add(postDTO);
            }
            if (arr.isEmpty()){
                return null;
            }
            return arr;
        }
    }

    public ArrayList<ViewPostDTO> getPostsByUserTagged(int userId) throws SQLException {
        try(Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_POSTS_BY_TAG)){
            ps.setInt(1, userId);
            ResultSet set = ps.executeQuery();
            ArrayList<ViewPostDTO> arr = new ArrayList<>();
            while(set.next()){
                ViewPostDTO postDTO = getPostDtoFromResultSet(set);
                arr.add(postDTO);
            }
            return arr;
        }
    }

    protected static ViewPostDTO getPostDtoFromResultSet (ResultSet set) throws SQLException {
        ViewPostDTO postDTO = new ViewPostDTO();
        postDTO.setId(set.getLong("id"));
        postDTO.setDescription(set.getString("description"));
        postDTO.setMapUrl(set.getString("map_url"));
        postDTO.setCoordinates(set.getString("coordinates"));
        postDTO.setLocationName(set.getString("location_name"));
        postDTO.setUserId(set.getInt("user_id"));
        postDTO.setCategoryId(set.getInt("category_id"));
        postDTO.setVideoUrl(set.getString("video_url"));
        postDTO.setOtherInfo(set.getString("other_info"));
        postDTO.setDateTime(set.getTimestamp("date_time").toLocalDateTime());
        return postDTO;
    }
}
