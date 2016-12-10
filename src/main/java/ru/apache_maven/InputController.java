package ru.apache_maven;

import ru.apache_maven.commands.Command;
import ru.apache_maven.commands.Message;
import ru.apache_maven.models.User;

import java.util.*;

/**
 * Created by tania on 12/1/16.
 */
//client
public class InputController {
    String info = "...some instructions...\n";
    public SessionController sessionController = new SessionController();
    AllCommands allCommands = AllCommands.getInstance();
    Scanner sc = new Scanner(System.in);
    Integer command;
    boolean flag = true;

    public void init() {
        getInfo();

        enter();
        while (flag) {
            System.out.println("Choose command:");
            allCommands.showCommands();
            command = getAndCheckNumberInput();
            Command currentCommand = allCommands.commands.get(command);
            Message currentMessage = currentCommand.createMessage();
            ArrayList<String> linesInput;
            while (currentMessage.isHasNextStep()) {
                currentMessage.run();
                linesInput = getLinesInput();
                currentMessage.getArgs().add(linesInput);
            }
            currentCommand.execute();
            flag = false;
        }
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
                    sessionController.beginTransaction();
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

    public String getInfo() {
        return info;
    }
}


