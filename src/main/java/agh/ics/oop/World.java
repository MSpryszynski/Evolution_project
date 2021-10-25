package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("System wystartował");
        Direction[] arguments = {Direction.f, Direction.b, Direction.r, Direction.l, Direction.l};
        run(arguments);
        System.out.println("Sysytem skończył działanie");
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        MapDirection map1 = MapDirection.EAST;
        MapDirection map2 = MapDirection.NORTH;
        System.out.println(map1.next());
        System.out.println(map2.next());
        System.out.println(map2.previous());
        System.out.println(map1.previous());
        System.out.println(map1.toUnitVector());
    }
    public static void run(Direction[] arguments){

        for (Direction argument: arguments) {
            switch (argument){
                case f -> System.out.println("Zwierzak idzie do przodu");
                case b -> System.out.println("Zwierzak idzie do tyłu");
                case r -> System.out.println("Zwierzak idzie w prawo");
                case l -> System.out.println("Zwierzak idzie w lewo");
                default -> System.out.println("Coś złego się stało");
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
