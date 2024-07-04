import controllers.PostCommentController;
import controllers.PostController;
import controllers.UserController;
import models.Post;
import models.PostComment;
import models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Homework {

    PostController postController = new PostController();
    PostCommentController postCommentController = new PostCommentController();
    UserController userController = new UserController();

    public void start() {
        fillTables();

        User user = userController.getById(1L);

        System.out.println("Task_3.1");
        System.out.println(postController.getById(1L).getPostComments());
        System.out.println("Task_3.2");
        System.out.println(postController.getByUser(user));
        System.out.println("Task_3.3");
        System.out.println(postCommentController.getByUser(user));
        System.out.println("Task_3.4");
        System.out.println(userController.getPostAuthors(user));
    }

    public void fillTables() {

        List<String> names = List.of("Buddy", "Tucker", "Jack", "Leo", "Duke", "Winston", "Bear", "Teddy", "Loki", "Archie");


        List<User> users = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            User user = new User();
            user.setName(getRandom(names));
            users.add(user);
        }
        userController.createFromList(users);

        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Post post = new Post();
            post.setTitle("Post #" + i);
            post.setUser(getRandom(users));
            posts.add(post);
        }
        postController.createFromList(posts);


        List<PostComment> postComments = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            PostComment postComment = new PostComment();
            postComment.setText("Comment #" + i);
            postComment.setUser(getRandom(users));
            postComment.setPost(getRandom(posts));
            postComments.add(postComment);
        }
        postCommentController.createFromList(postComments);

    }

    private <T> T getRandom(List<? extends T> items) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(randomIndex);
    }

}
