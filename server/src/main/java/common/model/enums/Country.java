package common.model.enums;

public enum Country {
    FRANCE,
    INDIA,
    VATICAN,
    THAILAND;

    public static boolean contains(String a){
        for(Country s : values()){
            if (s.toString().equals(a)){
                return true;
            }
        }
        return false;
    }
}
