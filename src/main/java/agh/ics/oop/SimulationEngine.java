package agh.ics.oop;


import java.util.ArrayList;

public class SimulationEngine implements IEngine{
    private final MoveDirection[] directions;
    private final ArrayList<Animal> animals = new ArrayList<>();

    SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] startingPositions){
        this.directions = directions;

        for (Vector2d position:startingPositions) {
            Animal animal = new Animal(map, position);
            map.place(animal);
            animals.add(animal);
        }
    }

    @Override
    public void run(){
        int len = animals.size();
        for (int i=0; i<directions.length; i++) {
            this.animals.get(i % len).move(this.directions[i]);
        }
    }
}
