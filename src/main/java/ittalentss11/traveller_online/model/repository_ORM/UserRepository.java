package ittalentss11.traveller_online.model.repository_ORM;

import ittalentss11.traveller_online.model.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TODO Finish up Repository folder and make corrections to old SQL queries
@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

}
