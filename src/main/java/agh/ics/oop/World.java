package agh.ics.oop;


public class World {
    public static void main(String[] args) {
        try {
            System.out.println("System wystartował");
            MoveDirection[] directions = new OptionsParser().parse(args);
            MoveDirection[] directions1 = {MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD,MoveDirection.FORWARD};
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(2, 2), new Vector2d(3, 2),new Vector2d(4, 2)};
            System.out.println(map);
            IEngine engine = new SimulationEngine(directions1, map, positions);
            System.out.println(map);
            engine.run();
            System.out.println(map);
            System.out.println("Sysytem skończył działanie");
        } catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }
}
