package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;




public class MapDirectionTest {
    @Test
    public void nextTest1(){
        assertSame(MapDirection.EAST.next(), MapDirection.SOUTH);
    }
    @Test
    public void nextTest2(){
        assertSame(MapDirection.SOUTH.next(), MapDirection.WEST);
    }
    @Test
    public void nextTest3(){
        assertSame(MapDirection.WEST.next(), MapDirection.NORTH);
    }
    @Test
    public void nextTest4(){
        assertSame(MapDirection.NORTH.next(), MapDirection.EAST);
    }
    @Test
    public void previousTest1(){
        assertSame(MapDirection.EAST.previous(), MapDirection.NORTH);
    }
    @Test
    public void previousTest2(){
        assertSame(MapDirection.SOUTH.previous(), MapDirection.EAST);
    }
    @Test
    public void previousTest3(){
        assertSame(MapDirection.WEST.previous(), MapDirection.SOUTH);
    }
    @Test
    public void previousTest4(){
        assertSame(MapDirection.NORTH.previous(), MapDirection.WEST);
    }
}
