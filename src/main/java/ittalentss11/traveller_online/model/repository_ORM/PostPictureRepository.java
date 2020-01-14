package ittalentss11.traveller_online.model.repository_ORM;

import ittalentss11.traveller_online.model.pojo.Post;
import ittalentss11.traveller_online.model.pojo.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;


@Repository
public interface PostPictureRepository extends JpaRepository<PostPicture, Long> {
}
