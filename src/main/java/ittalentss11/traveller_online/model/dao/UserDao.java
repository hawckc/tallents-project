package ittalentss11.traveller_online.model.dao;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import ittalentss11.traveller_online.model.pojo.User;
import ittalentss11.traveller_online.model.repository_ORM.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserDao {

    public static final String INSERT_USER = "INSERT INTO final_project.users (first_name, last_name, username, password, email) VALUES (?, ?, ?, ?, ?);";
    public static final String USER_BY_USERNAME = "SELECT * FROM final_project.users WHERE username = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    //=========== USER REGISTRATION ==========/
    //MAIL AVAILABILITY
    public boolean emailIsAvailable (String email){
        //int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM final_project.users WHERE email = ?;", new Object[]{email}, Integer.class);
        int result = userRepository.countUsersByEmailEquals(email);
        return result == 0;
    }
    //USERNAME AVAILABILITY
    public boolean usernameIsAvailable (String username){
        //int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM final_project.users WHERE username = ?;", new Object[]{username}, Integer.class);
        int result = userRepository.countUsersByUsernameEquals(username);
        return result == 0;
    }
    //USER REGISTRATION
    public void register (final User user){
        //hash is made by taking password and salting
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String hash = argon2.hash(4, 1024 * 1024, 8, user.getPassword());
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, hash);
            ps.setString(5, user.getEmail());
            return ps;
        }, keyHolder);
        user.setId((long)keyHolder.getKey());
    }

    public boolean foundUsernameForLogin(String username){
        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM final_project.users WHERE username = ?;", new Object[]{username}, Integer.class);
        return result == 1;
    }
    public User getUserByUsername(String username) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_BY_USERNAME, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()){
                User u = new User(set.getLong("id"),
                        set.getString("first_name"),
                        set.getString("last_name"),
                        set.getString("username"),
                        set.getString("email"),
                        set.getString("password"));
                return u;
            }
            return null;

        }
    }
}
