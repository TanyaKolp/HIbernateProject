package ru.apache_maven;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.apache_maven.commands.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by tania on 12/1/16.
 */
@Component
public class InputInterpreter {
    private final Map<String, Command> commands;
    Logger logger = Logger.getLogger(InputInterpreter.class);

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
            logger.error("NO SUCH COMMAND:" + input.get(0));
        }
        return result;
    }

    public String getHelp() {
        String string = "";
        Set<String> keys = commands.keySet();
        String[] keyArray =  keys.toArray(new String[0]);
        for (int i = 0; i < keyArray.length - 1; i++) {
            string += keyArray[i] + ", ";
        }
        string += keyArray[keyArray.length - 1];
        return string;
    }
}
