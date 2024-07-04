package Infrastructure.dao;

import Infrastructure.config.DBService;
import models.PostComment;
import models.User;
import org.hibernate.Session;

import java.util.List;

public class PostCommentDAOImpl implements iPostCommentDAO {

    public PostCommentDAOImpl() {
    }

    public void create(PostComment postComment) {
        Session session = DBService.getSessionFactory().getCurrentSession();
        session.persist(postComment);
        session.flush();
    }

    @Override
    public List<PostComment> getAll() {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM PostComment", PostComment.class).list();
    }

    @Override
    public PostComment getById(long id) {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM PostComment WHERE id = :id", PostComment.class).setParameter("id", id).uniqueResult();
    }

    @Override
    public List<PostComment> getByUser(User user) {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM PostComment pc WHERE pc.user = :user", PostComment.class).setParameter("user", user).list();
    }

    @Override
    public void update(PostComment postComment) {
        DBService.getSessionFactory().getCurrentSession()
                .merge(postComment);
    }

    @Override
    public void delete(PostComment postComment) {
        DBService.getSessionFactory().getCurrentSession().remove(postComment);
    }

}
