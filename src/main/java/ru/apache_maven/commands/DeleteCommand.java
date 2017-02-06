package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.FuelType;
import ru.apache_maven.models.User;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by tania on 12/22/16.
 */
@Component
@Named("delete")
public class DeleteCommand implements Command {
    private String help = "\nAfter word 'delete' print:\n" +
            "1) delete what\n2) value\n" +
            "\t1. favorite (means favorite companies), fuelType\n" +
            "\t2. for favorite:\n\t\tcompany name or ID\n" +
            "\tfor fuelType:\n\t\ttitle or ID.\n";
    @Autowired
    SessionController sessionController;
    private ArrayList<String> messages;
    private HashMap<String, Class> entityTypeMap = new HashMap<>();

    public DeleteCommand() {
        entityTypeMap.put("fuelType", FuelType.class);
        entityTypeMap.put("company", Company.class);
    }

    @Override
    public void execute() {

    }

    @Override
    public Result execute(List<String> input) {
        Result result = new Result();
        messages = new ArrayList<>();
        sessionController.getSession().beginTransaction();
        if (input.size() > 1) {
            if (delete(input)) {
                result.setSuccess(true);
                messages.add("\nDone.");
            } else {
                result.setSuccess(false);
                result.setHelp(getHelp());
            }
        } else {
            messages.add("ERROR! Not enough arguments.");
            result.setSuccess(false);
            result.setHelp(getHelp());
        }
        sessionController.getSession().getTransaction().commit();
        result.setMessages(messages);
        return result;
    }

    @Override
    public String getHelp() {
        return this.help;
    }

    private boolean delete(List<String> args) {
        if (!sessionController.getCurrentUser().isAdmin()) {
            messages.add("You have no rights!");
            return false;
        }
        Class entity = entityTypeMap.get(args.get(0));
        if (entity == null) {
            return false;
        }
        Integer id = tryParseInt(args.get(1));
        if (id != null) {
            return deleteById(entity, id);
        } else {
            return deleteByName(entity, args.get(1));
        }
    }

    private boolean deleteFuelType(String identifier) {

        messages.add("There is no user with identifier '" + identifier + "'");
        return false;
    }

    private Integer tryParseInt(String value) {
        Integer id;
        try {
            id = new Integer(value);
        } catch (NumberFormatException e) {
            return null;
        }
        return id;
    }

    private boolean deleteById(Class<?> type, Serializable id) {
        Object entity = sessionController.getSession().load(type, id);
        if (entity != null) {
            sessionController.getSession().delete(entity);
            return true;
        }
        messages.add("There is no " + type.getName() + " with ID '" + id + "'");
        return false;
    }

    private boolean deleteByName(Class<?> type, String identifier) {
        Criteria criteria = sessionController.getSession().createCriteria(type);
        List<Object> entities = (List<Object>) criteria.add(Restrictions.eq("name", identifier)).list();
        if (!entities.isEmpty()) {
            if (entities.size() == 1) {
                sessionController.getSession().delete(entities.get(0));
            return true;
            }
            messages.add("There are several " + type.getName() + " with the same name.");
            messages.add("Find " + type.getName() + " in the following list and delete by ID");
            for (Object entity : entities) {
                messages.add(entity.toString());
            }
        }
        return false;
    }
}
