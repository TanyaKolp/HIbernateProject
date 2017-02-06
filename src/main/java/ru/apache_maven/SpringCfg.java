package ru.apache_maven;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.apache_maven.models.*;

/**
 * Created by tania on 12/16/16.
 */
@Configuration
@ComponentScan(basePackages = {"ru.apache_maven.commands", "ru.apache_maven"})
public class SpringCfg {
    @Bean
    public static SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/GasStations");
        cfg.setProperty("hibernate.connection.username", "root" );
        cfg.setProperty("hibernate.connection.password", "rumin" );
        cfg.setProperty("hibernate.show_sql", "true" );
        cfg.setProperty("hibernate.hbm2ddl.auto", "update" );

        cfg.addAnnotatedClass(User.class);
        cfg.addAnnotatedClass(Company.class);
        cfg.addAnnotatedClass(GasStation.class);
        cfg.addAnnotatedClass(FuelType.class);
        cfg.addAnnotatedClass(Location.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
        return cfg.buildSessionFactory(builder.build());
    }

//    @Bean(name = "commands_2")
//    public Map<String, Command> getCommands() {
//        HashMap<String, Command> commands = new HashMap<>();
//        commands.put("null", new Command() {
//            @Override
//            public void execute() {
//
//            }
//
//            @Override
//            public void execute(String input) {
//
//            }
//        });
//        return commands;
//    }
}
