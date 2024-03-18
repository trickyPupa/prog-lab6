package common.commands.implementations;

import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;

public class FilterByGoldenPalmCountCommand extends AbstractCommand {
    public FilterByGoldenPalmCountCommand(Object[] args) {
        super("filter_by_golden_palm_count", "Команда для вывода элементов коллекции с заданным " +
                        "количество золотых пальмовых ветвей.",
                "goldenPalmCount", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.filterByGoldenPalmCount(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.filterByGoldenPalmCount(getArgs());
    }
}