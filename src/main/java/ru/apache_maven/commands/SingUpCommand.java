package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.User;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tania on 31.01.17.
 */
@Component
@Named("singUp")
public class SingUpCommand implements Command {
    @Autowired
    SessionController sessionController ;
    @Override
    public void execute() {

    }

    @Override
    public Result execute(List<String> input) {
        Result result = new Result();
        ArrayList<String> messages = new ArrayList<>();
        try {
            sessionController.getSession().beginTransaction();
            Criteria criteria = sessionController.getSession().createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("login", input.get(0))).uniqueResult();
            if (user == null) {
                User newUser = new User(input.get(0), input.get(1));
                sessionController.getSession().save(newUser);
                sessionController.setCurrentUser(newUser);
                result.setSuccess(true);
                messages.add("Welcome, " + input.get(0));
            } else {
                result.setSuccess(false);
                messages.add("Login '"+ input.get(0) +"' is busy.");
            }
            sessionController.getSession().getTransaction().commit();
        } catch (HibernateException e) {
            sessionController.getSession().getTransaction().rollback();
            System.out.println("sm is wrong");
            e.printStackTrace();
        }
        result.setMessages(messages);
        return result;
    }

    @Override
    public String getHelp() {
        return null;
    }
}
