package Infrastructure.dao;


import models.PostComment;
import models.User;

import java.util.List;

public interface iPostCommentDAO extends iDao {

    void create(PostComment postComment);

    List<PostComment> getAll();

    PostComment getById(long id);

    void update(PostComment postComment);

    void delete(PostComment postComment);

    List<PostComment> getByUser(User user);
}
