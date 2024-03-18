package managers;

import common.abstractions.*;
import common.commands.abstractions.Command;
import common.commands.implementations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Класс - обработчик команд программы; считывает команды из {@link IInputManager} и вызывает их.
 */
public class ServerCommandHandler {
    public class ShellValuables {
        private IOutputManager outputManager;
        private final HistoryManager historyManager;
        private CollectionManager collectionManager;
        private FileManager fileManager;
        private ClientsManager clientsManager;

        public final Map<String, Function<Object[], Command>> commands = new HashMap<>();

        public ShellValuables(ClientsManager cm, IOutputManager out, CollectionManager col,
                              FileManager fm, HistoryManager history){
            outputManager = out;
            collectionManager = col;
            historyManager = history;
            fileManager = fm;
            clientsManager = cm;
        }

        public HistoryManager getHistoryManager() {
            return historyManager;
        }

        public CollectionManager getCollectionManager() {
            return collectionManager;
        }

        public IOutputManager getOutputManager() {
            return outputManager;
        }

        public FileManager getFileManager() {
            return fileManager;
        }

        public ClientsManager getClientsManager(){
            return clientsManager;
        }
    }

    protected ShellValuables vals;

    protected AbstractReceiver receiver;

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

    public ServerCommandHandler(IOutputManager out, CollectionManager col, FileManager fm, ClientsManager cm){
        vals = new ShellValuables(cm, out, col, fm, new HistoryManager());
        receiver = new ServerCommandReceiver(vals);
    }

    public void nextCommand() {
        Command currentCommand = vals.getClientsManager().getNextRequest();

        currentCommand.execute(receiver);
        vals.getHistoryManager().next(currentCommand);
    }
}
