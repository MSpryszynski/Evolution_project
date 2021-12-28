package agh.ics.oop;

public interface IWorldMap {

    boolean canMoveTo(Vector2d position);

    boolean isOccupied(Vector2d position);

    Object objectAt(Vector2d position);

    int getWidth();

    int getHeight();

    void placeAnimals(int width, int height);

    void deleteDeadAnimals();

    void eatPlants();

    void doReproductions();

    void growOfPlants();

    void moveAnimals();

}