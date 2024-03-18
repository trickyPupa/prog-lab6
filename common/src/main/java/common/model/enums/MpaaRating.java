package common.model.enums;

public enum MpaaRating {
    PG,
    PG_13,
    NC_17;

    public static boolean contains(String a){
        for(MpaaRating s : values()){
            if (s.toString().equals(a)){
                return true;
            }
        }
        return false;
    }
}
