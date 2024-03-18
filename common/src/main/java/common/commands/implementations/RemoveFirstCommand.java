package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class RemoveFirstCommand extends AbstractCommand {
    public RemoveFirstCommand(Object[] arg) {
        super("remove_first", "Команда для удаления первого элемента коллекции.",
                "no", arg);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.removeFirst(s);
    }

    @Override
    public void execute(AbstractReceiver rec) {
        rec.removeFirst(getArgs());
    }
}