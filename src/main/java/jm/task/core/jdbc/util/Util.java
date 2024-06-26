package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Util {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/database";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static final String SHOW_SQL = "true";
    private static final String CURRENT_SESSION_CONTEXT_CLASS = "thread";

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.driver_class", DB_DRIVER); // driver mysql
        properties.setProperty("hibernate.connection.url", DB_URL);     //url DB
        properties.setProperty("hibernate.connection.username", DB_USER);   //user DB
        properties.setProperty("hibernate.connection.password", DB_PASS);   //user pass

        properties.setProperty("hibernate.dialect", HIBERNATE_DIALECT);     //SQL language for MySQL
        properties.setProperty("hibernate.show_sql", SHOW_SQL);             // show Hibernate SQL query
        properties.setProperty("hibernate.current_session_context_class", CURRENT_SESSION_CONTEXT_CLASS);
        return properties;
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration();
            configuration.setProperties(getProperties());
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
            return sessionFactory;
        } catch (HibernateException he) {
            System.out.println("не удалось запустить SessionFactory");
            he.printStackTrace();
        }
        return sessionFactory;
    }
}
