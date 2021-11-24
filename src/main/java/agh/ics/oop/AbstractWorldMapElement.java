package agh.ics.oop;

public abstract class AbstractWorldMapElement implements IMapElement{
    protected Vector2d position;

    public boolean isAt(Vector2d position){
        return (this.position.follows(position) && this.position.precedes(position));
    }

    public Vector2d getPosition(){
        return position;
    }

}
