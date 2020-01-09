package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.model.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class PostDAO {
    @Autowired
    private LocationDAO locationDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String INSERT_POST = "INSERT INTO final_project.posts (user_id, description, location_id , category_id) VALUES (?, ?, ?, ?);";

    //User post a post
    public Post post(Post post) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try(PreparedStatement ps = connection.prepareStatement(INSERT_POST, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, post.getUser().getId());
            ps.setString(2, post.getDescription());
            ps.setLong(3, post.getLocation().getId());
            ps.setLong(4, post.getCategory().getId());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            post.setId(keys.getLong(1));
        }
        return post;
    }
}
