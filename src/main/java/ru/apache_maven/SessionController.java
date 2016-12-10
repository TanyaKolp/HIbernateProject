package ru.apache_maven;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Table;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import ru.apache_maven.commands.Command;
import ru.apache_maven.models.User;

import javax.persistence.Query;
import javax.persistence.TableGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tania on 12/2/16.
 */
public class SessionController {
    SessionFactory sessionFactory = HibUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
    public SessionController(){
        session.beginTransaction();
    }
    public void openSession() {
        session = sessionFactory.openSession();
    }

    public void beginTransaction() {
        session.beginTransaction();
    }

    public void commitTransaction() {
        session.getTransaction().commit();
    }

    public void closeSession() {
        session.close();
    }

    public ArrayList<String> showTables() {
        SessionFactory sessionFactory = HibUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        ArrayList<String> allTables = (ArrayList<String>) session.createSQLQuery("SHOW TABLES").list();
        for (int i = 0; i < allTables.size(); i++) {
            System.out.println(i + ".  " + allTables.get(i));
        }
        session.getTransaction().commit();
        return allTables;
    }

    public ArrayList<String> showColumns(String tableName) {
        SessionFactory sessionFactory = HibUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        ArrayList<String> columns = (ArrayList<String>) session.createSQLQuery("select COLUMN_NAME" +
                " from information_schema.COLUMNS" +
                " where TABLE_NAME='" + tableName + "'").list();
        for (int i = 0; i < columns.size(); i++) {
            System.out.println(i + ".  " + columns.get(i));

        }

        session.getTransaction().commit();

        return columns;
    }

    public boolean checkUser(String userLogin, String userPassword) {
        boolean exist = false;
        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("login", userLogin)).uniqueResult();
        if (user != null) {
            if (user.getPassword().equalsIgnoreCase(userPassword)) {
                System.out.println("welcome!");
                exist = true;
            } else {
                System.out.println("Incorrect password!");
            }
        } else {
            System.out.println("Please, sing up.");
            closeSession();
            System.exit(0);
        }
        return exist;
    }

    private void old() {
        try {
            session.beginTransaction();


            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public void addRow() {

    }

    public boolean addUser(String userLogin, String userPassword) {
        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("login", userLogin)).uniqueResult();
        if (user == null) {
            User newUser = new User(userLogin, userPassword);
            session.save(newUser);
            commitTransaction();
            return true;
        } else {
            return false;
        }
    }
}

