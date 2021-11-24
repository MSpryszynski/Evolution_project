package agh.ics.oop;

import java.util.ArrayList;

public abstract class AbstractWorldMap implements IWorldMap{
    protected final ArrayList<IMapElement> mapElements = new ArrayList<>();
    protected Vector2d lowLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    protected Vector2d upRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);


    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition()) && this.equals(animal.getMap())){
            mapElements.add(0, animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        for (IMapElement mapElement: mapElements) {
            if (mapElement instanceof Animal) {
                if (mapElement.isAt(position)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (IMapElement mapElement: mapElements) {
            if (mapElement.isAt(position)) {return true;}
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (IMapElement mapElement : mapElements) {
            if (mapElement.isAt(position)) {return mapElement;}
        }
        return null;
    }

    @Override
    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        for (IMapElement mapElement:mapElements) {
            Vector2d vector = mapElement.getPosition();
            lowLeft = lowLeft.lowerLeft(vector);
            upRight = upRight.upperRight(vector);
        }
        return visualizer.draw(lowLeft,upRight);
    }
}
