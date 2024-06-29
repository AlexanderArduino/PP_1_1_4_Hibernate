package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    // get sessionFactory connect to DB
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS `user` (\n" +
                "                `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "                `name` VARCHAR(50) NOT NULL,\n" +
                "                `lastName` VARCHAR(50) NOT NULL,\n" +
                "                `age` INT NOT NULL,\n" +
                "                PRIMARY KEY (`id`));";

        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.createSQLQuery(query).executeUpdate();
                transaction.commit();
                System.out.println("Новая таблица user была создана");
            } catch (TransactionException transactionException) {
                if (transaction.getStatus() == TransactionStatus.ACTIVE
                        || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                    transaction.rollback();
                    System.out.println("Создание новой таблицы user не удалось");
                }
                transactionException.printStackTrace();
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            System.out.println("Создание новой таблицы user. Организация сессии завершилась неудачей");
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS `user`";
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.createSQLQuery(query).executeUpdate();
                transaction.commit();
                System.out.println("Таблица user была удалена");
            } catch (TransactionException transactionException) {
                if (transaction.getStatus() == TransactionStatus.ACTIVE
                        || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                    transaction.rollback();
                    System.out.println("Удаление таблицы user не удалось");
                }
                transactionException.printStackTrace();
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            System.out.println("Удаление таблицы user. Организация сессии завершилась неудачей");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.save(new User(name, lastName, age));
                transaction.commit();
                System.out.println("Новый User был добавлен в таблицу");
            } catch (TransactionException transactionException) {
                if (transaction.getStatus() == TransactionStatus.ACTIVE
                        || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                    transaction.rollback();
                    System.out.println("Добавление нового User не удалось");
                }
                transactionException.printStackTrace();
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            System.out.println("Добавление нового user. Организация сессии завершилась неудачей");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                User user = session.get(User.class, id);
                session.delete(user);
                transaction.commit();
                System.out.println("Новый User был удален в таблицу");
            } catch (TransactionException transactionException) {
                if (transaction.getStatus() == TransactionStatus.ACTIVE
                        || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                    transaction.rollback();
                    System.out.println("Удаление нового User не удалось");
                }
                transactionException.printStackTrace();
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            System.out.println("Удаление нового user. Организация сессии завершилась неудачей");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                allUsers = session.createQuery("FROM User").getResultList();
                transaction.commit();
                System.out.println("Новый User был удален в таблицу");
            } catch (TransactionException transactionException) {
                if (transaction.getStatus() == TransactionStatus.ACTIVE
                        || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                    transaction.rollback();
                    System.out.println("Удаление нового User не удалось");
                }
                transactionException.printStackTrace();
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            System.out.println("Удаление нового user. Организация сессии завершилась неудачей");
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.createQuery("delete from User").executeUpdate();
                transaction.commit();
                System.out.println("Таблица User была очищена");
            } catch (TransactionException transactionException) {
                if (transaction.getStatus() == TransactionStatus.ACTIVE
                        || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                    transaction.rollback();
                    System.out.println("Очистка таблицы User не удалась");
                }
                transactionException.printStackTrace();
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            System.out.println("Очистка таблицы user. Организация сессии завершилась неудачей");
        }
    }
}
