package common.commands.implementations;

import common.abstractions.AbstractReceiver;
import common.Utils;
import common.commands.abstractions.AbstractCommand;

public class UpdateCommand extends AbstractCommand {
    public UpdateCommand(Object[] args) {
        super("update", "Команда для обновления значений элемента коллекции с заданным id.",
                "id {element}", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.update(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.update(Utils.concatObjects(new Object[] {this}, getArgs()));
    }
}
