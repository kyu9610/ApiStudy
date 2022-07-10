package Study.ApiStudy.repository;

import Study.ApiStudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByName(String name);
}
