package agh.ics.oop;


public class World {
    public static void main(String[] args) {

        System.out.println("System wystartował");
        MoveDirection[] directions = new OptionsParser().parse(args);
        MoveDirection[] directions1 = {MoveDirection.LEFT, MoveDirection.LEFT,MoveDirection.FORWARD};
        IWorldMap map = new GrassField(3);
        Vector2d[] positions = { new Vector2d(0,2), new Vector2d(2,2) };
        System.out.println(map);
        IEngine engine = new SimulationEngine(directions1, map, positions);
        System.out.println(map);
        engine.run();
        System.out.println(map);

        System.out.println("Sysytem skończył działanie");

    }
}
