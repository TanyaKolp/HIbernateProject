package ru.apache_maven.commands;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tania on 12/3/16.
 */
public class ShowAndSetColumnsCommand implements Command {
    private String tableName;
    private Message message;

    public  void execute() {

//        SessionFactory sessionFactory = HibUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        try {
//            session.beginTransaction();
//
//            ArrayList<String> columns = (ArrayList<String>) session.createSQLQuery("select COLUMN_NAME" +
//                    " from information_schema.COLUMNS" +
//                    " where TABLE_NAME='" + tableName + "'").list();
//            for (int i = 0; i < columns.size(); i++) {
//                System.out.println(i + ".  " + columns.get(i));
//
//            }
//
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//            sessionFactory.close();
//        }
        System.out.println("you choose table number: " + message.getArgs().get(0).toString());
    }

    public void execute(Session session) {
        ArrayList<String> columns = (ArrayList<String>) session.createSQLQuery("select COLUMN_NAME" +
                " from information_schema.COLUMNS" +
                " where TABLE_NAME='" + tableName + "'").list();
        for (int i = 0; i < columns.size(); i++) {
            System.out.println(i + ".  " + columns.get(i));

        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public String getName() {
        return null;
    }

    @Override
    public Message createMessage() {
        Message message = new Message();
        ArrayList<String> messageForUser = new ArrayList<>();
        ArrayList<Command> commands = new ArrayList<>();
        messageForUser.add("Choose table:");
        commands.add(new ShowAndSetTableCommand());

        message.setMassagesForUser(messageForUser);
        message.setCommands(commands);
        this.message = message;
        return message;
    }

    @Override
    public Message getMassage() {
        return message;
    }

    public List<String> getData() {
        return null;
    }

}
