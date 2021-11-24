package agh.ics.oop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IWorldMapTest {
    IWorldMap grassField = new GrassField(10);
    IWorldMap rectangularMap = new RectangularMap(10, 15);
    Vector2d vector = new Vector2d(10,16);
    Animal animal = new Animal(grassField);
    Animal animal2 = new Animal(rectangularMap);
    Animal animal3 = new Animal(grassField, vector);
    Animal animal4 = new Animal(rectangularMap,vector);
    @Test
    public void placeInMapTest(){
        assertTrue(grassField.place(animal) && rectangularMap.place(animal2));
    }
    @Test
    public void placeNotInMapTest(){
        assertTrue(grassField.place(animal3) && !rectangularMap.place(animal4));
    }
    @Test
    public void placeAtAnimalTest(){
        assertFalse(grassField.place(animal) && grassField.place(animal));
    }
    @Test
    public void wrongMapTest(){
        assertFalse(grassField.place(animal4));
    }
    @Test
    public void canMoveToTest(){
        assertTrue(grassField.canMoveTo(new Vector2d(2,2)) && rectangularMap.canMoveTo(new Vector2d(2,2)));
    }
    @Test
    public void canMoveOutsideMap(){
        assertTrue(grassField.canMoveTo(new Vector2d(1000,1000)) && !rectangularMap.canMoveTo(new Vector2d(1000,1000)));
    }
    @Test
    public void canMoveWhereAnimalIs(){
        grassField.place(animal);
        assertFalse(grassField.canMoveTo(new Vector2d(2, 2)));
    }
    @Test
    public void isOccupiedByAnimalTest(){
        grassField.place(animal);
        assertTrue(grassField.isOccupied(new Vector2d(2,2)));
    }
    @Test
    public void isNotOccupiedTest(){
        assertFalse(grassField.isOccupied(new Vector2d(11,11)));
    }
    @Test
    public void isOccupiedOutside(){
        assertFalse(rectangularMap.isOccupied(new Vector2d(11,11)));
    }
    @Test
    public void objectAtTest(){
        grassField.place(animal);
        assertEquals(grassField.objectAt(new Vector2d(2, 2)), animal);
    }

}
