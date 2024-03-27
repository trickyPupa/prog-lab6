package data_transfer;

import java.io.Serializable;

public abstract class Request implements Serializable {
    public abstract Object getContent();
}
