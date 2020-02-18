package ru.apache_maven.commands;

import java.util.List;

/**
 * Created by tania on 30.01.17.
 */
public class Result {
    private boolean success;
    private List<String> messages;
    private String help;
    private String errorMessage;
    private List<String> resultMessage;

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(List<String> resultMessage) {
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
