package agh.ics.oop;


public class RoundWorld extends AbstractWorldMap{


    public RoundWorld(int width, int height, int jungleWidth, int jungleHeight) {
        super(width, height, jungleWidth, jungleHeight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }
}
