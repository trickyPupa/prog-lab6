package client;

import client.exceptions.NoSuchCommandException;
import common.abstractions.Handler;
import common.abstractions.IInputManager;
import common.abstractions.IOutputManager;
import common.commands.abstractions.Command;
import common.commands.implementations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Класс - обработчик команд программы; считывает команды из {@link IInputManager} и провоцирует их исполнение.
 */
public class ClientCommandHandler implements Handler {

    private IInputManager inputManager;
    private IOutputManager outputManager;
    private AbstractServerRequestManager serverRequestManager;

    public final Map<String, Function<Object[], Command>> commands = new HashMap<>();

    {
        commands.put("help", HelpCommand::new);
        commands.put("exit", ExitCommand::new);
        commands.put("add", AddCommand::new);
        commands.put("show", ShowCommand::new);
        commands.put("info", InfoCommand::new);
        commands.put("clear", ClearCommand::new);
        commands.put("update", UpdateCommand::new);
        commands.put("history", HistoryCommand::new);
        commands.put("remove_first", RemoveFirstCommand::new);
        commands.put("remove_by_id", RemoveByIdCommand::new);
        commands.put("filter_by_golden_palm_count", FilterByGoldenPalmCountCommand::new);
        commands.put("min_by_coordinates", MinByCoordinatesCommand::new);
        commands.put("remove_all_by_golden_palm_count", RemoveAllByGoldenPalmCountCommand::new);
        commands.put("remove_lower", RemoveLowerCommand::new);
        commands.put("execute_script", ExecuteScriptCommand::new);
    }

    public ClientCommandHandler(IInputManager inp, IOutputManager out, AbstractServerRequestManager srm){
        inputManager = inp;
        outputManager = out;
        serverRequestManager = srm;
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

        if (!commands.containsKey(commandName)){
            throw new NoSuchCommandException(line);
        }
        Command currentCommand = commands.get(commandName).apply(args);


        // сериализовать команду

        // отправить запрос серверу
        serverRequestManager.makeRequest(currentCommand);

        // получить ответ сервера
        String result = serverRequestManager.getResponse();

        outputManager.print(result);
    }

    @Override
    public void nextCommand() throws IOException {
        String line = inputManager.nextLine().strip();

        nextCommand(line);
    }
}