package agh.ics.oop;

import java.util.LinkedHashMap;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected final LinkedHashMap<Vector2d, Animal> animals = new LinkedHashMap<>();
    protected final LinkedHashMap<Vector2d, Grass> fieldsOfGrass = new LinkedHashMap<>();
    public final Vector2d lowLeft = new Vector2d(0, 0);
    public final Vector2d upRight = new Vector2d(30, 30);


    @Override
    public boolean canMoveTo(Vector2d vector) {
        return true;
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition()) && this.equals(animal.getMap())) {
            animals.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Position " + animal.getPosition() + " is occupied");
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return (animals.containsKey(position));
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            return animals.get(position);
        }
        return null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }
}
