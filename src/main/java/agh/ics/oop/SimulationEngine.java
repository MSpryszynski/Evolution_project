package agh.ics.oop;

import agh.ics.oop.gui.AppUpdater;

public class SimulationEngine implements Runnable{

    private final AppUpdater observer;
    private final IWorldMap map;

    public SimulationEngine(IWorldMap map, AppUpdater appUpdater, int numberOfAnimals){
        this.observer = appUpdater;
        this.map = map;
        for(int i=0; i<numberOfAnimals; i++){
            map.placeAnimals(map.getWidth(), map.getHeight());
        }
    }


    @Override
    public void run(){
        while(true){
            map.deleteDeadAnimals();
            map.moveAnimals();
            map.eatPlants();
            map.doReproductions();
            map.growOfPlants();
            try{
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            observer.mapChanged();
        }
    }
}
