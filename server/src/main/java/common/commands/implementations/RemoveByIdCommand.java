package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class RemoveByIdCommand extends AbstractCommand {
    public RemoveByIdCommand(Object[] args) {
        super("remove_by_id", "Команда для удаления элемента коллекции с заданным id.",
                "id", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.removeById(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.removeById(getArgs());
    }
}