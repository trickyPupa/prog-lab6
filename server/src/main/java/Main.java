
import server.managers.CollectionManager;
import server.managers.ServerCommandHandler;
import server.managers.FileManager;
import technical.exceptions.*;
import common.abstractions.Handler;
import common.abstractions.IInputManager;
import common.abstractions.IOutputManager;

import java.io.*;


public class Main {
    public static void main(String[] args) {
//        commandExecuting(args);
    }

//    public static void commandExecuting(String[] args){
//        String filename = args[0];
//
////        InputStream input = new BufferedInputStream(System.in);
//
//        try(InputStream input = new BufferedInputStream(System.in)){
//            IInputManager inputManager = new InputManager(input);
//            IOutputManager outputManager = new OutputManager();
//            FileManager fileManager = new FileManager(filename);
//            // CollectionManager collectionManager = new CollectionManager();
//            CollectionManager collectionManager = new CollectionManager(fileManager.collectionFromFile());
//
//            Handler handler = new ServerCommandHandler(inputManager, outputManager, collectionManager, fileManager);
//
//            while (true){
//                try {
//                    handler.nextCommand();
//                } catch (WrongArgumentException e){
//                    outputManager.print(e.toString());
//                } catch (InterruptException e){
//                    outputManager.print("Ввод данных остановлен.");
//                } catch (NoSuchCommandException e){
//                    outputManager.print("Нет такой команды в доступных.");
////                    outputManager.print(e.getMessage());
//                } catch (RecursionException e) {
//                    outputManager.print("Рекурсия в исполняемом файле.");
//                } catch (RuntimeException e){
//                    outputManager.print(e.getMessage());
//                    System.out.println("main catch runtime");
//                }
//            }
//        }
//        catch (FileException e){
//            System.out.println("Ошибка с файлом.");
//            System.out.println(e.getMessage());
//        }
//        catch(IOException e){
//            System.out.println("Ошибка при чтении данных");
//            System.out.println(e.getMessage());
//            System.out.println("main catch io");
//            throw new RuntimeException(e);
//        }
//        catch(Exception e){
//            System.out.println("Что-то пошло не так в ходе выполнения программы.");
//            System.out.println(e.getMessage());
//        }
//    }
}