package Infrastructure.entityService;

import models.Post;
import models.PostComment;
import models.User;

import java.util.List;

public interface iPostCommentService extends iService {

    void create(PostComment postComment);

    void createFromList(List<PostComment> postComments);

    List<PostComment> getPostComments();

    void update(PostComment postComment);

    void delete(PostComment postComment);

    PostComment getById(long id);

    List<PostComment> getByUser(User user);
}