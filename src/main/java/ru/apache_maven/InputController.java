package ru.apache_maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.commands.Command;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tania on 12/1/16.
 */
@Component
public class InputController {
    String info = "...some instructions...";
    SessionController sessionController = SessionController.getInstance();
    @Autowired
    AllCommands allCommands;
    Scanner sc = new Scanner(System.in);
    boolean flag = true;


    public void init() {
        getInfo();
        enter();
        while (flag) {
            System.out.println("Print command...");
            allCommands.input =parseInputString();
            allCommands.executeCommand();
            System.out.println("Print 'end' to finish work, or press 'Enter' to continue");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("end")) {
                flag = false;
            }
        }
        sessionController.commitTransaction();
        sessionController.closeSession();
    }

    private void enter() {
        System.out.println("1. Log in\n2.Sing up");
        int input = getAndCheckNumberInput();
        switch (input) {
            case 1:
                while (true) {
                    System.out.println("Enter login:");
                    String login = sc.nextLine();
                    System.out.println("Enter password:");
                    String password = sc.nextLine();
                    if (sessionController.checkUser(login, password)) {
                        break;
                    } else {
                        System.out.println("Try again.");
                    }
                }
                break;
            case 2:
                while (true) {
                    System.out.println("Enter your login:");
                    String login = sc.nextLine();
                    System.out.println("Enter your password:");
                    String password = sc.nextLine();
                    if (sessionController.addUser(login, password)) {
                        break;
                    }
                    System.out.println("This login is busy.");
                }
                break;
            default:
                System.out.println("Wrong enter! Choose 1 or 2. ");
                System.exit(0);
                break;
        }
    }


    public Integer getAndCheckNumberInput() {
        Integer input = null;
        try {
            input = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Wrong enter!..");
        }
        sc.nextLine();
        return input;
    }

    public ArrayList<String> getLinesInput() {
        ArrayList<String> data = new ArrayList<>();
        while (true) {

            String nextLine;
            nextLine = sc.nextLine();
            if (nextLine.equalsIgnoreCase("")) {
                break;
            }
            data.add(nextLine);
        }
        return data;
    }

    public void getInfo() {
        System.out.println(info);
    }

    public ArrayList<String> parseInputString() {
        ArrayList<String> inputList = new ArrayList<>();
        while (true) {
            String input = sc.nextLine();
            Pattern pattern = Pattern.compile("^(show|set|find)+ " +
                    "(compan[ies,y]|favorite|stations by|price lists|my price lists)+" +
                    "( [A-Za-z0-9]*[, [A-Za-z0-9]*[A-Za-z0-9]+]*)?");
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                inputList.add(matcher.group(1));
                inputList.add(matcher.group(2));
                inputList.add(matcher.group(3));
                break;
            } else {
                System.out.println("Wrong enter!..");
                System.out.println("Use pattern: <command> <entity> <value(s)>");
            }
        }
        return inputList;
    }
}


