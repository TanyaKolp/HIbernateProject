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



