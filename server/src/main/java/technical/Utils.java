package technical;

/**
 * Класс с полезными функциями.
 */
public class Utils {
    /**
     * @param s строка, которую требуется проверить
     * @return можно ли преобразовать строку в int
     */
    public static boolean isInt(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * @param s строка, которую требуется проверить
     * @return можно ли преобразовать строку в long
     */
    public static boolean isLong(String s){
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * @param s строка, которую требуется проверить
     * @return можно ли преобразовать строку в float
     */
    public static boolean isFloat(String s){
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
