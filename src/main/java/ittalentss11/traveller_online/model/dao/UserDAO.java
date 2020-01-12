package ittalentss11.traveller_online.model.dao;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import ittalentss11.traveller_online.controller.controller_exceptions.BadRequestException;
import ittalentss11.traveller_online.model.dto.PostDTO;
import ittalentss11.traveller_online.model.dto.ViewPostDTO;
import ittalentss11.traveller_online.model.pojo.User;
import ittalentss11.traveller_online.model.repository_ORM.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Component
public class UserDAO {
    public static final String INSERT_USER = "INSERT INTO final_project.users (first_name, last_name, username, password, email) VALUES (:first_name, :last_name, :username, :password, :email);";
    public static final String USER_BY_USERNAME = "SELECT * FROM final_project.users WHERE username = ?;";
    public static final String USER_NEWS_FEED = "SELECT followed.username, p.* FROM users AS u " +
            "JOIN users_follow_users AS f ON u.id = f.follower_id " +
            "JOIN posts AS p ON p.user_id = f.followed_id " +
            "JOIN users AS followed ON followed.id = f.followed_id WHERE f.follower_id = ? " +
            "UNION " +
            "SELECT followed.username, p.* FROM users AS u " +
            "JOIN users_follow_users AS f ON u.id = f.follower_id " +
            "JOIN tags AS t ON t.user_id = f.followed_id " +
            "JOIN posts AS p ON p.id = t.post_id " +
            "JOIN users AS followed ON followed.id = f.followed_id " +
            "WHERE f.follower_id = ? " +
            "GROUP BY username " +
            "ORDER BY username;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    //=========== USER REGISTRATION ==========/
    //MAIL AVAILABILITY
    public boolean emailIsAvailable (String email){
        int result = userRepository.countUsersByEmailEquals(email);
        return result == 0;
    }
    //USERNAME AVAILABILITY
    public boolean usernameIsAvailable (String username){
        int result = userRepository.countUsersByUsernameEquals(username);
        return result == 0;
    }
    //USER REGISTRATION
    public void register (final User user){
        //hash is made by taking password and salting
        final String hash = argon2.hash(4, 1024 * 1024, 8, user.getPassword());
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("username", user.getUsername())
                .addValue("password", hash)
                .addValue("email", user.getEmail());
        namedParameterJdbcTemplate.update(INSERT_USER, parameters, holder);
        user.setId(holder.getKey().longValue());
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
    public User getUserById(Long uId) throws BadRequestException {
        Optional<User> optionalUser = userRepository.findById(uId);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new BadRequestException("Sorry, this user does not exist.");
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public HashMap<String, ArrayList<ViewPostDTO>> getNewsFeed(User user) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_NEWS_FEED, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, (int) user.getId());
            preparedStatement.setInt(2, (int) user.getId());
            ResultSet set = preparedStatement.executeQuery();
            HashMap<String, ArrayList<ViewPostDTO>> usersWithPosts = new HashMap<>();
            while (set.next()){
                String username = set.getString("username");
                if (usersWithPosts.containsKey(username) == false){
                    usersWithPosts.put(username, new ArrayList<>());
                }
                ViewPostDTO postDTO = new ViewPostDTO();
                //join category to get category name
                postDTO.setUserId(set.getInt("user_id"));
                postDTO.setCategoryId(set.getInt("category_id"));
                postDTO.setDescription(set.getString("description"));
                postDTO.setMapUrl(set.getString("map_url"));
                postDTO.setOtherInfo(set.getString("other_info"));
                postDTO.setCoordinates(set.getString("coordinates"));
                postDTO.setVideoUrl(set.getString("video_url"));
                postDTO.setLocationName(set.getString("location_name"));
                ArrayList<ViewPostDTO> arr = usersWithPosts.get(username);
                arr.add(postDTO);
                usersWithPosts.put(username, arr);
            }
            return usersWithPosts;

        }
    }
}
