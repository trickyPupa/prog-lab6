package managers;

import common.abstractions.*;
import common.commands.abstractions.Command;
import common.commands.implementations.*;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Класс - обработчик команд программы; считывает команды и вызывает их.
 */
public class ServerCommandHandler implements Handler{
    public class ShellValuables {
        private ServerOutputManager serverOutputManager;
        private final HistoryManager historyManager;
        private CollectionManager collectionManager;
        private FileManager fileManager;
        public final Logger logger;

        public final Map<String, Function<Object[], Command>> commands = new HashMap<>();

        public ShellValuables(ServerOutputManager out1, CollectionManager col,
                              FileManager fm, HistoryManager history, Logger log){
            serverOutputManager = out1;
            collectionManager = col;
            historyManager = history;
            fileManager = fm;
            logger = log;
        }

        public HistoryManager getHistoryManager() {
            return historyManager;
        }

        public CollectionManager getCollectionManager() {
            return collectionManager;
        }

        public ServerOutputManager getServerOutputManager() {
            return serverOutputManager;
        }

        public FileManager getFileManager() {
            return fileManager;
        }
    }

    protected ShellValuables vals;

    protected AbstractReceiver receiver;

    public ServerCommandHandler(ServerOutputManager server_out, CollectionManager col, FileManager fm, Logger logger){
        vals = new ShellValuables(server_out, col, fm, new HistoryManager(), logger);
        receiver = new ServerCommandReceiver(vals);

        {
            vals.commands.put("help", HelpCommand::new);
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
    }

    public void nextCommand(Command currentCommand) {

        currentCommand.execute(receiver);
        vals.getHistoryManager().next(currentCommand);
    }

    @Override
    public void nextCommand() throws IOException {

    }

    @Override
    public void nextCommand(String commandName) {

    }
}
