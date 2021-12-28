package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")");
    }

    public boolean precedes(Vector2d other) {
        return (x <= other.x && y <= other.y);
    }

    public boolean follows(Vector2d other){
        return (x >= other.x && y >= other.y);
    }


    public Vector2d add(Vector2d other){
        int a = x + other.x;
        int b = y + other.y;
        return new Vector2d(a, b);
    }


    @Override
    public boolean equals(Object other){
        if (other == null || other.getClass() != getClass()) return false;
        Vector2d temp = (Vector2d) other;
        return (x == temp.x && y == temp.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Vector2d opposite(){
        return new Vector2d(-x, -y);
    }
}
