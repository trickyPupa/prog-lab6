package common.commands.implementations;

import common.abstractions.AbstractReceiver;
import common.Utils;
import common.commands.abstractions.AbstractCommand;

public class SaveCommand extends AbstractCommand {
    public SaveCommand(Object[] args){
        super("save", "Команда для сохранения текущей версии коллекции в файл.", "no", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver rec) {
        rec.save(s);
    }
    @Override
    public void execute(AbstractReceiver rec) {
        rec.save(getArgs());
    }
}
