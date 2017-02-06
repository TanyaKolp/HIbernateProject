package ru.apache_maven.commands;

import java.util.ArrayList;

/**
 * Created by tania on 30.01.17.
 */
public class Result {
    private boolean success;
    private ArrayList<String> messages;
    private String help;
    private String errorMessage;
    private ArrayList<String> resultMessage;

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public ArrayList<String> getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(ArrayList<String> resultMessage) {
        this.resultMessage = resultMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
