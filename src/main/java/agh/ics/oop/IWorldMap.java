package agh.ics.oop;

import java.util.ArrayList;

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

    Integer getNumberOfAnimals();

    String getStrongestGenotype();

    Integer getAverageEnergy();

    Double averageChildren();

    Integer getNumberOfPlants();

    Double averageLifeTime();

    void resetTrackedStatus();

    String getTrackedStatus();

    Animal sendTrackedAnimal();

    void setTrackedAnimal(Animal animal);

    ArrayList<Animal> getAnimalList();

    Genotype getBestGenotype();



}