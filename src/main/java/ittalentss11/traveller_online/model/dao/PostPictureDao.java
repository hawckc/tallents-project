package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.model.dto.PictureDTO;
import ittalentss11.traveller_online.model.dto.ViewPostDTO;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.PostPicture;
import ittalentss11.traveller_online.model.repository_ORM.PostPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostPictureDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_PICTURE = "INSERT INTO final_project.post_pictures (posts_id, picture_url)" +
            " VALUES (?, ?);";
    private static final String GET_PICTURES = "SELECT id, picture_url FROM final_project.post_pictures " +
            "WHERE posts_id = ?;";

    public PostPicture addPostPicture(Post post, String name) throws SQLException {
        PostPicture postPicture = new PostPicture();
        postPicture.setPost(post);
        postPicture.setPictureUrl(name);
        try(Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_PICTURE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, post.getId());
            ps.setString(2, name);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            postPicture.setId(keys.getLong(1));
        }
        return postPicture;
    }
    public long getNumberOfPictures(long id){
        long result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM final_project.post_pictures " +
                        "WHERE posts_id = ?;", new Object[]{id}, Long.class);
        return result;
    }

    public List<PictureDTO> getPicturesByPostId(long postId) throws SQLException {
        List<PictureDTO> picturesByPostId = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement ps = connection.prepareStatement(GET_PICTURES)){
            ps.setLong(1, postId);
            ResultSet set = ps.executeQuery();
            while (set.next()){
                PictureDTO pictureDTO = new PictureDTO();
                pictureDTO.setId(set.getInt("id"));
                pictureDTO.setPictureUrl(set.getString("picture_url"));
                picturesByPostId.add(pictureDTO);
            }
        }
        return picturesByPostId;
    }
}
