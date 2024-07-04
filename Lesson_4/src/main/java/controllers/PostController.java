package controllers;

import Infrastructure.entityService.ServiceFactory;
import Infrastructure.entityService.iPostService;
import models.Post;
import models.User;

import java.util.List;

public class PostController {
    private final iPostService postService = ServiceFactory.getService(iPostService.class);

    public Post create(String name, User user) {
        Post post = new Post(name, user);
        assert postService != null;
        this.postService.create(post);
        return post;
    }

    public void createFromList(List<Post> posts) {
        assert postService != null;
        this.postService.createFromList(posts);
    }

    public Post update(Post post) {
        assert postService != null;
        postService.update(post);
        return post;
    }
    public void delete(Post post) {
        assert postService != null;
        postService.delete(post);
    }

    public List<Post> getPosts() {
        assert postService != null;
       return postService.getPosts();
    }

    public Post getById(long id) {
        assert postService != null;
       return postService.getById(id);
    }

    public List<Post> getByUser(User user) {
        assert postService != null;
        return postService.getByUser(user);
    }
}
