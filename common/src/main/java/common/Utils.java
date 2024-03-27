package common;

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

    public static byte[] concatBytes(byte[] a, byte[] b){
        int l = a.length + b.length;
        byte[] res = new byte[l];

        for (int i = 0; i < l;i++){
            res[i] = i < a.length ? a[i] : b[i - a.length];
        }
        return res;
    }
}
