package client;

import common.commands.abstractions.Command;

import java.net.InetAddress;

public abstract class AbstractClientRequestManager {
    protected InetAddress address;
    protected int port;

    public AbstractClientRequestManager(InetAddress serverAddress, int p) {
        address = serverAddress;
        port = p;
    }

    public abstract void makeRequest(Command c);

    public abstract String getResponse();
}
