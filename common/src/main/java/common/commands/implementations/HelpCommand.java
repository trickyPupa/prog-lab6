package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class HelpCommand extends AbstractCommand {
    public HelpCommand(Object[] args){
        super("help", "Команда для вывода справки по всем доступным командам.", "no", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.help(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.help(getArgs());
    }
}
