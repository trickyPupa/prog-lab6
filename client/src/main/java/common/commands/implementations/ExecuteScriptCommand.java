package common.commands.implementations;

import common.abstractions.AbstractReceiver;
import common.commands.abstractions.AbstractCommand;

public class ExecuteScriptCommand extends AbstractCommand {
    public ExecuteScriptCommand(Object[] args) {
        super("execute_script", "Команда для исполнения скрипта из заданного файла.",
                "file_name", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.executeScript(s);
    }

    @Override
    public void execute(AbstractReceiver rec) {
        rec.executeScript(getArgs());
    }
}