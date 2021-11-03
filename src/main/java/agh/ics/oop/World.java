package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("System wystartował");
        OptionsParser parser = new OptionsParser();
        MoveDirection[] args2 = parser.parse(args);
        Animal zwierzak = new Animal();
        System.out.println(zwierzak);
        //run(args2);
        zwierzak.move(args2);
        System.out.println(zwierzak);
        System.out.println("Sysytem skończył działanie");
    }
    public static void run(MoveDirection[] arguments){

        for (MoveDirection argument: arguments) {
            switch (argument){
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak idzie w prawo");
                case LEFT -> System.out.println("Zwierzak idzie w lewo");
                default -> System.out.println("Coś złego się stało");
            }
        }
        int counter = 0;
        for (MoveDirection argument: arguments) {
            counter += 1;
            if (counter != arguments.length){
                System.out.printf("%s, ", argument);
            }
            else {
                System.out.println(argument);
            }
        }
    }
}
