package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class ClearCommand extends AbstractCommand {
    public ClearCommand(Object[] args){
        super("clear", "Команда для очищения коллекции.", "no", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.clear(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.clear(getArgs());
    }
}
