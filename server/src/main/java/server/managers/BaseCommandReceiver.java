package server.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.model.Movie;
import common.commands.abstractions.AbstractCommand;
import common.commands.abstractions.Command;
import technical.exceptions.FileException;
import technical.exceptions.WrongArgumentException;
import common.abstractions.AbstractCommandHandler;
import common.abstractions.AbstractReceiver;
import common.abstractions.IOutputManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Pattern;

import static technical.Utils.isInt;

public class BaseCommandReceiver extends AbstractReceiver {
    public BaseCommandReceiver(ServerCommandHandler.ShellValuables shell){
        super(shell);
    }

    // изменить
//    @Override
//    public void add(String[] args){
//        Movie element = Movie.createMovie1(shell.getInputManager(), shell.getOutputManager());
//        shell.getCollectionManager().add(element);
//    }

    @Override
    public void save(String[] args) {
        try {
            FileManager fm = shell.getFileManager();
            fm.writeToFile(shell.getCollectionManager().getCollection());
        } catch (JsonProcessingException e) {
            shell.getOutputManager().print("Не записать в файл:\n" + e.getMessage());
        }
    }

    @Override
    public void clear(String[] args) {
        shell.getCollectionManager().clear();
        shell.getOutputManager().print("Коллекция очищена.");
    }

    @Override
    public void show(String[] args) {
        shell.getOutputManager().print(shell.getCollectionManager().presentView());
    }

    // изменить
    @Override
    public void exit(String[] args) {
        System.exit(0);
    }

    @Override
    public void info(String[] args) {
        IOutputManager output = shell.getOutputManager();
        Map<String, String> info = shell.getCollectionManager().getInfo();
        output.print("Информация о коллекции:");

        for(String key : info.keySet()){
            output.print("\t" + key + " - " + info.get(key));
        }
    }

    // изменить
//    @Override
//    public void executeScript(String[] args) {
//        if (args[0].isBlank()) {
////            shell.getOutputManager().print("Некорректные аргументы.");
//            throw new WrongArgumentException("execute");
//        }
//
//        if (!args[0].endsWith(".txt")) {
//            throw new FileException("Указан файл недопустимого формата.");
//        }
//
//        File file = new File(args[0]);
//        if (!file.exists() || !file.isFile()){
//            throw new FileException("Нет файла с указанным именем");
//        } else if (!file.canRead()){
//            throw new FileException("Файл недоступен для чтения.");
//        }
//
//        StringBuilder writer = new StringBuilder();
//
//        try {
//            BufferedReader bufReader = new BufferedReader(new FileReader(file));
//
////            if (checkRecursion(Path.of(args[0]), new ArrayDeque<>())) {
////                shell.getOutputManager().print("При анализе скрипта обнаружена бесконечная рекурсия. Устраните ее перед исполнением.");
////                return;
////            }
//
//            String temp;
//            while ((temp = bufReader.readLine()) != null){
//                if (temp.strip().startsWith("execute_script")){ // && temp.strip().substring(14).strip().startsWith(args[0])
////                    throw new RecursionException();
//                    shell.getOutputManager().print("Рекурсия внутри файла не будет выполнена.");
//                    break;
//                } else writer.append("\n").append(temp);
//            }
//
//            CharArrayReader car = new CharArrayReader(writer.toString().toCharArray());
//
//            shell.getOutputManager().print("Начало исполнения файла {" + file.getPath() + "}.");
//            shell.getInputManager().setTemporaryInput(new BufferedReader(car));
//
//        } catch (FileNotFoundException e) {
//            throw new FileException("Нет файла с указанным именем");
//        } catch (IOException e){
//            shell.getOutputManager().print("Ошибка при чтении данных.");
//            shell.getOutputManager().print(e.getMessage());
//        }
//    }

    private boolean checkRecursion(Path path, ArrayDeque<Path> stack) throws IOException {
        if (stack.contains(path)) return true;
        stack.addLast(path);
        String str = Files.readString(path);

        Pattern pattern = Pattern.compile("execute_script .*");
        java.util.regex.Matcher patternMatcher = pattern.matcher(str);
        while (patternMatcher.find())
        {
            Path newPath = Path.of(patternMatcher.group().split(" ")[1]);
            if(checkRecursion(newPath, stack)) return true;
        }
        stack.removeLast();
        return false;
    }

    // изменить
    @Override
    public void filterByGoldenPalmCount(String[] args) {
        if (!isInt(args[0])){
//            shell.getOutputManager().print("Некорректные аргументы.");
            throw new WrongArgumentException("filter_by_golden_palm_count");
        }
        Integer gp_count = Integer.parseInt(args[0]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();

        if (collection.isEmpty()){
            shell.getOutputManager().print("Коллекция пуста.");
            return;
        }

        for (Movie i : collection){
            if (Objects.equals(i.getGoldenPalmCount(), gp_count)){
                shell.getOutputManager().print(i.toString());
            }
        }
    }

    // изменить
//    @Override
//    public void help(String[] args) {
//        Map<String, AbstractCommand> commandsList = shell.commands;
//
//        /*if (!s[0].isBlank() && commandsList.containsKey(s[0].strip())){
//            System.out.println("Справка по команде: " + s[0].strip());
//            System.out.printf("\t%s: \t%s\n", s[0].strip(), commandsList.get(s[0].strip()).getDescription());
//        }
//        else {
//            System.out.println("Список доступных команд.");
//            for (String name : commandsList.keySet()) {
//                System.out.printf("\t%s: \t%s\n", name, commandsList.get(name).getDescription());
//            }
//        }*/
//        IOutputManager output = shell.getOutputManager();
//
//        output.print("Список доступных команд.");
//        for (String name : commandsList.keySet()) {
//            String temp = name;
//            if (!commandsList.get(name).getRequiringArguments().equals("no")) temp += " " + commandsList.get(name).getRequiringArguments();
//            output.print(String.format("\t%s: \t%s", temp, commandsList.get(name).getDescription()));
//        }
//    }

    @Override
    public void history(String[] args) {
        IOutputManager output = shell.getOutputManager();

        output.print("[");
        for(Command i : shell.getHistoryManager().getHistory()){
            output.print("\t" + i.getName());
        }
        output.print("]");
    }

    // изменить
    @Override
    public void minByCoordinates(String[] args) {
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

    // подумать
    @Override
    public void removeAllByGoldenPalmCount(String[] args) {
        if (!isInt(args[0])){
//            shell.getOutputManager().print("Некорректные аргументы.");
            throw new WrongArgumentException("remove_all_by_golden_palm_count");
        }
        Integer gp_count = Integer.parseInt(args[0]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();
        for (Movie i : collection){
            if (Objects.equals(i.getGoldenPalmCount(), gp_count)){
                shell.getCollectionManager().remove(i);
            }
        }

        shell.getOutputManager().print("Элементы с количеством золотых пальмовых ветвей = " + gp_count + " удалены.");
    }

    // подумать
    @Override
    public void removeById(String[] args) {
        if (!isInt(args[0])){
//            shell.getOutputManager().print("Некорректные аргументы.");
            throw new WrongArgumentException("remove_by_id");
        }
        int id = Integer.parseInt(args[0]);

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

    // подумать
    @Override
    public void removeFirst(String[] args) {
        if (shell.getCollectionManager().getCollection().isEmpty()){
            shell.getOutputManager().print("Коллекция пуста.");
            return;
        }
        shell.getCollectionManager().removeFirst();
        shell.getOutputManager().print("Элемент удален.");
    }

    // подумать и исправить
//    @Override
//    public void removeLower(String[] args) {
//        CollectionManager cm = shell.getCollectionManager();
//        Vector<Movie> collection = cm.getCollection();
//
//        if (collection.isEmpty()){
//            shell.getOutputManager().print("Коллекция пуста.");
//            return;
//        }
//        Movie elem = Movie.createMovie1(shell.getInputManager(), shell.getOutputManager());
//        for(Movie i : collection){
//            if (i.compareTo(elem) < 0){
//                cm.remove(i);
//                shell.getOutputManager().print("Удален элемент {" + i + "}.");
//            }
//        }
//    }

    // исправить
//    @Override
//    public void update(String[] args) {
//        if (!isInt(args[0])){
////            shell.getOutputManager().print("Некорректные аргументы.");
//            throw new WrongArgumentException("update");
//        }
//        int id = Integer.parseInt(args[0]);
//
//        Vector<Movie> collection = shell.getCollectionManager().getCollection();
//        for (Movie i : collection){
//            if (i.getId() == id){
//                i.update(Movie.createMovie1(shell.getInputManager(), shell.getOutputManager()));
//                shell.getOutputManager().print("Элемент c id=" + id + " обновлён.");
//                return;
//            }
//        }
//        shell.getOutputManager().print("В коллекции нет элемента с id=" + id + ".");
//    }
}
