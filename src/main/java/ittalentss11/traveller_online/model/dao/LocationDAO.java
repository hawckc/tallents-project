package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.model.dto.LocationDTO;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.Location;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.*;
@Component
public class LocationDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static final String GET_BY_NAME = "SELECT * FROM final_project.locations WHERE name = ?;";
    public static final String INSERT_LOCATION = "INSERT INTO final_project.locations (name, coordinates, mapUrl ) VALUES (?, ?, ?);";
    public Location getByName(String name) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, "name");
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()){
                Location c = new Location(set.getLong("id"),
                        set.getString("name"),
                        set.getString("coordinates"),
                        set.getString("mapUrl"));
                return c;
            }
            return null;

        }
    }
    public Location insertLocation (Location location) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try(PreparedStatement ps = connection.prepareStatement(INSERT_LOCATION, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, location.getName());
            ps.setString(2, location.getCoordinates());
            ps.setString(3, location.getMapUrl());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            location.setId(keys.getLong(1));
        }
        return location;
    }
}
