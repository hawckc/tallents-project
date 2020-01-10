package ittalentss11.traveller_online.model.dao;
import ittalentss11.traveller_online.controller.controller_exceptions.BadRequestException;
import ittalentss11.traveller_online.model.pojo.Category;
import ittalentss11.traveller_online.model.pojo.User;
import ittalentss11.traveller_online.model.repository_ORM.CategoryRepository;
import ittalentss11.traveller_online.model.repository_ORM.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.Optional;

@Component
public class CategoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CategoryRepository categoryRepository;

    public static final String GET_BY_NAME = "SELECT * FROM final_project.categories WHERE name LIKE ?;";

    //what is that for?
    public Category getByName(String name) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()){
                Category c = new Category(set.getLong("id"),
                        set.getString("name"));
                return c;
            }
            return null;

        }
    }
    public Category getCategoryById(long categoryId) throws BadRequestException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()){
            return optionalCategory.get();
        }
        throw new BadRequestException("This category does not exist!");
    }
}
