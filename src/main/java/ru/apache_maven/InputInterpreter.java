package ru.apache_maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.commands.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by tania on 12/1/16.
 */
@Component
public class InputInterpreter {
    private final Map<String, Command> commands;

    @Autowired
    private InputInterpreter(Map<String, Command> commands) {
        this.commands = commands;
    }

    private Map<String, Command> getCommands() {
        return commands;
    }

    public Result executeCommand(ArrayList<String> input) {
        Result result;
        Command command = commands.get(input.get(0));
        ArrayList<String> messages = new ArrayList<>();
        input.remove(0);
        if (command != null) {
            result = command.execute(input);
        } else {
            result = new Result();
            result.setSuccess(false);
            messages.add("NO SUCH COMMAND! Choose one of these commands: ");
            result.setHelp(getHelp());
            result.setMessages(messages);
        }
        return result;
    }

    private String getHelp() {
        String string = "";
        for (String key : commands.keySet()) {
            string += key + ",";
        }
        return string;
    }
}
