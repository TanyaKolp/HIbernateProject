package ru.apache_maven.commands;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.GasStation;
import ru.apache_maven.models.Location;

import javax.inject.Named;
import java.util.*;

/**
 * Created by tania on 31.01.17.
 */
@Component
@Named("find")
public class FindCommand implements Command {
    @Autowired
    SessionController sessionController;
//                "'find my station by city Moscow, road M4'\n";

    private String help = "\nAfter word 'find' print:\n" +
            "1) find all or only favorite?\n2) 'station by'\n3) by what?\n4) value\n" +
            "\t1. all or my\n" +
            "\t3. city, region or road\n";
    private ArrayList<String> messages;

    @Override
    public void execute() {

    }

    @Override
    public Result execute(List<String> input) {
        Result result = new Result();
        messages = new ArrayList<>();
        try {
            sessionController.getSession().beginTransaction();
            if (input.size() > 4) {
                if (findStations(input)) {
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
        } catch (HibernateException e) {
            sessionController.getSession().getTransaction().rollback();
            result.setSuccess(false);
            messages = new ArrayList<>();
            messages.add("sm is wrong");
            e.printStackTrace();
        }
        result.setMessages(messages);
        return result;
    }

    @Override
    public String getHelp() {
        return this.help;
    }

    private Collection<Company> getCompanies(String arg) {
        Collection<Company> companies = null;
        if (arg.equalsIgnoreCase("all")) {
            companies = (ArrayList<Company>) sessionController.getSession().createCriteria(Company.class).list();
        } else if (arg.equalsIgnoreCase("my")) {
            companies = sessionController.getCurrentUser().getCompanies();
        } else {
            messages.add("ERROR! Wrong argument. Choose 'all' or 'my'");
        }
        return companies;
    }

    private HashMap<String, String> getConditionMap(List<String> args) {
        HashMap<String, String> conditions = new HashMap<>();
        for (int i = 0; i < args.size() - 1; i = i + 2) {
            String s = args.get(i + 1).replaceAll(",", "");
            if (s.equalsIgnoreCase("y")) {
                s = "true";
            } else if (s.equalsIgnoreCase("n")) {
                s = "false";
            } else {
                if (args.get(i).equalsIgnoreCase("cafe") || args.get(i).equalsIgnoreCase("shop")) {
                    messages.add("ERROR! Wrong value. For cafe and shop choose 'y' or 'n'");
                    return null;
                }
            }
            conditions.put(args.get(i), s);
        }
        return conditions;
    }


    private boolean findStations(List<String> args) {
        String firstArg = args.get(0);
        for (int i = 0; i < 3; i++) {
            args.remove(0);
        }
        HashMap<String, String> conditions = getConditionMap(args);
        if (conditions == null) {
            return false;
        }
        Collection<Company> companies = getCompanies(firstArg);
        ArrayList<String> foundStations =   new ArrayList<>();
        Boolean found = null;
        for (Company company : companies) {
            List<GasStation> gasStations = company.getStations();
            for (GasStation gs : gasStations) {
                for (String key : conditions.keySet()) {
                    String[] criterion = {key, conditions.get(key)};
                    found = checkConditions(criterion, gs);
                    if (found != null && !found) {
                        return false;
                    }
                }
                if (found != null && found) {
                    foundStations.add(company.getName() + " - " + gs.getName() + " - " + gs.getLocation().getAddress());
                }
            }
        }
        if (!foundStations.isEmpty()) {
            messages = foundStations;
        } else {
            messages.add("\nNot found.");
        }
        return true;
    }

    private Boolean checkConditions(String[] criterion, GasStation gs) {
        switch (criterion[0]) {
            case "road":
                if (criterion[1].equalsIgnoreCase(gs.getLocation().getRoadNumber())) return true;
                break;
            case "region":
                if (criterion[1].equalsIgnoreCase(gs.getLocation().getRegion())) return true;
                break;
            case "city":
                if (criterion[1].equalsIgnoreCase(gs.getLocation().getCity())) return true;
                break;
            case "cafe":
                if (criterion[1].equalsIgnoreCase(String.valueOf(gs.getCafe()))) return true;
                break;
            case "shop":
                if (criterion[1].equalsIgnoreCase(String.valueOf(gs.getShop()))) return true;
                break;
            default:
                messages.add("ERROR! Wrong argument (3): '" + criterion[0] + "'.");
                return false;
        }
        return null;
    }
}
