package ru.apache_maven;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by tania on 11/16/16.
 */
public class HibUtil {
    private static SessionFactory factory;

    static {
        File f = new File("/home/tania/gasstations/src/main/java/ru/apache_maven/hibernate.cfg.xml");

        Configuration cfg = new Configuration().configure(f);
        cfg.addAnnotatedClass(User.class);
        cfg.addAnnotatedClass(Company.class);
        cfg.addAnnotatedClass(GasStation.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());

        factory = cfg.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
