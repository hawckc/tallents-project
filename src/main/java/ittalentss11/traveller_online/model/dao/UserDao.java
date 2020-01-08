package ittalentss11.traveller_online.model.dao;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import ittalentss11.traveller_online.model.dto.UserNoSensitiveDTO;
import ittalentss11.traveller_online.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserDao {
    public static final String INSERT_USER = "INSERT INTO final_project.user (first_name, last_name, username, password, email) VALUES (?, ?, ?, ?, ?);";
    public static final String USER_BY_USERNAME = "SELECT * FROM final_project.user WHERE username = ?;";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    //=========== USER REGISTRATION ==========/
    //TODO: MAKE SURE TO USER CORRECT SQL QUERRIES
    //MAIL AVAILABILITY
    public boolean emailIsAvailable (String email){
        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM final_project.user WHERE email = ?;", new Object[]{email}, Integer.class);
        return result == 0;
    }
    //USERNAME AVAILABILITY
    public boolean usernameIsAvailable (String username){
        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM final_project.user WHERE username = ?;", new Object[]{username}, Integer.class);
        return result == 0;
    }
    //USER REGISTRATION
    public UserNoSensitiveDTO register (User user){
        //hash is made by taking password and salting
        //System.out.println(user.getPassword());
        String hash = argon2.hash(4, 1024 * 1024, 8, user.getPassword());
        jdbcTemplate.update(INSERT_USER,
                                user.getFirstName(),
                                user.getLastName(),
                                user.getUsername(),
                                hash,
                                user.getEmail());
        return new UserNoSensitiveDTO(user);
    }
    public boolean foundUsernameForLogin(String username){
        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM final_project.user WHERE username = ?;", new Object[]{username}, Integer.class);
        return result == 1;
    }
    /*public String getHashByUser(String username){
        String result = jdbcTemplate.queryForObject("SELECT password FROM final_project.user WHERE username = '"+username+"';", String.class);
        return result;
    }*/
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
