package client;

import common.commands.abstractions.Command;

public abstract class AbstractServerRequestManager {
    public abstract void makeRequest(Command c);

    public abstract String getResponse();
}
