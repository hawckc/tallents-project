package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class PostDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_POST =
            "INSERT INTO final_project.posts " +
            "(user_id, video_url, description , other_info, category_id, coordinates, map_url, location_name) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_BY_ID = "SELECT * FROM final_projects.posts WHERE id = ?";

    //User post a post
    //TODO: Verify if this works
    public void addPost(Post post) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try(PreparedStatement ps = connection.prepareStatement(INSERT_POST, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, (int) post.getUser().getId());
            ps.setString(2, post.getVideoUrl());
            ps.setString(3, post.getDescription());
            ps.setString(4, post.getOtherInfo());
            ps.setInt(5, (int) post.getCategory().getId());
            ps.setString(6, post.getCoordinates());
            ps.setString(7, post.getMapUrl());
            ps.setString(8, post.getLocationName());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            post.setId(keys.getLong(1));
        }
    }

    public Post getPostById(long id) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, id);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()){
                Post post = new Post();
                //make select for location
                //make select for category
            }
            return null;

        }
    }
}
