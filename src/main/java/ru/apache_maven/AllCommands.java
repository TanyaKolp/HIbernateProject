package ru.apache_maven;

import ru.apache_maven.commands.AddRowsCommand;
import ru.apache_maven.commands.Command;

import java.util.ArrayList;

/**
 * Created by tania on 12/1/16.
 */
public class AllCommands {
    public ArrayList<Command> commands = new ArrayList<Command>();

    public static AllCommands instance = null;

    private AllCommands() {
    }

    public static AllCommands getInstance() {
        if (instance == null) {
            instance = new AllCommands();
            instance.fillList();
        }
        return instance;
    }

    private void fillList() {
        commands.add(new AddRowsCommand());
    }
    public void showCommands(){
        for (int i = 0; i < commands.size(); i++) {
            System.out.println("    " + i + ".  " + commands.get(i).getName());
        }
    }


}
