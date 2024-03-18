package client;

import data_transfer.CommandRequest;
import data_transfer.CommandResponse;
import exceptions.ConnectionsFallsExcetion;
import common.commands.abstractions.Command;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class ClientRequestManager extends AbstractClientRequestManager {
    private DatagramSocket socket;
    private SocketAddress socketAddress;
    private DatagramChannel channel;

    public ClientRequestManager(String serverName, int port) throws UnknownHostException {
        this(InetAddress.getByName(serverName), port);
    }

    public ClientRequestManager(byte[] ip, int port) throws UnknownHostException {
        this(InetAddress.getByAddress(ip), port);
    }

    public ClientRequestManager(InetAddress address, int port){
        super(address, port);

        try {
            socket = new DatagramSocket();
            socketAddress = new InetSocketAddress(address, port);
            socket.connect(socketAddress);

            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.connect(socketAddress);

        } catch (SocketException e) {
            throw new ConnectionsFallsExcetion(e.getMessage());
        } catch (IOException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        } catch (RuntimeException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void makeRequest(Command c){
        CommandRequest cr  = new CommandRequest(c);
        byte[] preparedData = Serializer.prepareData(cr);
        ByteBuffer buf = ByteBuffer.allocate(preparedData.length);
        buf.put(preparedData);

        try {
            channel.write(buf);
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getResponse(){
        ByteBuffer buf = ByteBuffer.allocate(1000);

        try {
            channel.receive(buf);
            CommandResponse cr = (CommandResponse) Serializer.deserializeData(buf.array());
            return cr.getMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makeRequest(Command c, InetAddress addr, int port){
        ;
    }

    public static String getResponse(InetAddress addr, int port){
        return "";
    }
}
