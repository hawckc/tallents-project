package ittalentss11.traveller_online.model.dao;

import ittalentss11.traveller_online.model.dto.UserRegDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //=========== USER REGISTRATION ==========/
    //MAIL AVAILABILITY
    public boolean emailIsAvailable (String email){
        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM final_project.users WHERE email = '"+email+"';", Integer.class);
        return result == 0;
    }
    //USERNAME AVAILABILITY
    public boolean usernameIsAvailable (String username){
        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM final_project.users WHERE username = '"+username+"';", Integer.class);
        return result == 0;
    }
    //USER REGISTRATION
    public void register (UserRegDTO user){
        jdbcTemplate.update("INSERT INTO final_project.users (`first_name`, `last_name`, `username`, `password`, `email`) VALUES (?, ?, ?, ?, ?);",
                                user.getFirstName(),
                                user.getLastName(),
                                user.getUsername(),
                                user.getPassword(),
                                user.getEmail());
    }
}
