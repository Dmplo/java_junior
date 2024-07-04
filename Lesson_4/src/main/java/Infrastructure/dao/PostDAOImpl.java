package Infrastructure.dao;

import Infrastructure.config.DBService;
import models.Post;
import models.PostComment;
import models.User;
import org.hibernate.Session;

import java.util.List;

public class PostDAOImpl implements iPostDAO {

    public PostDAOImpl() {
    }

    @Override
    public void create(Post post) {
        Session session = DBService.getSessionFactory().getCurrentSession();
        session.persist(post);
        session.flush();
    }

    @Override
    public List<Post> getByUser(User user) {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM Post p WHERE p.user = :user", Post.class).setParameter("user", user).list();
    }

    @Override
    public List<Post> getAll() {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM Post", Post.class).list();
    }

    @Override
    public Post getById(long id) {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM Post WHERE id = :id", Post.class).setParameter("id", id).uniqueResult();
    }

    @Override
    public void update(Post post) {
        DBService.getSessionFactory().getCurrentSession()
                .merge(post);
    }

    @Override
    public void delete(Post post) {
        DBService.getSessionFactory().getCurrentSession().remove(post);
    }

}
