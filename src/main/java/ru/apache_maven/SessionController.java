package ru.apache_maven;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import ru.apache_maven.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by tania on 12/2/16.
 */
public class SessionController {

    SessionFactory sessionFactory = HibUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
    public static SessionController instance = new SessionController();
    User currentUser = null;

    private SessionController() {
        session.beginTransaction();
    }

    public static SessionController getInstance() {
        return instance;
    }

    public void commitTransaction() {
        session.getTransaction().commit();
    }
    public Criteria getCriteria(Class clazz){
        return session.createCriteria(clazz);
    }
    public void saveSession(Object o){
        session.save(o);
    }

    public void closeSession() {
        session.close();
        sessionFactory.close();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean checkUser(String userLogin, String userPassword) {
        boolean exist = false;
        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("login", userLogin)).uniqueResult();
        this.currentUser = user;
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

    public List show(Class clazz) {
        Criteria criteria = session.createCriteria(clazz);
        ArrayList list = (ArrayList) criteria.list();
        return list;
    }

    public boolean addUser(String userLogin, String userPassword) {
        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("login", userLogin)).uniqueResult();
        if (user == null) {
            User newUser = new User(userLogin, userPassword);
            session.save(newUser);
            this.currentUser = newUser;
            commitTransaction();
            return true;
        } else {
            return false;
        }
    }
}

