package managers;

import common.abstractions.AbstractCollectionManager;
import common.model.entities.Movie;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Класс управляет основной коллекцией; данная реализация работает с {@link Vector}.
 */
public class CollectionManager extends AbstractCollectionManager<Movie> {
    private Vector<Movie> collection;
    private final LocalDateTime creationDate;

    public CollectionManager(){
        collection = new Vector<>();
        creationDate = LocalDateTime.now();
    }

    public CollectionManager(Vector<Movie> vec){
        collection = new Vector<>(vec);
        creationDate = LocalDateTime.now();
    }

    @Override
    public void add(Movie element){
        collection.add(element);
    }

    @Override
    public Movie remove(int i){
        return collection.remove(i);
    }

    @Override
    public Movie remove(Movie i){
        if (collection.remove(i)) return i;
        return null;
    }

    @Override
    public Movie removeFirst(){
        return collection.remove(0);
    }

    @Override
    public String presentView(){
        if (collection.isEmpty()) return "Коллекция пуста";
        String res = "Текущая коллекция фильмов:\n";

        for (Movie i : collection){
            res = res + " - " + i.toString() + "\n";
        }

        return res;
    }

    /**
     * @return сводка информации о коллекции: длина, тип коллекции, дата инициализации, хэш-код.
     */
    @Override
    public Map<String, String> getInfo(){
        Map<String, String> ans = new LinkedHashMap<>();
        ans.put("длина", String.valueOf(collection.size()));
        ans.put("тип коллекции", collection.getClass().getName());
        ans.put("дата инициализации", creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        ans.put("хэш-код", String.valueOf(this.hashCode()));

        return ans;
    }

    /**
     * Очищает коллекцию
     */
    @Override
    public void clear(){
        collection.clear();
    }

    public Vector<Movie> getCollection(){
        return new Vector<>(collection);
    }

    @Override
    public String toString() {
        return "collection=" + collection +
                '}';
    }
}
