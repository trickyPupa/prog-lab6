package commands;

import managers.ServerControlReceiver;

public class StopServerCommand extends AbstractServerCommand{
    public StopServerCommand(Object[] args) {
        super("stop", "Завершает работу сервера.", "no", args);
    }

    @Override
    public int execute(ServerControlReceiver rec) {
        return rec.stop();
    }
}
