package ru.apache_maven.commands;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.apache_maven.HibUtil;

import java.util.ArrayList;

/**
 * Created by tania on 12/3/16.
 */
public class ShowAndSetTableCommand implements Command {
    public void execute() {
        SessionFactory sessionFactory = HibUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();


            ArrayList<String> allTables = (ArrayList<String>) session.createSQLQuery("SHOW TABLES").list();
            for (int i = 0; i < allTables.size(); i++) {
                System.out.println(i + ".  " + allTables.get(i));
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public void execute(Session session) {
        ArrayList<String> allTables = (ArrayList<String>) session.createSQLQuery("SHOW TABLES").list();
        for (int i = 0; i < allTables.size(); i++) {
            System.out.println(i + ".  " + allTables.get(i));
        }
    }

    public String getName() {
        return null;
    }

    @Override
    public Message createMessage() {
        return null;
    }

    @Override
    public Message getMassage() {
        return null;
    }
}
