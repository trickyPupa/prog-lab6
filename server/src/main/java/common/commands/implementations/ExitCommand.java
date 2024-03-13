package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class ExitCommand extends AbstractCommand {

    public ExitCommand(Object[] args) {
        super("exit", "Команда для завершения работы.", "no", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.exit(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.exit(getArgs());
    }
}