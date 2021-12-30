package agh.ics.oop;

import agh.ics.oop.gui.AppUpdater;

import java.util.ArrayList;


public class SimulationEngine implements Runnable{


//    Used link
//    https://stackoverflow.com/questions/16758346/how-pause-and-then-resume-a-thread

    private final AppUpdater observer;
    private final IWorldMap map;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    private Integer numberOfDays = 0;
    private Long animals = (long)0;
    private Long grass = (long)0;
    private double energy = 0;
    private double lifetime = 0;
    private double children = 0;
    private final ArrayList<String[]> toCSV = new ArrayList<>();

    public SimulationEngine(IWorldMap map, AppUpdater appUpdater, int numberOfAnimals){
        this.observer = appUpdater;
        this.map = map;
        String[] header = {"day", "animals", "grass", "avg energy", "avg lifetime", "avg children"};
        toCSV.add(header);
        for(int i=0; i<numberOfAnimals; i++){
            map.placeAnimals(map.getWidth(), map.getHeight());
        }
    }


    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }
            map.deleteDeadAnimals();
            if (map.getTrackedStatus().equals("Dead")){
                Animal tracked = map.sendTrackedAnimal();
                if(tracked.getDeathDay() == 0){
                    tracked.setDeathDay(numberOfDays);
                }
            }
            map.moveAnimals();
            map.eatPlants();
            map.doReproductions();
            map.growOfPlants();
            animals += map.getNumberOfAnimals();
            grass += map.getNumberOfPlants();
            energy += map.getAverageEnergy();
            lifetime += map.averageLifeTime();
            children += map.averageChildren();
            String[] record = {numberOfDays.toString(),
                    map.getNumberOfAnimals().toString(),
                    map.getNumberOfPlants().toString(),
                    map.getAverageEnergy().toString(),
                    map.averageLifeTime().toString(),
                    map.averageChildren().toString()};
            toCSV.add(record);
            try{
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            observer.mapChanged();
            numberOfDays += 1;
        }
    }

    public void stop() {
        running = false;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public Integer getNumberOfDays(){
        return numberOfDays;
    }

    public ArrayList<String[]> getCSV(){
        return toCSV;
    }

    public String[] average(){
        double avgAnimals = (double)(animals/numberOfDays);
        double avgGrass = (double)(grass/numberOfDays);
        double avgEnergy = energy/numberOfDays;
        double avgLifetime = lifetime/numberOfDays;
        double avgChildren = children/numberOfDays;
        return new String[]{"1",
                Double.toString(avgAnimals),
                Double.toString(avgGrass),
                Double.toString(avgEnergy),
                Double.toString(avgLifetime),
                Double.toString(avgChildren),};
    }
}