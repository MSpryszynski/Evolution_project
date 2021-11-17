package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTestLab4 {
    OptionsParser parser = new OptionsParser();
    @Test
    public void onlyForwardTest(){
        String[] args = {"f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = parser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertTrue(map.isOccupied(new Vector2d(2, 5)) && map.isOccupied(new Vector2d(3, 5)));
    }
    @Test
    public void collisionTest(){
        String[] args = {"f", "l", "f", "f", "r", "b", "b"};
        MoveDirection[] directions = parser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertTrue(map.isOccupied(new Vector2d(1, 4)) && map.isOccupied(new Vector2d(4, 4)));
    }
    @Test
    public void collisionNearEdgeTest(){
        String[] args = {"f", "f", "f", "l", "f", "l", "r", "b", "f", "f","l","r","l","f"};
        MoveDirection[] directions = parser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertTrue(map.isOccupied(new Vector2d(2, 5)) && map.isOccupied(new Vector2d(2, 4)));
    }
    @Test
    public void objectAtTest(){
        IWorldMap map = new RectangularMap(10, 5);
        Animal zwierzak = new Animal(map, new Vector2d(2,3));
        map.place(zwierzak);
        assertEquals(map.objectAt(new Vector2d(2, 3)), zwierzak);
    }

    @Test
    public void placeNotInMapTest(){
        IWorldMap map = new RectangularMap(10, 5);
        Animal zwierzak = new Animal(map, new Vector2d(11,3));
        map.place(zwierzak);
        for (int i=0; i<=10; i++){
            for (int j=0; j<=5; j++){
                if(map.isOccupied(new Vector2d(i, j))){
                    fail();
                }
            }
        }
    }
}
