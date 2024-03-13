package client;

import client.exceptions.*;
import common.abstractions.Handler;
import common.abstractions.IInputManager;
import common.abstractions.IOutputManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientApp {

    public static void main(String[] args) {
        ;
    }

    public static void start(){
        try(InputStream input = new BufferedInputStream(System.in)){

            IInputManager inputManager = new InputManager(input);
            IOutputManager outputManager = new OutputManager();
            AbstractServerRequestManager serverRequestManager = new ServerRequestManager();

            Handler handler = new ClientCommandHandler(inputManager, outputManager, serverRequestManager);

            while (true){
                try {
                    handler.nextCommand();

                    //dfs
                } catch (WrongArgumentException e){
                    outputManager.print(e.toString());
                } catch (InterruptException e){
                    outputManager.print("Ввод данных остановлен.");
                } catch (NoSuchCommandException e){
                    outputManager.print("Нет такой команды в доступных.");
                } catch (RecursionException e) {
                    outputManager.print("Рекурсия в исполняемом файле.");
                } catch (RuntimeException e){
                    outputManager.print(e.getMessage());
                    System.out.println("main catch runtime");
                }
            }
        }
        catch(IOException e){
            System.out.println("Ошибка при чтении данных");
            System.out.println(e.getMessage());
            System.out.println("main catch io");
            throw new RuntimeException(e);
        }
        catch(Exception e){
            System.out.println("Что-то пошло не так в ходе выполнения программы.");
            System.out.println(e.getMessage());
        }
    }
}
