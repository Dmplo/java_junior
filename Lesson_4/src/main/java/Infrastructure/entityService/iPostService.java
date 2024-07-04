package Infrastructure.entityService;

import models.Post;
import models.PostComment;
import models.User;

import java.util.List;

public interface iPostService extends iService {

    void create(Post post);

    void createFromList(List<Post> posts);

    List<Post> getPosts();

    void update(Post post);

    void delete(Post post);

    Post getById(long id);

    List<Post> getByUser(User user);

}