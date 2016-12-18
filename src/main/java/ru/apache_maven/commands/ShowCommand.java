package ru.apache_maven.commands;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;

import java.util.ArrayList;

/**
 * Created by tania on 12/12/16.
 */
@Component
public class ShowCommand implements Command {
    SessionController sessionController = SessionController.getInstance();
    @Override
    public void execute() {
    }
}
