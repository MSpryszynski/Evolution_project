package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("System wystartował");
        Direction[] arguments = {Direction.FORWARD, Direction.BACKWARD, Direction.RIGHT, Direction.LEFT, Direction.LEFT};
        run(arguments);
        System.out.println("Sysytem skończył działanie");
    }
    public static void run(Direction[] arguments){
        for (Direction argument: arguments) {
            switch (argument){
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak idzie w prawo");
                case LEFT -> System.out.println("Zwierzak idzie w lewo");
            }
        }
        int counter = 0;
        for (Direction argument: arguments) {
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
