package ru.apache_maven.commands;

import org.hibernate.Session;

import java.util.List;

/**
 * Created by tania on 12/2/16.
 */
public interface Command {
    public void execute();
    public Result execute(List<String> input);
    public String getHelp();
}