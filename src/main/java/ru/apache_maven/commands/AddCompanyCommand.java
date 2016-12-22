package ru.apache_maven.commands;

import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.FuelType;

import java.util.StringTokenizer;

/**
 * Created by tania on 12/21/16.
 */
@Component
public class AddCompanyCommand implements Command {
    SessionController sessionController = SessionController.getInstance();

    @Override
    public void execute() {

    }

    public void execute(String input) {
        Company newCompany = new Company();
        newCompany.setCompany_name(input);
        sessionController.saveSession(newCompany);
    }
}
