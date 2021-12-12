package agh.ics.oop;

import java.util.LinkedHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected final LinkedHashMap<Vector2d,IMapElement> mapElements = new LinkedHashMap<>();
    protected final MapBoundary mapBoundary = new MapBoundary(this);
    protected Vector2d lowLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    protected Vector2d upRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);


    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition()) && this.equals(animal.getMap())){
            mapElements.put(animal.getPosition(), animal);
            mapBoundary.addElement(animal.getPosition());
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Position " + animal.getPosition() + " is occupied");
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        Object mapElement = this.objectAt(position);
        return !(mapElement instanceof Animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return  (mapElements.containsKey(position));
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            return mapElements.get(position);
        }
        return null;
    }

    @Override
    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(mapBoundary.getLowerLeft(), mapBoundary.getUpperRight());
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        IMapElement mapElement = mapElements.remove(oldPosition);
        mapElements.put(newPosition, mapElement);
        mapBoundary.positionChanged(oldPosition, newPosition);
    }

    public Vector2d getLowerLeft(){
        return mapBoundary.getLowerLeft();
    }

    public Vector2d getUpperRight(){
        return mapBoundary.getUpperRight();
    }

}
