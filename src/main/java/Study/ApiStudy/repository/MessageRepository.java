package Study.ApiStudy.repository;

import Study.ApiStudy.entity.Message;
import Study.ApiStudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);
}
