package Infrastructure.entityService;

import Infrastructure.config.DBService;
import Infrastructure.dao.DaoFactory;
import Infrastructure.dao.iPostCommentDAO;
import Infrastructure.dao.iPostDAO;
import jakarta.persistence.NoResultException;
import models.Post;
import models.PostComment;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.util.List;

public class PostServiceImpl implements iPostService {

    @Override
    public void create(Post post) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostDAO dao = DaoFactory.getDao(iPostDAO.class);
            assert dao != null;
            dao.create(post);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createFromList(List<Post> posts) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostDAO dao = DaoFactory.getDao(iPostDAO.class);
            assert dao != null;
            for (Post post : posts) {
                dao.create(post);
            }
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getByUser(User user) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostDAO dao = DaoFactory.getDao(iPostDAO.class);
            assert dao != null;
            List<Post> posts = dao.getByUser(user);
            transaction.commit();
            return posts;
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getPosts() {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostDAO dao = DaoFactory.getDao(iPostDAO.class);
            assert dao != null;
            return dao.getAll();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getById(long id) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostDAO dao = DaoFactory.getDao(iPostDAO.class);
            assert dao != null;
            Post post = dao.getById(id);
            transaction.commit();
            return post;
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Post post) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostDAO dao = DaoFactory.getDao(iPostDAO.class);
            assert dao != null;
            dao.update(post);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Post post) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostDAO dao = DaoFactory.getDao(iPostDAO.class);
            assert dao != null;
            dao.delete(post);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

}