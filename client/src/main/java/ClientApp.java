import client.*;
import common.OutputManager;
import common.abstractions.*;
import common.exceptions.*;
import data_transfer.ConnectionRequest;
import data_transfer.ConnectionResponse;
import data_transfer.Serializer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ClientApp {
    public static int PORT = 1783;
    public static String HOST_NAME = "localhost";

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        try(InputStream input = new BufferedInputStream(System.in)){

            IInputManager inputManager = new InputManager(input);
            IOutputManager outputManager = new OutputManager();
            AbstractReceiver receiver = new ClientReceiver(inputManager, outputManager);
            DataInputReceiver diReceiver = null;

            AbstractClientRequestManager clientRequestManager = new ClientRequestManager(InetAddress.getLocalHost(), PORT);

            ClientCommandHandler handler = new ClientCommandHandler(inputManager, outputManager, clientRequestManager,
                    receiver, diReceiver);


            clientRequestManager.makeRequest(new ConnectionRequest());
            var answer = clientRequestManager.getResponse();
            outputManager.print(answer.getMessage());

            if (!((ConnectionResponse) answer).isSuccess()){
                outputManager.print("Попробуйте позже. Завершение работы.");
                System.exit(0);
            }

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
                }
//                catch (RuntimeException e){
//                    outputManager.print(e.getMessage());
//                    System.out.println("main catch runtime");
//                }
            }
        }
        catch (UnknownHostException e){
            throw new RuntimeException(e);
        }
        catch(IOException e){
            System.out.println("Ошибка при чтении данных");
            System.out.println(e.getMessage());
            System.out.println("main catch io");
            throw new RuntimeException(e);
        }
        catch(Exception e){
            System.out.println("Что-то пошло не так в ходе выполнения программы.");
//            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
