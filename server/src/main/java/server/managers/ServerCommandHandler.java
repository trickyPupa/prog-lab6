package server.managers;

import common.commands.abstractions.Command;
import common.commands.implementations.*;
import technical.exceptions.NoSuchCommandException;
import common.abstractions.AbstractCommandHandler;
import common.abstractions.Handler;
import common.abstractions.IInputManager;
import common.abstractions.IOutputManager;

import java.io.IOException;

/**
 * Класс - обработчик команд программы; считывает команды из {@link IInputManager} и провоцирует их исполнение.
 */
public class ServerCommandHandler extends AbstractCommandHandler implements Handler {
    {
        vals.commands.put("help", HelpCommand::new);
        vals.commands.put("save", SaveCommand::new);
        vals.commands.put("exit", ExitCommand::new);
        vals.commands.put("add", AddCommand::new);
        vals.commands.put("show", ShowCommand::new);
        vals.commands.put("info", InfoCommand::new);
        vals.commands.put("clear", ClearCommand::new);
        vals.commands.put("update", UpdateCommand::new);
        vals.commands.put("history", HistoryCommand::new);
        vals.commands.put("remove_first", RemoveFirstCommand::new);
        vals.commands.put("remove_by_id", RemoveByIdCommand::new);
        vals.commands.put("filter_by_golden_palm_count", FilterByGoldenPalmCountCommand::new);
        vals.commands.put("min_by_coordinates", MinByCoordinatesCommand::new);
        vals.commands.put("remove_all_by_golden_palm_count", RemoveAllByGoldenPalmCountCommand::new);
        vals.commands.put("remove_lower", RemoveLowerCommand::new);
        vals.commands.put("execute_script", ExecuteScriptCommand::new);
    }

    public ServerCommandHandler(IInputManager inp, IOutputManager out, CollectionManager col, FileManager fm){
        super(inp, out, col, fm);
        receiver = new BaseCommandReceiver(vals);
    }

    @Override
    public void nextCommand(String line) {
        line = line.strip();
        String commandName;
//        String[] args;
        Object[] args;

        if (line.contains(" ")){
            commandName = line.substring(0, line.indexOf(" ")).strip();
            args = line.substring(1 + commandName.length()).split(" ");
        } else{
            commandName = line.strip();
            args = new String[]{""};
        }

        if (!vals.commands.containsKey(commandName)){
            throw new NoSuchCommandException(line);
        }
        Command currentCommand = vals.commands.get(commandName).apply(args);

        currentCommand.execute(receiver);
        vals.getHistoryManager().next(currentCommand);
    }

    @Override
    public void nextCommand() throws IOException {
        IInputManager input = vals.getInputManager();

        String line = input.nextLine().strip();

        nextCommand(line);
    }
}
