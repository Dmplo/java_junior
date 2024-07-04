package Infrastructure.entityService;

import models.Post;
import models.User;

import java.util.List;

public interface iUserService extends iService {

    void create(User user);

    void createFromList(List<User> users);

    List<User> getUsers();

    void update(User user);

    void delete(User user);

    User getById(long id);

    List<User> getPostAuthors(User user);

}