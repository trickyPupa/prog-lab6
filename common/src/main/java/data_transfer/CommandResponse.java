package data_transfer;

public class CommandResponse extends Response {
    private String message;

    public CommandResponse(String msg) {
        message = msg;
    }

    public String getMessage(){
        return message;
    }
}
