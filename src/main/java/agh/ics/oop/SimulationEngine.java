package agh.ics.oop;

import agh.ics.oop.gui.AppUpdater;
import java.util.ArrayList;

public class SimulationEngine implements Runnable{
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final AppUpdater observer;

    public SimulationEngine(IWorldMap map, Vector2d[] startingPositions, AppUpdater appUpdater){
        this.observer = appUpdater;

        for (Vector2d position:startingPositions) {
            Animal animal = new Animal(map, position, 0);
            map.place(animal);
            animals.add(animal);
        }
    }


    @Override
    public void run(){
//        int len = animals.size();
//        for (int i=0; i<directions.length; i++) {
//            try{
//                Thread.sleep(300);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            this.animals.get(i % len).move();
//            observer.mapChanged();
//        }
//    }
}}
