import common.abstractions.IOutputManager;
import managers.*;
import common.exceptions.InterruptException;
import common.exceptions.NoSuchCommandException;
import common.exceptions.RecursionException;
import common.exceptions.WrongArgumentException;

public class ServerApp {
    public static void main(String[] args) {
        ;
    }
    private static void start(String filename){
        ClientsManager clientsManager = new ClientsManager();
        IOutputManager outputManager = new ServerOutputManager();
        FileManager fileManager = new FileManager(filename);
        CollectionManager collectionManager = new CollectionManager(fileManager.collectionFromFile());

        ServerCommandHandler handler = new ServerCommandHandler(outputManager, collectionManager,
                fileManager, clientsManager);

        while (true){
            try {
                handler.nextCommand();
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
