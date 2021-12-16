package agh.ics.oop;


public class RectangularMap extends AbstractWorldMap{



    @Override
    public boolean canMoveTo(Vector2d position){
        if(position.follows(lowLeft) && position.precedes(upRight)) {
            return (super.canMoveTo(position));
        }
        return false;
    }
}
