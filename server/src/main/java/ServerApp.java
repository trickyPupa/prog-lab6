import common.OutputManager;
import common.abstractions.IOutputManager;
import managers.*;
import common.exceptions.InterruptException;
import common.exceptions.NoSuchCommandException;
import common.exceptions.RecursionException;
import common.exceptions.WrongArgumentException;

import java.net.SocketException;

public class ServerApp {
    public static int PORT = 7783;

    public static void main(String[] args) {
        ;
    }
    private static void start(String filename){
        IOutputManager outputManager = new ServerOutputManager();
        IOutputManager consoleOutput = new OutputManager();
        FileManager fileManager = new FileManager(filename);
        CollectionManager collectionManager = new CollectionManager(fileManager.collectionFromFile());

        ServerCommandHandler handler = new ServerCommandHandler(outputManager, consoleOutput, collectionManager, fileManager);

        ServerConnectionManager serverConnectionManager = null;
        while (serverConnectionManager == null) {
            try {
                serverConnectionManager = new ServerConnectionManager(PORT, handler);
            } catch (SocketException e) {
                outputManager.print("Невозможно подключиться к порту " + PORT);
                PORT++;
            }
        }



        while (true){
            try {
                serverConnectionManager.run();
            } catch (WrongArgumentException e){
                outputManager.print(e.toString());
            } catch (InterruptException e){
                outputManager.print("Ввод данных остановлен.");
            } catch (NoSuchCommandException e){
                outputManager.print("Нет такой команды в доступных.");
//                    outputManager.print(e.getMessage());
            } catch (RecursionException e) {
                outputManager.print("Рекурсия в исполняемом файле.");
            } catch (RuntimeException e){
                outputManager.print(e.getMessage());
                System.out.println("main catch runtime");
            }
        }
    }
}
