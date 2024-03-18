package common.model.enums;

public enum EyeColor {
    BLUE,
    YELLOW,
    ORANGE,
    WHITE,
    BROWN;

    public static boolean contains(String a){
        for(EyeColor s : values()){
            if (s.toString().equals(a)){
                return true;
            }
        }
        return false;
    }
}
