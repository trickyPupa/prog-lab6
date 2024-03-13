package server.managers;

import common.abstractions.IOutputManager;

public class ServerOutputManager implements IOutputManager {
    private String response = "";

    @Override
    public void print(String s) {
        response += s + "\n";
    }

    public String getResponse(){
        return response;
    }
}
