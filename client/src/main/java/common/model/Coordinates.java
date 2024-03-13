package common.model;

import common.abstractions.IInputManager;
import common.abstractions.IOutputManager;
import technical.exceptions.InterruptException;

import java.io.IOException;

import static java.lang.Math.sqrt;
import static technical.Utils.isInt;
import static technical.Utils.isLong;

public class Coordinates implements Checkable, Comparable<Coordinates> {
    private int x;  // > -879
    private long y;  // <= 155

    public Coordinates(int a, long b){
        x = a;
        y = b;
    }
    private Coordinates(){}

    public void setX(int x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    public static Coordinates createCoords(IInputManager input, IOutputManager output){
        Coordinates elem = new Coordinates();

        try{
            while(true) {
                output.print("Введиите координату X фильма (целое число больше -879): ");
                String line = input.nextLine();
                if (line == null || line.equals("exit")){
                    throw new InterruptException();
                }
                if (isInt(line) && Integer.parseInt(line) > -879){
                    elem.setX(Integer.parseInt(line));
                    break;
                }
                output.print("Некорректные данные.");
            }

            while(true) {
                output.print("Введиите координату Y фильма (целое число не больше 155): ");
                String line = input.nextLine();
                if (line == null || line.equals("exit")){
                    throw new InterruptException();
                }
                if (isLong(line) && Long.parseLong(line) <= 155){
                    elem.setY(Long.parseLong(line));
                    break;
                }
                output.print("Некорректные данные.");
            }

        } catch (IOException e){
            output.print(e.getMessage());
        }

        return elem;
    }

    @Override
    public boolean checkItself(){
        return x > -879 && y <= 155;
    }

    @Override
    public int compareTo(Coordinates o) {
        double dif = sqrt((long) this.x * this.x + this.y * this.y) - sqrt((long) o.x * o.x + o.y * o.y);
        if (dif == 0){
            return 0;
        } else if (dif < 0){
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
