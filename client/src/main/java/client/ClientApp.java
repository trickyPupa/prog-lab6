package client;

import common.abstractions.AbstractReceiver;
import common.exceptions.*;
import common.abstractions.Handler;
import common.abstractions.IInputManager;
import common.abstractions.IOutputManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ClientApp {
    public static int PORT = 7783;
    public static String HOST_NAME = "";

    public static void main(String[] args) {
        var s = Serializer.prepareData("fdslnf;ldsa");
        System.out.println(Arrays.toString(s));

//        System.out.println(Serializer.deserializeData(s));
    }

    public static void start(){
        try(InputStream input = new BufferedInputStream(System.in)){

            IInputManager inputManager = new InputManager(input);
            IOutputManager outputManager = new OutputManager();
            AbstractReceiver receiver = new ClientReciver();

            AbstractClientRequestManager clientRequestManager = new ClientRequestManager(HOST_NAME, PORT);

            Handler handler = new ClientCommandHandler(inputManager, outputManager, clientRequestManager, receiver);

            while (true){
                try {
                    handler.nextCommand();

                    // отправка серверу
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
        catch (UnknownHostException e){
            ;
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
