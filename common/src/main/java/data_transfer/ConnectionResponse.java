package data_transfer;

public class ConnectionResponse extends Response{
    protected boolean success;

    public ConnectionResponse(String msg, boolean success) {
        super(msg);
        this.success = success;
    }

    public ConnectionResponse(boolean success) {
        super(success ? "Успешное подключение к серверу." : "Подключение к серверу недоступно в данный момент.");
        this.success = success;
    }

    public boolean isSuccess(){
        return success;
    }
}
