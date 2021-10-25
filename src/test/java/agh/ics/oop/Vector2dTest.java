package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    public void equalsTest1(){
        assertEquals(new Vector2d(1, 0), new Vector2d(1, 0));
    }
    @Test
    public void equalsTest2(){
        assertNotEquals(new Vector2d(1, 0), new Vector2d(1, 1));
    }
    @Test
    public void toStringTest1(){
        assertEquals(new Vector2d(1,0).toString(), "(1,0)");
    }
    @Test
    public void toStringTest2(){
        assertNotEquals(new Vector2d(1,0).toString(), "(2,0)");
    }
    @Test
    public void toStringTest3(){
        assertNotEquals(new Vector2d(1,0).toString(), "(1,1)");
    }
    @Test
    public void precedesTest1(){
        assertTrue(new Vector2d(1,0).precedes(new Vector2d(1, 0)));
    }
    @Test
    public void precedesTest2(){
        assertTrue(new Vector2d(1,0).precedes(new Vector2d(2, 2)));
    }
    @Test
    public void precedesTest3(){
        assertFalse(new Vector2d(1,0).precedes(new Vector2d(0, 0)));
    }
    @Test
    public void precedesTest4(){
        assertFalse(new Vector2d(1,0).precedes(new Vector2d(2, -1)));
    }
    @Test
    public void followsTest1(){
        assertTrue(new Vector2d(1,0).follows(new Vector2d(1, 0)));
    }
    @Test
    public void followsTest2(){
        assertTrue(new Vector2d(1,0).follows(new Vector2d(0, -1)));
    }
    @Test
    public void followsTest3(){
        assertFalse(new Vector2d(1,0).follows(new Vector2d(2, 0)));
    }
    @Test
    public void followsTest4(){
        assertFalse(new Vector2d(1,0).follows(new Vector2d(0, 1)));
    }
    @Test
    public void upperRightTest1(){
        assertEquals(new Vector2d(2,0).upperRight(new Vector2d(1,1)), new Vector2d(2,1));
    }
    @Test
    public void upperRightTest2(){
        assertEquals(new Vector2d(2,2).upperRight(new Vector2d(1,1)), new Vector2d(2,2));
    }
    @Test
    public void upperRightTest3(){
        assertNotEquals(new Vector2d(0,2).upperRight(new Vector2d(1,1)), new Vector2d(0,0));
    }
    @Test
    public void lowerLeftTest1(){
        assertEquals(new Vector2d(2,0).lowerLeft(new Vector2d(1,1)), new Vector2d(1,0));
    }
    @Test
    public void lowerLeftTest2(){
        assertEquals(new Vector2d(2,2).lowerLeft(new Vector2d(1,1)), new Vector2d(1,1));
    }
    @Test
    public void lowerLeftTest3(){
        assertNotEquals(new Vector2d(2,1).lowerLeft(new Vector2d(1,2)), new Vector2d(2,2));
    }
    @Test
    public void addTest1(){
        assertEquals(new Vector2d(1,1).add(new Vector2d(1,1)), new Vector2d(2,2));
    }
    @Test
    public void addTest2(){
        assertEquals(new Vector2d(41,0).add(new Vector2d(-1,1)), new Vector2d(40,1));
    }
    @Test
    public void addTest3(){
        assertNotEquals(new Vector2d(1,1).add(new Vector2d(0,0)), new Vector2d(0,0));
    }
    @Test
    public void subtractTest1(){
        assertEquals(new Vector2d(1,1).subtract(new Vector2d(1,1)), new Vector2d(0,0));
    }
    @Test
    public void subtractTest2(){
        assertEquals(new Vector2d(41,0).subtract(new Vector2d(-1,1)), new Vector2d(42,-1));
    }
    @Test
    public void subtractTest3(){
        assertNotEquals(new Vector2d(1,1).subtract(new Vector2d(0,0)), new Vector2d(0,0));
    }
    @Test
    public void oppositeTest1(){
        assertEquals(new Vector2d(0,0).opposite(), new Vector2d(0,0));
    }
    @Test
    public void oppositeTest2(){
        assertEquals(new Vector2d(1,2).opposite(), new Vector2d(-1,-2));
    }
    @Test
    public void oppositeTest3(){
        assertNotEquals(new Vector2d(-10,5).opposite(), new Vector2d(-5,10));
    }
}
