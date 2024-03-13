package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class RemoveAllByGoldenPalmCountCommand extends AbstractCommand {
    public RemoveAllByGoldenPalmCountCommand(Object[] args) {
        super("remove_all_by_golden_palm_count", "Команда для удаления всех элементов коллекции, " +
                        "с заданным количеством золотых пальмовых ветвей.",
                "goldenPalmCount", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.removeAllByGoldenPalmCount(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.removeAllByGoldenPalmCount(getArgs());
    }
}
