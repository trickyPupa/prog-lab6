package managers;

import common.commands.abstractions.Command;
import data_transfer.CommandRequest;
import data_transfer.CommandResponse;
import data_transfer.Serializer;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class ServerConnectionManager {
    private final int PACKET_SIZE = 1024;

    protected final int port;
    private InetSocketAddress localSocketAddress;
    private SocketAddress curClient = null;
    private DatagramSocket socket;
    private ServerCommandHandler handler;

    public ServerConnectionManager(int p, ServerCommandHandler h) throws SocketException {
        handler = h;

        port = p;
        localSocketAddress = new InetSocketAddress(port);
        socket = new DatagramSocket(localSocketAddress);

        System.out.println("Старт сервера, порт: " + port);
    }

    protected Command getNextRequest() throws IOException {
        boolean received = false;

        byte[] buf = new byte[PACKET_SIZE];
        DatagramPacket dp = new DatagramPacket(buf, PACKET_SIZE);

        while(!received) {
            socket.receive(dp);

            // logging

            if (buf[buf.length - 1] == 1){
                received = true;
            }
        }
        if (curClient == null){
            curClient = dp.getSocketAddress();
        }

        CommandRequest cr = (CommandRequest) Serializer.deserializeData(buf);
        return cr.getCommand();
    }

    protected void sendResponse(String response){
        CommandResponse cr = new CommandResponse(response);
        byte[] buf = Serializer.prepareData(cr);

        DatagramPacket dp = new DatagramPacket(buf, buf.length, curClient);
        try {
            socket.send(dp);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    protected void workoutConnection(){
        ;
    }

    public void run(){
        while (true){
            try {
                getNextRequest()
            } catch (IOException e){
                ;
            }
        }
    }
}
