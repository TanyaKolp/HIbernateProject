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
    ShowCompaniesCommand showCompaniesCommand;
    @Autowired
    ShowStationsCommand showStationsCommand;
    @Autowired
    SetFavoriteCompaniesCommand setFavoriteCompaniesCommand;
    @Autowired
    ShowPriceListsOfAllCompaniesCommand showPriceListsOfAllCompaniesCommand;
    @Autowired
    ShowPriceListsOfFavoriteCompaniesCommand showPriceListsOfFavoriteCompaniesCommand;
    @Autowired
    FindStationsCommand findStationsCommand;
    @Autowired
    ShowFavoriteCompaniesCommand showFavoriteCompaniesCommand;
    @Autowired
    ShowFavoriteStationsCommand showFavoriteStationsCommand;
    @Autowired
    FindFavoriteStationsCommand findFavoriteStationsCommand;

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
}
