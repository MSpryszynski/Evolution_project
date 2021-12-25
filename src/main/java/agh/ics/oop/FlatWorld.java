package agh.ics.oop;

public class FlatWorld extends AbstractWorldMap{

    public FlatWorld(int width, int height, int jungleWidth, int jungleHeight) {
        super(width, height, jungleWidth, jungleHeight);
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return (position.follows(startPoint) && position.precedes(endPoint));
    }
}
