package common.model.enums;

public enum HairColor {
    GREEN,
    RED,
    BLUE,
    YELLOW,
    ORANGE;

    public static boolean contains(String a){
        for(HairColor s : values()){
            if (s.toString().equals(a)){
                return true;
            }
        }
        return false;
    }
}
