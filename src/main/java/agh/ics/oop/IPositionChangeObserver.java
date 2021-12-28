package agh.ics.oop;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
    void deleteAnimal(Vector2d position, Animal animal);
}
