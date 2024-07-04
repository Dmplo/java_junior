package Infrastructure.dao;

import Infrastructure.config.DBService;
import models.User;
import org.hibernate.Session;

import java.util.List;

public class UserDAOImpl implements iUserDAO {

    public UserDAOImpl() {
    }
    
    @Override
    public void create(User user) {
        Session session = DBService.getSessionFactory().getCurrentSession();
        session.persist(user);
        session.flush();
    }

    @Override
    public List<User> getAll() {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM User", User.class).list();
    }

    @Override
    public List<User> getPostAuthors(User user) {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("select p.user from Post p inner join p.postComments pc where pc.user = :user", User.class).setParameter("user", user).list();
    }

    @Override
    public User getById(long id) {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM User WHERE id = :id", User.class).setParameter("id", id).uniqueResult();
    }

    @Override
    public void update(User user) {
        DBService.getSessionFactory().getCurrentSession()
                .merge(user);
    }

    @Override
    public void delete(User user) {
        DBService.getSessionFactory().getCurrentSession().remove(user);
    }

}
