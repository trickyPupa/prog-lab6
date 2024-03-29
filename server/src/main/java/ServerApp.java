import common.OutputManager;
import common.Utils;
import common.abstractions.IOutputManager;
import data_transfer.ConnectionRequest;
import data_transfer.Serializer;
import managers.*;
import common.exceptions.InterruptException;
import common.exceptions.NoSuchCommandException;
import common.exceptions.RecursionException;
import common.exceptions.WrongArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ContainerAdapter;
import java.net.SocketException;
import java.util.Arrays;

public class ServerApp {
    public static int PORT = 1783;
    public static Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        start("C:\\Users\\timof\\IdeaProjects\\prog-lab6\\server\\data\\data.json");
//        test();
    }

    public static void test(){
        var a = Serializer.prepareData(new ConnectionRequest());
        System.out.println(Arrays.toString(a));

        byte[] c = Utils.concatBytes(a, new byte[] {0,0,0,0,0,0,0,0,0,0});
        System.out.println(Arrays.toString(c));

        var b = Serializer.deserializeData(c);
        System.out.println(b);
        System.out.println(((ConnectionRequest) b).getContent());
    }
    
    private static void start(String filename){
        IOutputManager outputManager = new ServerOutputManager();
        FileManager fileManager = new FileManager(filename);
        CollectionManager collectionManager = new CollectionManager(fileManager.collectionFromFile());

        ServerCommandHandler handler = new ServerCommandHandler((ServerOutputManager) outputManager, collectionManager,
                fileManager, logger);

        ServerConnectionManager serverConnectionManager = null;
        while (serverConnectionManager == null) {
            try {
                serverConnectionManager = new ServerConnectionManager(PORT, handler);
                logger.info("Начало работы сервера");
            } catch (SocketException e) {
                logger.error("Невозможно подключиться к порту " + PORT);
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
            }
//            catch (RuntimeException e){
//                outputManager.print(e);
//                System.out.println(e);
//                System.out.println("main catch runtime");
//            }
        }
    }
}
