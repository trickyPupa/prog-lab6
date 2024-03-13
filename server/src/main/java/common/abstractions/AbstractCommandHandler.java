package common.abstractions;

import common.commands.abstractions.AbstractCommand;
import common.commands.abstractions.Command;
import server.managers.CollectionManager;
import server.managers.FileManager;
import server.managers.HistoryManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractCommandHandler implements Handler {
    public class ShellValuables {
        private IInputManager inputManager;
        private IOutputManager outputManager;
        private final HistoryManager historyManager;
        private CollectionManager collectionManager;
        private FileManager fileManager;

        public final Map<String, Function<Object[], Command>> commands = new HashMap<>();

        public ShellValuables(IInputManager inp, IOutputManager out, CollectionManager col,
                              FileManager fm, HistoryManager history){
            inputManager = inp;
            outputManager = out;
            collectionManager = col;
            historyManager = history;
            fileManager = fm;
        }

        public IInputManager getInputManager() {
            return inputManager;
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

        public Handler getCommandHander(){
            return AbstractCommandHandler.this;
        }
    }

    protected ShellValuables vals;

    protected AbstractReceiver receiver;

    public AbstractCommandHandler(IInputManager inp, IOutputManager out, CollectionManager col, FileManager fm){
        vals = new ShellValuables(inp, out, col, fm, new HistoryManager());
    }
}
