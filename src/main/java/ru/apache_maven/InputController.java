package ru.apache_maven;

import org.apache.log4j.Logger;
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
    Logger logger = Logger.getLogger(InputController.class);

    private String info = "Please, sing up or log in first.\n" +
            "Then you can use commands below. Print <command> and press 'Enter', then " +
            "follow the further instructions.\n " +
            "Print 'end' to finish work.";
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
                logger.fatal("DB error.",e);
                break;
            }
        }
        sessionController.getSession().close();
        sessionController.getSessionFactory().close();
    }

    private boolean executeFromFile(String path) {
        logger.info("executing from file..");
        ArrayList<String> inputArgs;
        int lineCount = 0;
        File file = new File(path);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            logger.info("file was successfully opened");
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
                        System.out.print("  " + result.getHelp());
                    }
                }
            }
            fis.close();
            logger.info("file was successfully closed");
            br.close();

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            logger.error("file not found");
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
            System.out.println("\n1. Log in\n2.Sing up");
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
        System.out.println("List of commands: ");
        System.out.print(inputInterpreter.getHelp());
    }
}