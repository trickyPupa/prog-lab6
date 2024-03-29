package managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.abstractions.AbstractReceiver;import common.Utils;
import common.abstractions.IOutputManager;
import common.commands.abstractions.AbstractCommand;
import common.commands.abstractions.Command;
import common.model.entities.Movie;
import common.exceptions.WrongArgumentException;
import exceptions.FinishConnecton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

import static common.Utils.isInt;

public class ServerCommandReceiver extends AbstractReceiver {
    private ServerCommandHandler.ShellValuables shell;

    public ServerCommandReceiver(ServerCommandHandler.ShellValuables shell){
        super(null, shell.getServerOutputManager());
        this.shell = shell;
    }

    @Override
    public void add(Object[] args) {
        shell.getCollectionManager().add((Movie) args[1]);
    }

    @Override
    public void save(Object[] args) {
        try {
            FileManager fm = shell.getFileManager();
            fm.writeToFile(shell.getCollectionManager().getCollection());
        } catch (JsonProcessingException e) {
            shell.getServerOutputManager().print("Не записать в файл:\n" + e.getMessage());
        }
    }

    @Override
    public void clear(Object[] args) {
        shell.getCollectionManager().clear();
        shell.getServerOutputManager().print("Коллекция очищена.");
    }

    @Override
    public void show(Object[] args) {
        shell.getServerOutputManager().print(shell.getCollectionManager().presentView());
    }

    // подумать
    @Override
    public void exit(Object[] args) {
        throw new FinishConnecton();
    }

    // сделать красивый вывод
    @Override
    public void info(Object[] args) {
        IOutputManager output = shell.getServerOutputManager();
        Map<String, String> info = shell.getCollectionManager().getInfo();
        output.print("Информация о коллекции:");

        for(String key : info.keySet()){
            output.print("\t" + key + " - " + info.get(key));
        }
    }

    // хорошо подумать
    @Override
    public void executeScript(Object[] args) {
        super.executeScript(args);
    }

    private int checkRecursion(Path path, ArrayDeque<Path> stack, int j) throws IOException {
        int i = 0;

        if (stack.contains(path)) return j;
        stack.addLast(path);
        String str = Files.readString(path);

        Pattern pattern = Pattern.compile("execute_script .*");
        var patternMatcher = pattern.matcher(str);
        while (patternMatcher.find())
        {
            i++;
            Path newPath = Path.of(patternMatcher.group().split(" ")[1]);
//            if(checkRecursion(newPath, stack, i) != 0) return i;
            int a = checkRecursion(newPath, stack, i);
            if (a != 0) return a + j;
        }
        stack.removeLast();
        return 0;
    }

    // проверить
    @Override
    public void filterByGoldenPalmCount(Object[] args) {
        // проверка аргументов
        Integer gp_count = Integer.parseInt((String) args[0]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();

        if (collection.isEmpty()){
            shell.getServerOutputManager().print("Коллекция пуста.");
            return;
        }
        shell.getServerOutputManager().print(collection.stream().filter((x) -> Objects.equals(x.getGoldenPalmCount(), gp_count)));
    }

    // подумать как сделать и изменить на красивый вывод
    @Override
    public void help(Object[] args) {
        IOutputManager output = shell.getServerOutputManager();
        var commandsList = shell.commands;

        output.print("Список доступных команд.");
        for (String name : commandsList.keySet()) {
            String temp = String.format("%15s", name);
            AbstractCommand curCmd = (AbstractCommand) commandsList.get(name).apply(null);

            if (!curCmd.getRequiringArguments().equals("no")) temp += " " + curCmd.getRequiringArguments();

            output.print(String.format("\t%s: \t%s", temp, curCmd.getDescription()));
        }
    }

    // подумать
    @Override
    public void history(Object[] args) {
        IOutputManager output = shell.getServerOutputManager();

        output.print("[");
        for(Command i : shell.getHistoryManager().getHistory()){
            output.print("\t" + i.getName());
        }
        output.print("]");
    }

    // проверить
    @Override
    public void minByCoordinates(Object[] args) {
        Vector<Movie> collection = shell.getCollectionManager().getCollection();

        if (collection.isEmpty()){
            shell.getServerOutputManager().print("Коллекция пуста.");
            return;
        }

        shell.getServerOutputManager().print(collection.stream().min(Comparator.comparing(Movie::getCoordinates)).toString());
    }

    // проверить
    @Override
    public void removeAllByGoldenPalmCount(Object[] args) {
//        if (!isInt((String) args[0])){
//            throw new WrongArgumentException("remove_all_by_golden_palm_count");
//        }

        Integer gp_count = Integer.parseInt((String) args[0]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();

//        for (Movie i : collection){
//            if (Objects.equals(i.getGoldenPalmCount(), gp_count)){
//                shell.getCollectionManager().remove(i);
//            }
//        }
        collection.stream()
                .filter((x) -> Objects.equals(x.getGoldenPalmCount(), gp_count))
                .forEach((x) -> shell.getCollectionManager().remove(x));

        shell.getServerOutputManager().print("Элементы с количеством золотых пальмовых ветвей = " + gp_count + " удалены.");
    }

    // проверить
    @Override
    public void removeById(Object[] args) {
//        if (!isInt((String) args[0])){
//            throw new WrongArgumentException("remove_by_id");
//        }

        int id = Integer.parseInt((String) args[0]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();

        var stream = collection.stream()
                .filter((x) -> x.getId() == id);

        if (stream.findAny().isEmpty()){
            shell.getServerOutputManager().print("В коллекции нет элемента с id=" + id + ".");
        } else {
            stream.forEach((x) -> {
                shell.getCollectionManager().remove(x);
                shell.getServerOutputManager().print("Элемент c id=" + id + "удален.");
            });
        }
    }

    @Override
    public void removeFirst(Object[] args) {
        if (shell.getCollectionManager().getCollection().isEmpty()){
            shell.getServerOutputManager().print("Коллекция пуста.");
            return;
        }
        shell.getCollectionManager().removeFirst();
        shell.getServerOutputManager().print("Элемент удален.");
    }

    // проверить
    @Override
    public void removeLower(Object[] args) {
        CollectionManager cm = shell.getCollectionManager();
        Vector<Movie> collection = cm.getCollection();

        if (collection.isEmpty()){
            shell.getServerOutputManager().print("Коллекция пуста.");
            return;
        }

        Movie elem = (Movie) args[1];

        collection.stream()
                .filter((x) -> x.compareTo(elem) < 0)
                .forEach((x) -> {
                    cm.remove(x);
                    shell.getServerOutputManager().print(String.format("Удален элемент {%100s}.", x));
                });
    }

    @Override
    public void update(Object[] args) {
        if (!isInt((String) args[1])){
//            shell.getOutputManager().print("Некорректные аргументы.");
            throw new WrongArgumentException("update");
        }
        int id = Integer.parseInt((String) args[1]);

        Vector<Movie> collection = shell.getCollectionManager().getCollection();
        for (Movie i : collection){
            if (i.getId() == id){
                i.update((Movie) args[2]);
                shell.getServerOutputManager().print("Элемент c id=" + id + " обновлён.");
                return;
            }
        }
        shell.getServerOutputManager().print("В коллекции нет элемента с id=" + id + ".");
    }
}
