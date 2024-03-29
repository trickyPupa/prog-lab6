package common.commands.implementations;

import common.abstractions.AbstractReceiver;
import common.Utils;
import common.commands.abstractions.AbstractCommand;

public class RemoveLowerCommand extends AbstractCommand {
    public RemoveLowerCommand(Object[] args) {
        super("remove_lower", "Команда для удаления всех элементов коллекции, меньших чем заданный.",
                "{element}", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.removeLower(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.removeLower(Utils.concatObjects(new Object[] {this}, getArgs()));
    }
}
