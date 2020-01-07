package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;
import java.sql.*;
@Component
public class CategoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static final String GET_BY_NAME = "SELECT * FROM final_project.locations WHERE name = ?;";
    public Category getByName(String name) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, "name");
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()){
                Category c = new Category(set.getLong("id"),
                        set.getString("name"));
                return c;
            }
            return null;

        }
    }
}