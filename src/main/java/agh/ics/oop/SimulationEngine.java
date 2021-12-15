package agh.ics.oop;

import agh.ics.oop.gui.AppUpdater;
import java.util.ArrayList;

public class SimulationEngine implements IEngine, Runnable{
    private MoveDirection[] directions;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final AppUpdater observer;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] startingPositions, AppUpdater appUpdater){
        this.directions = directions;
        this.observer = appUpdater;

        for (Vector2d position:startingPositions) {
            Animal animal = new Animal(map, position);
            map.place(animal);
            animals.add(animal);
        }
    }
    public SimulationEngine(IWorldMap map, Vector2d[] startingPositions, AppUpdater appUpdater){
        this.observer = appUpdater;
        for (Vector2d position:startingPositions) {
            Animal animal = new Animal(map, position);
            map.place(animal);
            animals.add(animal);
        }
    }

    public void setDirections(MoveDirection[] directions) {
        this.directions = directions;
    }

    @Override
    public void run(){
        int len = animals.size();
        for (int i=0; i<directions.length; i++) {
            try{
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.animals.get(i % len).move(this.directions[i]);
            observer.mapChanged();
        }
    }
}
