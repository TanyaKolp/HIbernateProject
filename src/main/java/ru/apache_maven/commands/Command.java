package ru.apache_maven.commands;

import org.hibernate.Session;

/**
 * Created by tania on 12/2/16.
 */
public interface Command {
    public void execute();
    public void execute(Session session);
    public String getName();
    public Message createMessage();
    public Message getMassage();
}
