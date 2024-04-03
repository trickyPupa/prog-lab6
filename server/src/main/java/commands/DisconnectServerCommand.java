package commands;

import common.abstractions.AbstractReceiver;
import common.commands.abstractions.AbstractCommand;
import managers.ServerControlReceiver;

public class DisconnectServerCommand extends AbstractServerCommand {
    public DisconnectServerCommand(Object[] args) {
        super("disconnect", "Завершает текущее соединение.", "no", args);
    }

    @Override
    public int execute(ServerControlReceiver rec) {
        return rec.disconnect();
    }
}
