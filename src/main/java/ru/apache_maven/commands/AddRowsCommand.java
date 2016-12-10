package ru.apache_maven.commands;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tania on 12/2/16.
 */
public class AddRowsCommand implements Command {
    private Message message;
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void execute() {
//        SessionFactory sessionFactory = HibUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        try {
//            session.beginTransaction();
//
//
//
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//            sessionFactory.close();
//        }
        System.out.println("you enter data: ");
        ArrayList<String> lines = message.getArgs().get(1);
        for(String s : lines) {
            System.out.println(s);
        }
        System.out.println("success");
    }

    public void execute(Session session) {

    }

    public String getName() {
        return "Add data";
    }

    @Override
    public Message createMessage() {
        Message message = new Message();
        ArrayList<String> messageForUser = new ArrayList<>();
        ArrayList<Command> commands = new ArrayList<>();
        messageForUser.add("Choose table:");
        messageForUser.add("Enter data:");
        commands.add(new ShowAndSetTableCommand());
        commands.add(new ShowAndSetColumnsCommand());

        message.setMassagesForUser(messageForUser);
        message.setCommands(commands);
        this.message = message;
        return message;
    }

    @Override
    public Message getMassage() {
        return message;
    }


}
