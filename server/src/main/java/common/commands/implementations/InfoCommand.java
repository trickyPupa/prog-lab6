package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class InfoCommand extends AbstractCommand {
    public InfoCommand(Object[] args){
        super("info", "Команда для вывода информации о коллекции.", "no", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.info(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.info(getArgs());
    }
}
