package ru.apache_maven;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by tania on 11/14/16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("DB");
        init();

    }

    private static void init() {
        List<User> users = null;
        List<Company> companies = null;
        Object result = null;
        SessionFactory sessionFactory = HibUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
//            User u2 = (User) session.load(User.class, 2);
//            User u3 = (User) session.load(User.class, 3);
//            User u4 = (User) session.load(User.class, 4);
//            User u5 = (User) session.load(User.class, 5);
//            User u6 = (User) session.load(User.class, 6);
//            User u7 = (User) session.load(User.class, 7);
//            User u8 = (User) session.load(User.class, 8);
//            User u9 = (User) session.load(User.class, 9);
//            User u10 = (User) session.load(User.class, 10);
//            u2.setFirstName("John");
//            u3.setFirstName("Joe");
//            u4.setFirstName("Sam");
//            u5.setFirstName("Ivan");
//            u6.setFirstName("Alex");
//            u7.setFirstName("Olga");
//            u8.setFirstName("Helen");
//            u9.setFirstName("Oleg");
//            u10.setFirstName("Leo");
//            String hql = "select e from User e inner join e.company";
//            Query query = session.createQuery(hql);
//            users = query.getResultList();

//
//            Criteria criteria2 = session.createCriteria(Company.class, "company");
//            criteria2.createCriteria("company.user", "user");
//            criteria2.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//
//            companies = criteria2.list();


            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
//        for (Iterator iterator = users.iterator(); iterator.hasNext(); ) {
//            System.out.println(iterator.next().toString());
//        }

//
//        for (Company company : companies) {
//            System.out.println(company.getCompany_name());
//            for (User user : company.getUsers()) {
//                System.out.println("    " + user.getName());
//            }
//        }
    }
}



