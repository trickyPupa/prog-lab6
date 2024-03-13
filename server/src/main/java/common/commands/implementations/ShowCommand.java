package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class ShowCommand extends AbstractCommand {
    public ShowCommand(Object[] args) {
        super("show", "Команда для просмотра коллекции.", "no", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.show(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.show(getArgs());
    }
}
