package server.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.abstractions.AbstractReceiver;
import common.abstractions.IOutputManager;
import common.commands.abstractions.AbstractCommand;
import common.commands.abstractions.Command;
import common.model.Movie;
import technical.exceptions.FileException;
import technical.exceptions.WrongArgumentException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Pattern;

import static technical.Utils.isInt;

public class ServerCommandsReceiver extends AbstractReceiver {
    public ServerCommandsReceiver(ServerCommandHandler.ShellValuables shell){
        super(shell);
    }

    @Override
    public void add(Object[] args) {
        shell.getCollectionManager().add((Movie) args[0]);
    }

    @Override
    public void save(Object[] args) {
        try {
            FileManager fm = shell.getFileManager();
            fm.writeToFile(shell.getCollectionManager().getCollection());
        } catch (JsonProcessingException e) {
            shell.getOutputManager().print("Не записать в файл:\n" + e.getMessage());
        }
    }

    @Override
    public void clear(Object[] args) {
        shell.getCollectionManager().clear();
        shell.getOutputManager().print("Коллекция очищена.");
    }

    @Override
    public void show(Object[] args) {
        shell.getOutputManager().print(shell.getCollectionManager().presentView());
    }

    // подумать
    @Override
    public void exit(Object[] args) {
        ;
    }

    // сделать красивый вывод
    @Override
    public void info(Object[] args) {
        IOutputManager output = shell.getOutputManager();
        Map<String, String> info = shell.getCollectionManager().getInfo();
        output.print("Информация о коллекции:");

        for(String key : info.keySet()){
            output.print("\t" + key + " - " + info.get(key));
        }
    }

    // изменить
    @Override
    public void executeScript(Object[] args) {
        super.executeScript(args);
    }

    // изменить на stream
    @Override
    public void filterByGoldenPalmCount(Object[] args) {
//        if (!isInt(args[0])){
////            shell.getOutputManager().print("Некорректные аргументы.");
//            throw new WrongArgumentException("filter_by_golden_palm_count");
//        }
//        Integer gp_count = Integer.parseInt(args[0]);
//
//        Vector<Movie> collection = shell.getCollectionManager().getCollection();
//
//        if (collection.isEmpty()){
//            shell.getOutputManager().print("Коллекция пуста.");
//            return;
//        }
//
//        for (Movie i : collection){
//            if (Objects.equals(i.getGoldenPalmCount(), gp_count)){
//                shell.getOutputManager().print(i.toString());
//            }
//        }
    }

    // подумать как сделать и изменить на красивый вывод
    @Override
    public void help(Object[] args) {
//        IOutputManager output = shell.getOutputManager();
//
//        output.print("Список доступных команд.");
//        for (String name : commandsList.keySet()) {
//            String temp = name;
//            if (!commandsList.get(name).getRequiringArguments().equals("no")) temp += " " + commandsList.get(name).getRequiringArguments();
//            output.print(String.format("\t%s: \t%s", temp, commandsList.get(name).getDescription()));
//        }
    }

    // подумать
    @Override
    public void history(Object[] args) {
        IOutputManager output = shell.getOutputManager();

        output.print("[");
        for(Command i : shell.getHistoryManager().getHistory()){
            output.print("\t" + i.getName());
        }
        output.print("]");
    }

    // изменить на stream
    @Override
    public void minByCoordinates(Object[] args) {
        Vector<Movie> collection = shell.getCollectionManager().getCollection();

        if (collection.isEmpty()){
            shell.getOutputManager().print("Коллекция пуста.");
            return;
        }

        Movie min = null;
        for (Movie i : collection){
            if (min == null || min.getCoordinates().compareTo(i.getCoordinates()) > 0){
                min = i;
            }
        }
        shell.getOutputManager().print(min.toString());
    }

    // изменить на stream
    @Override
    public void removeAllByGoldenPalmCount(Object[] args) {
        if (!isInt((String) args[0])){
//            shell.getOutputManager().print("Некорректные аргументы.");
            throw new WrongArgumentException("remove_all_by_golden_palm_count");
        }
        Integer gp_count = Integer.parseInt((String) args[0]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();
        for (Movie i : collection){
            if (Objects.equals(i.getGoldenPalmCount(), gp_count)){
                shell.getCollectionManager().remove(i);
            }
        }

        shell.getOutputManager().print("Элементы с количеством золотых пальмовых ветвей = " + gp_count + " удалены.");
    }

    // изменить на stream
    @Override
    public void removeById(Object[] args) {
        if (!isInt((String) args[0])){
//            shell.getOutputManager().print("Некорректные аргументы.");
            throw new WrongArgumentException("remove_by_id");
        }
        int id = Integer.parseInt((String) args[0]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();
        for (int i = 0; i < collection.size(); i++){
            if (collection.get(i).getId() == id){
                shell.getCollectionManager().remove(i);
                shell.getOutputManager().print("Элемент c id=" + id + "удален.");
                return;
            }
        }
        shell.getOutputManager().print("В коллекции нет элемента с id=" + id + ".");
    }

    @Override
    public void removeFirst(Object[] args) {
        if (shell.getCollectionManager().getCollection().isEmpty()){
            shell.getOutputManager().print("Коллекция пуста.");
            return;
        }
        shell.getCollectionManager().removeFirst();
        shell.getOutputManager().print("Элемент удален.");
    }

    // изменить на stream
    @Override
    public void removeLower(Object[] args) {
        CollectionManager cm = shell.getCollectionManager();
        Vector<Movie> collection = cm.getCollection();

        if (collection.isEmpty()){
            shell.getOutputManager().print("Коллекция пуста.");
            return;
        }
        Movie elem = (Movie) args[0];
        for(Movie i : collection){
            if (i.compareTo(elem) < 0){
                cm.remove(i);
                shell.getOutputManager().print("Удален элемент {" + i + "}.");
            }
        }
    }

    @Override
    public void update(Object[] args) {
        if (!isInt((String) args[0])){
//            shell.getOutputManager().print("Некорректные аргументы.");
            throw new WrongArgumentException("update");
        }
        int id = Integer.parseInt((String) args[0]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();
        for (Movie i : collection){
            if (i.getId() == id){
                i.update((Movie) args[1]);
                shell.getOutputManager().print("Элемент c id=" + id + " обновлён.");
                return;
            }
        }
        shell.getOutputManager().print("В коллекции нет элемента с id=" + id + ".");
    }
}
