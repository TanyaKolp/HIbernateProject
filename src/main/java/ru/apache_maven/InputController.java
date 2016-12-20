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
    String info = "You can use 3 commands: set, find, show.\n" +
            "Pattern: '<command> <first part> <second part>'\n" +
            "<command> can be 'set', 'find' or 'show'\n" +
            "<first part> can be 'all' or 'my' with 'find' and 'show' command and 'favorite' with 'set' command\n" +
            "'my' means use only your favorite companies, 'all' - all companies\n" +
            "<second part> tels what you want to see.\n" +
            "For example, 'show all station' means print information about stations of all companies\n" +
            "For 'set' command in this part you should print values, separated by ','\n" +
            "You can enter search terms in this part - print after keyword 'by' search criteria\n" +
            " (it could be 'road', 'city' or 'region') and then value, for example:\n" +
            "'find my station by city Moscow'" ;
    SessionController sessionController = SessionController.getInstance();
    @Autowired
    AllCommands allCommands;
    Scanner sc = new Scanner(System.in);
    boolean flag = true;
    String input;


    public void init() {
        getInfo();
        enter();
        while (flag) {
            while (true) {
                System.out.println("Print command...");
                input = sc.nextLine();
                if (!parseInputString()) {
                    continue;
                } else {
                    break;
                }
            }
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

    public boolean parseInputString() {
        ArrayList<String> inputList = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        String currentWord;
        StringTokenizer st = new StringTokenizer(input);
        if (st.countTokens() < 3) {
            System.out.println("Wrong enter!");
            System.out.println("Please, enter query again:");
            return false;
        } else {
            //check first word
            currentWord = st.nextToken();
            pattern = Pattern.compile("(set|find|show)");
            matcher = pattern.matcher(currentWord);
            if (matcher.matches()) {
                inputList.add(currentWord);
            } else {
                System.out.println("Wrong command!");
                System.out.println("Please, enter query again:");
                return false;
            }
            //check second word
            currentWord = st.nextToken();
            pattern = Pattern.compile("(all|my|favorite|firstName|lastName)");
            matcher = pattern.matcher(currentWord);
            if (matcher.matches()) {
                inputList.add(currentWord);
            } else {
                System.out.println("Wrong second word!");
                System.out.println("Please, enter query again:");
                return false;
            }
            //check third word
            currentWord = st.nextToken();
            if (inputList.get(0).equalsIgnoreCase("set")) {
                inputList.add(currentWord);
            } else {
                pattern = Pattern.compile("(company|priceList|station)");
                matcher = pattern.matcher(currentWord);
                if (matcher.matches()) {
                    inputList.add(currentWord);
                }else {
                    System.out.println("Wrong third word!");
                    System.out.println("Please, enter query again:");
                    return false;
                }
            }
            //check end of string
            if (st.hasMoreTokens()) {
                currentWord = st.nextToken();
                if (currentWord.equalsIgnoreCase("by")) {
                    currentWord = input.substring(input.indexOf("by")+3);
                    pattern = Pattern.compile("(city|region|road) [A-Za-z0-9]+" +
                            "(, (city|region|road) [A-Za-z0-9]+)*");
                    matcher = pattern.matcher(currentWord);
                    if(matcher.matches()){
                        inputList.add(currentWord);
                    }else {
                        System.out.println("Wrong enter! You should choose condition - 'city', 'region' or 'road'");
                        System.out.println("Please, enter query again:");
                        return false;
                    }
                }else {
                    System.out.println("No key word 'by'");
                    System.out.println("Please, enter query again:");
                    return false;
                }
            }
        }
        allCommands.input = inputList;
        return true;
    }
}


//while (true) {
//            pattern = Pattern.compile("^(show|set|find)+ " +
//                    "(compan[ies,y]|favorite|stations by|price lists|my price lists)+" +
//                    "( [A-Za-z0-9]*[, [A-Za-z0-9]*[A-Za-z0-9]+]*)?");
//            matcher = pattern.matcher(input);
//            if (matcher.matches()) {
//                inputList.add(matcher.group(1));
//                inputList.add(matcher.group(2));
//                inputList.add(matcher.group(3));
//                break;
//            } else {
//                System.out.println("Wrong enter!..");
//                System.out.println("Use pattern: <command> <entity> <value(s)>");
//            }
//        }