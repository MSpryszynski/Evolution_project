package agh.ics.oop;


public class RectangularMap extends AbstractWorldMap{

    public RectangularMap(int width,int height){
        this.lowLeft = new Vector2d(0,0);
        this.upRight = new Vector2d(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        if(position.follows(lowLeft) && position.precedes(upRight)) {
            return (super.canMoveTo(position));
        }
        return false;
    }
}
