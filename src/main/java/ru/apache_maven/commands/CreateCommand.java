package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.FuelType;
import ru.apache_maven.models.GasStation;
import ru.apache_maven.models.Location;

import javax.inject.Named;
import java.io.*;
import java.util.*;

/**
 * Created by tania on 24.01.17.
 */
@Component
@Named("create")
public class CreateCommand implements Command {
    @Autowired
    private SessionController sessionController;
    private String help = "After word 'create' print:\n" +
            "1) what to create\n2) what characteristic\n" +
            "1. company, fuelType or station\n" +
            "2. for company:\n\tname\n" +
            "for fuelType:\n\tcompany name\n\ttitle\n\tprice\n" +
            "for station:\n\tcompany name\n\tnumber\n\tshop[y/n]\n\tcafe[y/n]" +
            "\n\tregion/-\n\tcity/-\n\troad/-\n\tstreet/-\n\tbuilding/-";
    private Map<String, Creation> createCommandMap = new HashMap<>();

    @Autowired
    public CreateCommand(Map<String, Creation> createCommandMap) {
        this.createCommandMap = createCommandMap;
    }

    @Override
    public void execute() {

    }

    @Override
    public Result execute(List<String> input) {
        Creation currentCommand;
        Result result = new Result();
        ArrayList<String> messages = new ArrayList<String>();
        if (!checkUserRights()) {
            result.setSuccess(false);
            messages.add("You have no rights!");
        }
        if (input.size() != 0) {
            currentCommand = createCommandMap.get(input.get(0));
            if (currentCommand == null) {
                messages.add("ERROR! No such entity!");
                result.setSuccess(false);
                result.setHelp(getHelp());
                result.setMessages(messages);
            } else {
                input.remove(0);
                result = currentCommand.execute(input);
            }
        } else {
            messages.add("");
            result.setSuccess(false);
            result.setHelp(getHelp());
            result.setMessages(messages);
        }
        return result;
    }

    private boolean checkUserRights() {
        return sessionController.getCurrentUser().isAdmin();
    }

    @Override
    public String getHelp() {
        return this.help;
    }
}
