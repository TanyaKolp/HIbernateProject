package ru.apache_maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.commands.*;

import java.util.ArrayList;

/**
 * Created by tania on 12/1/16.
 */
@Component
public class AllCommands {
    public ArrayList<Command> commands = new ArrayList<Command>();
    public ArrayList<String> input;
    @Autowired
    Command showCompaniesCommand;
    @Autowired
    Command showStationsCommand;
    @Autowired
    Command setFavoriteCompaniesCommand;
    @Autowired
    Command showPriceListsOfAllCompaniesCommand;
    @Autowired
    Command showPriceListsOfFavoriteCompaniesCommand;
    @Autowired
    Command findStationsCommand;
    @Autowired
    Command showFavoriteCompaniesCommand;
    @Autowired
    Command showFavoriteStationsCommand;
    @Autowired
    Command findFavoriteStationsCommand;
    @Autowired
    Command addCompanyCommand;
    @Autowired
    Command addStationCommand;
    @Autowired
    Command addFuelTypeCommand;
    @Autowired
    Command deleteCompanyCommand;
    @Autowired
    Command deleteStationCommand;
    @Autowired
    Command deleteFuelTypeCommand;


    public void executeCommand() {
        switch (input.get(0)) {
            case "show":
                if (input.get(1).equalsIgnoreCase("all")) {
                    if (input.get(2).startsWith("company")) {
                        showCompaniesCommand.execute();
                    } else if (input.get(2).startsWith("price")) {
                        showPriceListsOfAllCompaniesCommand.execute();
                    } else {
                        showStationsCommand.execute();
                    }
                } else {
                    if (input.get(2).startsWith("company")) {
                        showFavoriteCompaniesCommand.execute();
                    } else if (input.get(2).startsWith("price")) {
                        showPriceListsOfFavoriteCompaniesCommand.execute();
                    } else {
                        showFavoriteStationsCommand.execute();
                    }
                }
                break;
            case "set":
                if (input.get(1).equalsIgnoreCase("favorite")) {
                    setFavoriteCompaniesCommand.execute(input.get(2));
                } else {
                    System.out.println("someth is wrong ");
                }
                break;
            case "find":
                if (input.get(1).equalsIgnoreCase("all")) {
                    findStationsCommand.execute(input.get(3));
                } else {
                    findFavoriteStationsCommand.execute(input.get(3));
                }
                if (input.get(1).equalsIgnoreCase("station by"))
                    findStationsCommand.execute(input.get(2));
                break;
            default:
                System.out.println("someth is wrong ");
                break;
        }
    }

    public void executeAdminCommand() {
        switch (input.get(0)) {
            case "create":
                if (input.get(1).equalsIgnoreCase("company")) {
                    addCompanyCommand.execute(input.get(2));
                } else if (input.get(1).equalsIgnoreCase("station")) {
                    addStationCommand.execute(input.get(2));
                } else if (input.get(1).equalsIgnoreCase("fuelType")) {
                    addFuelTypeCommand.execute(input.get(2));
                }
                break;
            case "delete":
                if (input.get(1).equalsIgnoreCase("company")) {
                    deleteCompanyCommand.execute(input.get(2));
                } else if (input.get(1).equalsIgnoreCase("station")) {
                    deleteStationCommand.execute(input.get(2));
                } else if (input.get(1).equalsIgnoreCase("fuelType")) {
                    deleteFuelTypeCommand.execute(input.get(2));
                }
                break;
            case "change":
                if (input.get(1).equalsIgnoreCase("company")) {
//                    changeCompanyCommand.execute(input.get(2));
                } else if (input.get(1).equalsIgnoreCase("station")) {
//                    changeStationCommand.execute(input.get(2));
                } else if (input.get(1).equalsIgnoreCase("fuelType")) {
//                    changeFuelTypeCommand.execute(input.get(2));
                }
                break;
            default:
                System.out.println("someth is wrong ");
                break;
        }
    }
}
