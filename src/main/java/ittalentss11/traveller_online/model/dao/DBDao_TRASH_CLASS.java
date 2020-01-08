package ittalentss11.traveller_online.model.dao;

import ittalentss11.traveller_online.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DBDao_TRASH_CLASS {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //testing to see if the JDBC Template works (getting all users with SQL querry)
    public List<User> getAllUsers (){
        List<User> users = jdbcTemplate.query("SELECT first_name, last_name, username, password, email FROM final_project.users;", (resultSet, i) -> toUser(resultSet));
        return users;
    }

    //used by getAllUsers, it is here just for simplicity
    private User toUser (ResultSet resultSet) throws SQLException {
        User u = new User();
        u.setFirstName(resultSet.getString("first_name"));
        u.setLastName(resultSet.getString("last_name"));
        u.setUsername(resultSet.getString("username"));
        u.setEmail(resultSet.getString("email"));
        return u;
    }
}
