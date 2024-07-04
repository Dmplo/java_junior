package controllers;

import Infrastructure.entityService.ServiceFactory;
import Infrastructure.entityService.iUserService;
import models.User;

import java.util.List;

public class UserController {
    private final iUserService userService = ServiceFactory.getService(iUserService.class);

    public User create(String name) {
        User user = new User(name);
        assert userService != null;
        this.userService.create(user);
        return user;
    }

    public void createFromList(List<User> users) {
        assert userService != null;
        this.userService.createFromList(users);
    }

    public User update(User user) {
        assert userService != null;
        userService.update(user);
        return user;
    }

    public void delete(User user) {
        assert userService != null;
        userService.delete(user);
    }

    public List<User> getUsers() {
        assert userService != null;
        return userService.getUsers();
    }

    public User getById(long id) {
        assert userService != null;
        return userService.getById(id);
    }

    public List<User> getPostAuthors(User user) {
        assert userService != null;
        return userService.getPostAuthors(user);
    }
}

