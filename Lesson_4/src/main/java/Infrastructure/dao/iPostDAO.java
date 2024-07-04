package Infrastructure.dao;


import models.Post;
import models.PostComment;
import models.User;

import java.util.List;

public interface iPostDAO extends iDao {

    void create(Post post);

    List<Post> getAll();

    Post getById(long id);

    void update(Post post);

    void delete(Post post);

    List<Post> getByUser(User user);

}
