package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.PostPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;
import java.sql.*;
@Component
public class PostPictureDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String INSERT_PICTURE = "INSERT INTO final_project.post_pictures (posts_id, picture_url)" +
            " VALUES (?, ?);";

    public void addPostPicture(Post post, String name) throws SQLException {

        //String[] picturewithall = postPicture.getPictureUrl().split(".");
        //String picture = picturewithall[0];
        //picture += "_";
        //picture += postPicture.getPost().getUser().getId();
        PostPicture postPicture = new PostPicture();
        postPicture.setPost(post);
        try(Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_PICTURE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, (int) post.getId());
            ps.setString(2, name);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            postPicture.setId(keys.getLong(1));
        }
    }
    public int getAllPictures(int id){
        int result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM final_project.post_pictures " +
                        "WHERE posts_id = ?;", new Object[]{id}, Integer.class);
        return result;
    }
}
