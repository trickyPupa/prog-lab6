package common.commands.implementations;

import common.Utils;
import common.abstractions.DataInputReceiver;
import common.commands.abstractions.AbstractCommand;
import common.abstractions.AbstractReceiver;import common.Utils;

public class AddCommand extends AbstractCommand{

    public AddCommand(Object[] args) {
        super("add", "Команда для добавления Movie в коллекцию.\n    Требуется ввести все" +
                "характеристики фильма:\n - Название (строка), количество \"Оскаров\" (целое число), количество " +
                "золотых пальмовых ветвей (целое число или пустая строка), продолжительность фильма " +
                "(целое число)\n - Координаты (2 целых числа: первое больше -879, второе  не больше 155)\n - MPAA" +
                " рейтинг фильма (одна из строк: PG, PG_13, NC_17)\n - Режиссер фильма: имя, дата " +
                "рождения в формате ДД.ММ.ГГГГ, цвет глаз (одна из строк: BLUE, YELLOW, ORANGE, WHITE, " +
                "BROWN.), на отдельной строке цвет волос (одна " +
                "из строк: GREEN, RED, BLUE, YELLOW, ORANGE), национальность (одна из " +
                "доступных стран: FRANCE, INDIA, VATICAN, THAILAND), местонахождение (3 " +
                "числа-координаты: дробное, целое, целое)", "{element}", args);
    }

    @Override
    public void execute(String[] s, AbstractReceiver r) {
//        Movie element = Movie.createMovie1(shell.getInputManager(), shell.getOutputManager());
//
//        try {
//            shell.getCollectionManager().add(element);
//        } catch (WrongArgumentException e){
//            throw new WrongArgumentException(getName());
//        }

        r.add(s);
    }

    @Override
    public void execute(AbstractReceiver rec) {
        rec.add(Utils.concatObjects(new Object[] {this}, getArgs()));
    }

}
