package controllers;

import Infrastructure.entityService.ServiceFactory;
import Infrastructure.entityService.iPostCommentService;
import models.Post;
import models.PostComment;
import models.User;

import java.util.List;

public class PostCommentController {
    private final iPostCommentService postCommentService = ServiceFactory.getService(iPostCommentService.class);

    public PostComment create(String text, User user, Post post) {
        PostComment postComment = new PostComment(text, user, post);
        assert postCommentService != null;
        postCommentService.create(postComment);
        return postComment;
    }

    public void createFromList(List<PostComment> postComments) {
        assert postCommentService != null;
        this.postCommentService.createFromList(postComments);
    }

    public PostComment update(PostComment postComment) {
        assert postCommentService != null;
        postCommentService.update(postComment);
        return postComment;
    }

    public void delete(PostComment postComment) {
        assert postCommentService != null;
        postCommentService.delete(postComment);
    }

    public List<PostComment> getPostComments() {
        assert postCommentService != null;
        return postCommentService.getPostComments();
    }

    public PostComment getById(long id) {
        assert postCommentService != null;
        return postCommentService.getById(id);
    }

    public List<PostComment> getByUser(User user) {
        assert postCommentService != null;
        return postCommentService.getByUser(user);
    }

}
