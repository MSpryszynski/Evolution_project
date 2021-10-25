package agh.ics.oop;

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

    public Vector2d upperRight(Vector2d other){
        int a = Math.max(x, other.x);
        int b = Math.max(y, other.y);
        return new Vector2d(a, b);
    }

    public Vector2d lowerLeft(Vector2d other){
        int a = Math.min(x, other.x);
        int b = Math.min(y, other.y);
        return new Vector2d(a, b);
    }

    public Vector2d add(Vector2d other){
        int a = x + other.x;
        int b = y + other.y;
        return new Vector2d(a, b);
    }

    public Vector2d subtract(Vector2d other){
        int a = x - other.x;
        int b = y - other.y;
        return new Vector2d(a, b);
    }

    public boolean equals(Object other){
        if (other == null || other.getClass() != getClass()) return false;
        Vector2d temp = (Vector2d) other;
        return (x == temp.x && y == temp.y);
    }

    public Vector2d opposite(){
        return new Vector2d(-x, -y);
    }


}
