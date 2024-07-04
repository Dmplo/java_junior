package Infrastructure.config;

import lombok.Getter;
import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DBService {

    private static final String path = "/database.properties";

    @Getter
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = getConfiguration();
            sessionFactory = createSessionFactory(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Transaction getTransaction() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = getSessionFactory().getCurrentSession().getTransaction();
        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }
        return transaction;
    }

    public static void transactionRollback(Transaction transaction) {
        if (transaction.getStatus() == TransactionStatus.ACTIVE
            || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
            transaction.rollback();
        }
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        addAnnotatedClassToConfiguration(configuration);

        try (InputStream is = DBService.class.getResourceAsStream(path)) {
            Properties props = new Properties();
            props.load(is);

            configuration.setProperty("hibernate.connection.driver_class", props.getProperty("hibernate.connection.driver_class"));
            configuration.setProperty("hibernate.connection.url", props.getProperty("hibernate.connection.url"));
            configuration.setProperty("hibernate.connection.username", props.getProperty("hibernate.connection.username"));
            configuration.setProperty("hibernate.connection.password", props.getProperty("hibernate.connection.password"));
//            configuration.setProperty("hibernate.show_sql", props.getProperty("hibernate.show_sql"));
            configuration.setProperty("hibernate.hbm2ddl.auto", props.getProperty("hibernate.hbm2ddl.auto"));
            configuration.setProperty("hibernate.current_session_context_class", "thread");
            configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
//            configuration.setProperty("hibernate.format_sql", "true");

        } catch (IOException e) {
            throw new RuntimeException("Invalid config file " + path);
        }
        return configuration;
    }

    private static void addAnnotatedClassToConfiguration(Configuration configuration) {
        configuration
                .addAnnotatedClass(Post.class)
                .addAnnotatedClass(PostComment.class)
                .addAnnotatedClass(User.class);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void close() {
        sessionFactory.close();
    }

}