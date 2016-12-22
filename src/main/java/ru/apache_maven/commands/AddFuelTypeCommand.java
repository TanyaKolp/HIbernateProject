package ru.apache_maven.commands;

import org.springframework.stereotype.Component;
import ru.apache_maven.SessionController;
import ru.apache_maven.models.FuelType;

import java.util.StringTokenizer;

/**
 * Created by tania on 12/22/16.
 */
@Component
public class AddFuelTypeCommand implements Command {
    SessionController sessionController = SessionController.getInstance();

    @Override
    public void execute() {

    }

    public void execute(String input) {
        FuelType newType = new FuelType();
        Float price;
        StringTokenizer st = new StringTokenizer(input);
        newType.setTitle(st.nextToken());
        try{
            price = new Float(st.nextToken());
            newType.setPrice(price);
        }catch (Exception e){
            System.out.println("not a number for price");
        }
        sessionController.saveSession(newType);
    }
}

