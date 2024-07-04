package Infrastructure.entityService;

import Infrastructure.config.DBService;
import Infrastructure.dao.DaoFactory;
import Infrastructure.dao.iPostDAO;
import Infrastructure.dao.iUserDAO;
import jakarta.persistence.NoResultException;
import models.Post;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.util.List;

public class UserServiceImpl implements iUserService {

    @Override
    public void create(User user) {
        Transaction transaction = DBService.getTransaction();
        try {
            iUserDAO dao = DaoFactory.getDao(iUserDAO.class);
            assert dao != null;
            dao.create(user);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createFromList(List<User> users) {
        Transaction transaction = DBService.getTransaction();
        try {
            iUserDAO dao = DaoFactory.getDao(iUserDAO.class);
            assert dao != null;
            for (User user : users) {
                dao.create(user);
            }
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getPostAuthors(User user) {
        Transaction transaction = DBService.getTransaction();
        try {
            iUserDAO dao = DaoFactory.getDao(iUserDAO.class);
            assert dao != null;
            return dao.getPostAuthors(user);
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUsers() {
        Transaction transaction = DBService.getTransaction();
        try {
            iUserDAO dao = DaoFactory.getDao(iUserDAO.class);
            assert dao != null;
            return dao.getAll();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(long id) {
        Transaction transaction = DBService.getTransaction();
        try {
            iUserDAO dao = DaoFactory.getDao(iUserDAO.class);
            assert dao != null;
            User user = dao.getById(id);
            transaction.commit();
            return user;
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = DBService.getTransaction();
        try {
            iUserDAO dao = DaoFactory.getDao(iUserDAO.class);
            assert dao != null;
            dao.update(user);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = DBService.getTransaction();
        try {
            iUserDAO dao = DaoFactory.getDao(iUserDAO.class);
            assert dao != null;
            dao.delete(user);
            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException(e);
        }
    }

}