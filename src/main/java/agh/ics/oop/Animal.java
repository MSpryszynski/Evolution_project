package agh.ics.oop;

import java.util.Objects;

public class Animal extends AbstractWorldMapElement{
    private MapDirection orientation = MapDirection.NORTH;
    private final IWorldMap map;
    private static final int maxi=4;


    public Animal(IWorldMap map){
        this.map = map;
        this.position = new Vector2d(2,2);
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
    }

    public Animal(){
        this.position = new Vector2d(2,2);
        this.map = new RectangularMap(maxi, maxi);
    }


    @Override
    public String toString() {
        String temp = "";
        switch (orientation){
            case NORTH -> temp="N";
            case EAST -> temp="E";
            case SOUTH -> temp="S";
            case WEST -> temp="W";
        }
        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return orientation == animal.orientation && Objects.equals(position, animal.position) && Objects.equals(map, animal.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orientation, position, map);
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public IWorldMap getMap(){return map;}


    public void move(MoveDirection[] directions){
        for (MoveDirection direction:directions) {
            switch (direction) {
                case RIGHT:
                    orientation = orientation.next();
                    break;
                case LEFT:
                    orientation = orientation.previous();
                    break;
                case FORWARD:
                    if (map.canMoveTo(orientation.toUnitVector().add(position))) {
                        position = position.add(orientation.toUnitVector());
                    }
                    break;
                case BACKWARD:
                    if (map.canMoveTo(orientation.toUnitVector().opposite().add(position))) {
                        position = position.add(orientation.toUnitVector().opposite());
                    }
                    break;
            }
        }
    }
    public void move(MoveDirection direction) {
        MoveDirection[] directions = {direction};
        move(directions);
    }
}
