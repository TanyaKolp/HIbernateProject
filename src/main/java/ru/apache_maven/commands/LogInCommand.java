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
 * Created by tania on 25.01.17.
 */
@Component
@Named("logIn")
public class LogInCommand implements Authorization {
    @Autowired
    private SessionController sessionController;

    @Override
    public Result authorize(List<String> inputArgs) {
        Result result = new Result();
        ArrayList<String> messages = new ArrayList<>();
        try {
            sessionController.getSession().beginTransaction();
            Criteria criteria = sessionController.getSession().createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("login", inputArgs.get(0))).uniqueResult();
            if (user != null) {
                sessionController.setCurrentUser(user);
                if (user.getPassword().equalsIgnoreCase(inputArgs.get(1))) {
                    result.setSuccess(true);
                    messages.add("Welcome!");
                    result.setMessages(messages);
                } else {
                    result.setSuccess(false);
                    messages.add("Incorrect password!");
                    result.setMessages(messages);
                }
            } else {
                result.setSuccess(false);
                messages.add("Please, sing up.");
                result.setMessages(messages);
            }
            sessionController.getSession().getTransaction().commit();
        } catch (HibernateException e) {
            sessionController.getSession().getTransaction().rollback();
            System.out.println("someth is wrong");
        }
        return result;
    }
}
