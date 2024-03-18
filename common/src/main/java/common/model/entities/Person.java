package common.model.entities;

import common.model.enums.*;
import common.exceptions.InterruptException;
import common.abstractions.IInputManager;
import common.abstractions.IOutputManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Person implements Comparable<Person>, Checkable {
    private String name; //Поле не может быть null, Строка не может быть пустой
//    @JsonSerialize(using = FileManager.CustomDateSerializer.class)
    private Date birthday; //Поле не может быть null
    private EyeColor eyeColor; //Поле может быть null
    private HairColor hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле не может быть null

    protected Person() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEyeColor(EyeColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Deprecated
    public static Person createPerson(IInputManager input, IOutputManager output){
        Person elem = new Person();

        try{
            while (true) {
                try {
                    output.print("\nВведите имя режиссёра и дату его рождения (ДД.ММ.ГГГГ) через запятую: ");
                    String[] args = input.nextLine().split(",");

                    if (!args[0].isBlank()) {
                        elem.setName(args[0].strip());
                    } else {
                        output.print("Имя не может быть пустым.");
                        continue;
                    }
                    if (args[1].strip().matches("(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).(1[89]\\d\\d|20([01]\\d|2[01234]))")) {
                        String[] temp = args[1].split("\\.");

                        int day = Integer.parseInt(temp[0].strip());
                        int month = Integer.parseInt(temp[1].strip());
                        int year = Integer.parseInt(temp[2].strip());

                        elem.setBirthday(new Date(year, month, day));
                        break;
                    } else {
                        output.print("Неправильный формат даты.");
                    }
                } catch (RuntimeException e){
                    output.print(e.getMessage());
                    output.print("Неправильный формат аргументов");
                }
            }

            while (true) {
                output.print("\nЦвет глаз режиссёра - BLUE, YELLOW, ORANGE, WHITE или BROWN: ");
                String color = input.nextLine().strip();
                if (EyeColor.contains(color)){
                    elem.setEyeColor(EyeColor.valueOf(color));
                    break;
                } else{
                    output.print("Недопустимый цвет глаз.");
                }
            }

            while (true) {
                output.print("\nЦвет волос режиссёра - GREEN, RED, BLUE, YELLOW или ORANGE: ");
                String color = input.nextLine().strip();
                if (HairColor.contains(color)){
                    elem.setHairColor(HairColor.valueOf(color));
                    break;
                } else{
                    output.print("Недопустимый цвет глаз.");
                }
            }

            while (true) {
                output.print("\nСтрана рождения режиссёра - FRANCE, INDIA, VATICAN или THAILAND: ");
                String line = input.nextLine().strip();
                if (Country.contains(line)){
                    elem.setNationality(Country.valueOf(line));
                    break;
                } else{
                    output.print("Недопустимая страна.");
                }
            }

            while (true) {
                output.print("\nМестонахождение режиссёра - дробное число и два целых через запятую: ");
                try {
                    String[] line = input.nextLine().strip().replace(" ", "").split(",");

                    if (line[0].matches("-?\\d*.\\d*") && line[1].matches("-?\\d*")
                            && line[2].matches("-?\\d*")) {
                        elem.setLocation(new Location(Float.parseFloat(line[0]), Long.parseLong(line[1]),
                                Integer.parseInt(line[2])));
                        break;
                    } else {
                        output.print("Недопустимый формат.");
                    }
                } catch (RuntimeException e){
                    System.out.println(e.getMessage());
                    System.out.println("Неправильный формат аргументов.");
                }
            }
            return elem;
        } catch (IOException e){
            output.print("Что-то случилось, введите команду заново.");
            throw new InterruptException();
        }
    }

    public static Person createPerson1(IInputManager input, IOutputManager output){
        Person elem = new Person();

        Map<String, Predicate<String>> args_checkers = new LinkedHashMap<>();
        args_checkers.put("имя режиссёра", x -> {
            if (!x.isBlank()){
                elem.setName(x);
                return true;
            }
            return false;
        });
        args_checkers.put("дату рождения режиссёра", x -> {
            if (x.matches("(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(1[89]\\d\\d|20([01]\\d|2[01234]))")){

                String[] temp = x.split("\\.");

                int day = Integer.parseInt(temp[0].strip());
                int month = Integer.parseInt(temp[1].strip());
                int year = Integer.parseInt(temp[2].strip());

                elem.setBirthday(new Date(year - 1900, month, day));
                return true;
            }
            return false;
        });
        args_checkers.put("цвет глаз режиссёра (BLUE, YELLOW, ORANGE, WHITE, BROWN)", x -> {
            if (EyeColor.contains(x)){
                elem.setEyeColor(EyeColor.valueOf(x));
                return true;
            }
            return false;
        });
        args_checkers.put("цвет волос режиссёра (GREEN, RED, BLUE, YELLOW, ORANGE)", x -> {
            if (HairColor.contains(x)){
                elem.setHairColor(HairColor.valueOf(x));
                return true;
            }
            return false;
        });
        args_checkers.put("национальность режиссёра (FRANCE, INDIA, VATICAN, THAILAND)", x -> {
            if (Country.contains(x)){
                elem.setNationality(Country.valueOf(x));
                return true;
            }
            return false;
        });

        try{
            for (String a : args_checkers.keySet()){
                Predicate<String> check = args_checkers.get(a);
                output.print("Введите " + a + ":");
                String line = input.nextLine();
                if (line == null || line.equals("exit")){
                    throw new InterruptException();
                }

                while (!check.test(line)){
                    output.print("Некорректные данные.");
                    output.print("Введите " + a + ":");
                    line = input.nextLine();
                }
            }

            elem.setLocation(Location.createLocation(input, output));

            return elem;
        } catch (IOException e){
            output.print("Что-то случилось, введите команду заново.");
            throw new InterruptException();
        }
    }

    @Override
    public boolean checkItself(){
        return name != null && !name.isBlank() && birthday != null && location.checkItself();
    }

    @Override
    public String toString() {
//        return name + " (" + nationality.name() + "), born in " + birthday.getYear();
        return name + " (" + nationality.name() + "), born in " +
                new SimpleDateFormat("dd.MM.yyyy").format(birthday);
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }
}
