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

public class PostCommentServiceImpl implements iPostCommentService {
    
    @Override
    public void create(PostComment postComment) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostCommentDAO dao = DaoFactory.getDao(iPostCommentDAO.class);
            assert dao != null;
            dao.create(postComment);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createFromList(List<PostComment> postComments) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostCommentDAO dao = DaoFactory.getDao(iPostCommentDAO.class);
            assert dao != null;
            for (PostComment postComment : postComments) {
                dao.create(postComment);
            }
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostComment> getPostComments() {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostCommentDAO dao = DaoFactory.getDao(iPostCommentDAO.class);
            assert dao != null;
            return dao.getAll();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public PostComment getById(long id) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostCommentDAO dao = DaoFactory.getDao(iPostCommentDAO.class);
            assert dao != null;
            PostComment postComment = dao.getById(id);
            transaction.commit();
            return postComment;
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<PostComment> getByUser(User user) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostCommentDAO dao = DaoFactory.getDao(iPostCommentDAO.class);
            assert dao != null;
            List<PostComment> postComments = dao.getByUser(user);
            transaction.commit();
            return postComments;
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PostComment postComment) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostCommentDAO dao = DaoFactory.getDao(iPostCommentDAO.class);
            assert dao != null;
            dao.update(postComment);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(PostComment postComment) {
        Transaction transaction = DBService.getTransaction();
        try {
            iPostCommentDAO dao = DaoFactory.getDao(iPostCommentDAO.class);
            assert dao != null;
            dao.delete(postComment);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

}