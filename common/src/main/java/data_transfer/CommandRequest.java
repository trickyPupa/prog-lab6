package data_transfer;

import common.commands.abstractions.Command;

public class CommandRequest extends Request {
    private final Command command;

    public CommandRequest(Command c) {
        command = c;
    }

    public Command getCommand() {
        return command;
    }
}