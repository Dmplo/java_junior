package Infrastructure.dao;


import models.User;

import java.util.List;

public interface iUserDAO extends iDao {
    void create(User user);

    List<User> getAll();

    User getById(long id);

    void update(User user);

    void delete(User user);
    List<User> getPostAuthors(User user);
}
