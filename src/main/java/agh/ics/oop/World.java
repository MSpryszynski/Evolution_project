package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("System wystartował");
        String[] arguments = {"F", "B"};
        run(arguments);
        System.out.println("Sysytem skończył działanie");
    }

    public static void run(String[] arguments){
        System.out.println("Zwierzak idzie do przodu");
        int counter = 0;
        for (String argument: arguments) {
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