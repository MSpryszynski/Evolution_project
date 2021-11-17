package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("System wystartował");
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        System.out.println(map);
        IEngine engine = new SimulationEngine(directions, map, positions);
        System.out.println(map);
        engine.run();
        System.out.println(map);
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
