package ru.apache_maven.commands;

import java.util.ArrayList;

/**
 * Created by tania on 12/4/16.
 */
public class Message {
    private ArrayList<String> massagesForUser;
    private ArrayList<Command> commands;
    private ArrayList<ArrayList<String>> args = new ArrayList<>();
    private int currentStep;
    private boolean hasNextStep = true;

    public ArrayList<ArrayList<String>> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<ArrayList<String>> args) {
        this.args = args;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void setMassagesForUser(ArrayList<String> massagesForUser) {
        this.massagesForUser = massagesForUser;
    }

    public boolean isHasNextStep() {
        return hasNextStep;
    }

    public void run() {
        System.out.println(massagesForUser.get(currentStep));
        Message currentMessage = commands.get(currentStep).createMessage();
        if (currentMessage != null) {
            commands.get(currentStep).getMassage().args = this.args;
            commands.get(currentStep).execute();
        } else {
            commands.get(currentStep).execute();
        }
        currentStep++;
        if (currentStep > (commands.size() - 1)) {
            hasNextStep = false;
        }
    }
}
