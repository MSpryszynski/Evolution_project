package agh.ics.oop;

import java.util.ArrayList;
import java.util.Random;

public class Animal extends AbstractWorldMapElement{
    private int direction;
    private MapDirection orientation;
    private final IWorldMap map;
    private int energy;
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private final Genotype genotype;
    private final int moveEnergy;
    private final Random rand = new Random();
    private int lifeTime=0;
    private int children=0;
    private boolean tracked = false;
    private boolean trackedAncestor = false;
    private int trackedChildren = 0;
    private int trackedDescendants = 0;
    private int deathDay = 0;

    public Animal(IWorldMap map, Vector2d initialPosition, int energy, int moveEnergy){
        this.map = map;
        this.position = initialPosition;
        this.energy=energy;
        this.genotype = new Genotype();
        this.direction = rand.nextInt(8);
        this.orientation = setOrientation(this.direction);
        this.moveEnergy = moveEnergy;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int energy, Genotype genotype, int moveEnergy){
        this.map = map;
        this.position = initialPosition;
        this.energy=energy;
        this.genotype = genotype;
        this.direction = rand.nextInt(8);
        this.orientation = setOrientation(this.direction);
        this.moveEnergy = moveEnergy;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer:observers) {
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }
    @Override
    public String toString() {
        String temp = "";
        switch (orientation){
            case NORTH -> temp="N";
            case NORTHEAST -> temp="NE";
            case EAST -> temp="E";
            case SOUTHEAST -> temp="SE";
            case SOUTH -> temp="S";
            case SOUTHWEST -> temp="SW";
            case WEST -> temp="W";
            case NORTHWEST -> temp="NW";
        }
        return temp;
    }

    public Genotype getGenotype(){
        return genotype;
    }


    public void useGenotype(){
        int randInt = rand.nextInt(32);
        int move = this.genotype.getGenes()[randInt];
        switch (move) {
            case 0 -> this.moveForward();
            case 1 -> {
                this.direction += 1;
                this.direction %= 8;
                this.orientation = setOrientation(this.direction);
            }
            case 2 -> {
                this.direction += 2;
                this.direction %= 8;
                this.orientation = setOrientation(this.direction);
            }
            case 3 -> {
                this.direction += 3;
                this.direction %= 8;
                this.orientation = setOrientation(this.direction);
            }
            case 4 -> this.moveBackward();
            case 5 -> {
                this.direction += 5;
                this.direction %= 8;
                this.orientation = setOrientation(this.direction);
            }
            case 6 -> {
                this.direction += 6;
                this.direction %= 8;
                this.orientation = setOrientation(this.direction);
            }
            case 7 -> {
                this.direction += 7;
                this.direction %= 8;
                this.orientation = setOrientation(this.direction);
            }
        }
        this.energy -= moveEnergy;
    }

    private void moveForward(){
        Vector2d tempPosition = position;
        if (map.canMoveTo(orientation.toUnitVector().add(position))) {
            position = position.add(orientation.toUnitVector());
            positionChanged(tempPosition,position);
        }
        else if(this.map instanceof RoundWorld){
            position = position.add(orientation.toUnitVector());
            position = new Vector2d(position.x % (map.getWidth()+1), position.y % (map.getHeight()+1));
            if (position.x < 0) {
                position = new Vector2d(map.getWidth(), position.y);
            }
            if (position.y < 0) {
                position = new Vector2d(position.x, map.getHeight());
            }
            positionChanged(tempPosition, position);
        }
    }

    private void moveBackward(){
        Vector2d tempPosition = position;
        if (map.canMoveTo(orientation.toUnitVector().opposite().add(position))) {
            position = position.add(orientation.toUnitVector().opposite());
            positionChanged(tempPosition,position);
        }
        else if(this.map instanceof RoundWorld){
            position = position.add(orientation.toUnitVector().opposite());
            position = new Vector2d(position.x % (map.getWidth()+1), position.y % (map.getHeight()+1));
            if (position.x < 0) {
                position = new Vector2d(map.getWidth(), position.y);
            }
            if (position.y < 0) {
                position = new Vector2d(position.x, map.getHeight());
            }
            positionChanged(tempPosition,position);
        }
    }

    public MapDirection setOrientation(int direction){
        switch(direction){
            case 0 -> { return MapDirection.NORTH; }
            case 1 -> { return MapDirection.NORTHEAST; }
            case 2 -> { return MapDirection.EAST; }
            case 3 -> { return MapDirection.SOUTHEAST; }
            case 4 -> { return MapDirection.SOUTH; }
            case 5 -> { return MapDirection.SOUTHWEST; }
            case 6 -> { return MapDirection.WEST; }
            case 7 -> { return MapDirection.NORTHWEST; }
        }
        return null;
    }


    public Animal reproduction(Animal father){
        this.addChild();
        father.addChild();
        Genotype fatherGenotype = father.getGenotype();
        int genotypeBorder = energy*32/(energy+ father.getEnergy());
        Genotype childGenotype = new Genotype(this.genotype, fatherGenotype, genotypeBorder);
        this.addEnergy(-energy/4);
        father.addEnergy(-father.getEnergy()/4);
        Animal child = new Animal(map, position, (energy+ father.getEnergy())/4, childGenotype, moveEnergy);
        if(tracked){
            trackedChildren += 1;
            trackedDescendants +=1;
            child.setTrackedAncestor(true);
        }
        if(father.isTracked()){
            father.addTrackedChild();
            father.addTrackedDescendants();
            child.setTrackedAncestor(true);
        }
        if(trackedAncestor){
            child.setTrackedAncestor(true);
            Animal ancestor = map.sendTrackedAnimal();
            ancestor.addTrackedDescendants();
        }
        if (father.hasTrackedAncestor()){
            child.setTrackedAncestor(true);
            Animal ancestor = map.sendTrackedAnimal();
            ancestor.addTrackedDescendants();
        }
        return child;
    }

    public void updateLifeTime(){
        this.lifeTime += 1;
    }

    public int getLifeTime(){
        return lifeTime;
    }

    public void addChild(){
        children += 1;
    }

    public int getChildren(){
        return children;
    }

    public void setTracked(){
        this.tracked = true;
    }
    public void unsetTracked(){
        this.tracked = false;
        trackedChildren = 0;
        trackedDescendants = 0;
        map.resetTrackedStatus();
    }
    public boolean isTracked(){
        return tracked;
    }

    public void addTrackedChild(){
        this.trackedChildren += 1;
    }

    public Integer getTrackedChildren(){
        return trackedChildren;
    }

    public void setDeathDay(int day){
        this.deathDay = day;
    }

    public Integer getDeathDay(){
        return deathDay;
    }

    public void setTrackedAncestor(boolean bool){
        trackedAncestor = bool;
    }

    public void addTrackedDescendants(){
        this.trackedDescendants += 1;
    }

    public boolean hasTrackedAncestor(){
        return trackedAncestor;
    }

    public Integer getTrackedDescendants(){
        return trackedDescendants;
    }
}
