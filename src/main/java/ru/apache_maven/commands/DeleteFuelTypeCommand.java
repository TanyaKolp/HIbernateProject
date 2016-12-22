package ru.apache_maven.commands;

import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.Company;

/**
 * Created by tania on 12/22/16.
 */
@Component
public class DeleteFuelTypeCommand implements Command {
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
