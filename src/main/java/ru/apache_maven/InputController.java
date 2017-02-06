package ru.apache_maven;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.commands.Result;

import java.io.*;
import java.util.*;

/**
 * Created by tania on 12/1/16.
 */
@Component
public class InputController {
    private String info = "You can use 3 commands: set, find, show.\n" +
            "Pattern: '<command> <first part> <second part>'\n" +
            "<command> can be 'set', 'find' or 'show'\n" +
            "<first part> can be 'all' or 'my' with 'find' and 'show' command and 'favorite' with 'set' command\n" +
            "'my' means use only your favorite companies, 'all' - all companies\n" +
            "<second part> tells what you want to see.\n" +
            "For example, 'show all station' means print information about stations of all companies\n" +
            "For 'set' command in this part you should print values, separated by ','\n" +
            "You can enter search terms in this part - print after keyword 'by' search criteria\n" +
            " (it could be 'road', 'city' or 'region') and then value, for example:\n" +
            "'find my station by city Moscow'\n";
    @Autowired
    private SessionController sessionController;
    @Autowired
    private InputInterpreter inputInterpreter;
    private Scanner sc = new Scanner(System.in);

    void init() {
        ArrayList<String> inputArgs;
        getInfo();
        enter();
        while (true) {
            System.out.println("\nPrint command:");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("end")) {
                System.out.println("Bye.");
                break;
            }
            System.out.println("Working...");
            inputArgs = new ArrayList<String>(Arrays.asList(input.split(" ")));
            try {
                if (inputArgs.get(0).equalsIgnoreCase("file")) {
                    executeFromFile(inputArgs.get(1));
                } else {
                    Result result = inputInterpreter.executeCommand(inputArgs);
                    printMessages(result);
                    if (result.getHelp() != null) {
                        System.out.println(result.getHelp());
                    }
                }
            } catch (HibernateException e) {
                e.printStackTrace();
                sessionController.getSession().getTransaction().rollback();
                System.out.println("INTERNAL ERROR! Application will be stopped.");
                break;
            }
        }
        sessionController.getSession().close();
        sessionController.getSessionFactory().close();
    }

    private boolean executeFromFile(String path) {
        ArrayList<String> inputArgs;
        int lineCount = 0;
        File file = new File(path);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) { // метод readline построчное чтение
                lineCount++;
                inputArgs = new ArrayList<String>(Arrays.asList(line.split(" ")));
                Result result = inputInterpreter.executeCommand(inputArgs);
                if (result.isSuccess()) {
                    System.out.println("Line #" + lineCount + ": ");
                    printInLineMessages(result);
                } else {
                    System.out.println("ERROR! Line #" + lineCount + ": ");
                    printInLineMessages(result);
                    if (result.getHelp() != null) {
                        System.out.print("  "+result.getHelp());
                    }
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void printMessages(Result result) {
        System.out.println();
        if (result.getMessages() != null) {
            for (String s : result.getMessages()) {
                System.out.println(s);
            }
        }
    }
    private void printInLineMessages(Result result) {
        if (result.getMessages() != null) {
            for (String s : result.getMessages()) {
                System.out.print(s);
            }
        }
    }

    private void enter() {
        ArrayList<String> inputArgs;
        while (true) {
            inputArgs = new ArrayList<>();
            System.out.println("1. Log in\n2.Sing up");
            String input = getAndCheckNumberInput();
            inputArgs.add(input);
            System.out.print("Enter login: ");
            String login = sc.nextLine();
            inputArgs.add(login);
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            inputArgs.add(password);
            Result result = inputInterpreter.executeCommand(inputArgs);
            if (result.isSuccess()) {
                break;
            } else {
                System.out.println(result.getMessages());
                System.out.println("Try again.");
            }
        }
    }

    private String getAndCheckNumberInput() {
        String userChoice = null;
        try {
            int input = sc.nextInt();
            if (input == 1) {
                userChoice = "logIn";
            } else if (input == 2) {
                userChoice = "singUp";
            } else {
                return null;
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong enter! Not a number");
        }
        sc.nextLine();
        return userChoice;
    }

    public void getInfo() {
        System.out.println(info);
    }
}