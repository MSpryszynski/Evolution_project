package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {
    Animal xyz = new Animal();
    OptionsParser parser = new OptionsParser();
    @Test
    public void OrientationTest1(){
        MoveDirection[] arr = {MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT};
        xyz.move(arr);
        assertEquals(xyz.getOrientation(), MapDirection.NORTH);
    }
    @Test
    public void OrientationTest2(){
        MoveDirection[] arr = {MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT};
        xyz.move(arr);
        assertEquals(xyz.getOrientation(), MapDirection.WEST);
    }
    @Test
    public void OrientationTest3(){
        MoveDirection[] arr = {MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.LEFT};
        xyz.move(arr);
        assertEquals(xyz.getOrientation(), MapDirection.NORTH);
    }
    @Test
    public void OrientationTest4(){
        MoveDirection[] arr = {MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT};
        xyz.move(arr);
        assertEquals(xyz.getOrientation(), MapDirection.EAST);
    }
    @Test
    public void PositionTest1(){
        MoveDirection[] arr = {MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.BACKWARD};
        xyz.move(arr);
        assertTrue(xyz.isAt(new Vector2d(1,2)));
    }
    @Test
    public void PositionTest2(){
        MoveDirection[] arr = {MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.BACKWARD};
        xyz.move(arr);
        assertTrue(xyz.isAt(new Vector2d(2,3)));
    }
    @Test
    public void PositionTest3(){
        MoveDirection[] arr = {MoveDirection.LEFT,MoveDirection.BACKWARD,MoveDirection.BACKWARD,MoveDirection.BACKWARD};
        xyz.move(arr);
        assertTrue(xyz.isAt(new Vector2d(4,2)));
    }
    @Test
    public void PositionTest4(){
        MoveDirection[] arr = {MoveDirection.FORWARD,MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.FORWARD};
        xyz.move(arr);
        assertTrue(xyz.isAt(new Vector2d(2,2)));
    }
    @Test
    public void PositionTest5(){
        MoveDirection[] arr = {MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.BACKWARD,MoveDirection.BACKWARD};
        xyz.move(arr);
        assertTrue(xyz.isAt(new Vector2d(0,4)));
    }
    @Test
    public void ArrayOfStringsTest1(){
        String[] arr = {"l","l","l","b"};
        xyz.move(parser.parse(arr));
        assertTrue(xyz.isAt(new Vector2d(1,2)));
    }
    @Test
    public void ArrayOfStringsTest2(){
        String[] arr = {"forward","forward","forward","forward","backward"};
        xyz.move(parser.parse(arr));
        assertTrue(xyz.isAt(new Vector2d(2,3)));
    }
    @Test
    public void ArrayOfStringsTest3(){
        String[] arr = {"l","backward","backward","b"};
        xyz.move(parser.parse(arr));
        assertTrue(xyz.isAt(new Vector2d(4,2)));
    }
    @Test
    public void ArrayOfStringsTest4(){
        String[] arr = {"forward","kaczka","l","Java",">>>>","Kotlin","left","f"};
        assertThrows(IllegalArgumentException.class, ()->xyz.move(parser.parse(arr)));
    }
    @Test
    public void ArrayOfStringsTest5(){
        String[] arr = {"forward","forward","r","b","b","obiektowe",">","funkcyjne"};
        assertThrows(IllegalArgumentException.class, ()->xyz.move(parser.parse(arr)));
    }
}
