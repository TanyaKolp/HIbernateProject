package ru.apache_maven;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.apache_maven.commands.ShowCompaniesCommand;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.GasStation;
import ru.apache_maven.models.Location;
import ru.apache_maven.models.User;

import java.util.*;
import java.util.spi.LocaleServiceProvider;

/**
 * Created by tania on 11/14/16.
 */
public class Main {
    static SessionFactory sessionFactory = HibUtil.getSessionFactory();
    static Session session = sessionFactory.openSession();

    public static void main(String[] args) {
        System.out.println("DB");
        init();

    }

    private static void init() {
        List<User> users = null;
        ArrayList<Company> companies = null;
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(Location.class);
            List<Location> locations = (List<Location>) criteria.add(Restrictions.eq("city", "Moscow")).list();
            for (Location location : locations) {
                System.out.println(location.getAddress());
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
}



