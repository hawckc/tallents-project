package ittalentss11.traveller_online.model.repository_ORM;
import ittalentss11.traveller_online.model.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
