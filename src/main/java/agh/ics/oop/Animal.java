package agh.ics.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Animal extends AbstractWorldMapElement{
    private int direction;
    private MapDirection orientation;
    private final IWorldMap map;
    private int energy;
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private final int[] genotype;
    private final int moveEnergy;
    private final Random rand = new Random();




    public Animal(IWorldMap map, Vector2d initialPosition, int energy, int moveEnergy){
        this.map = map;
        this.position = initialPosition;
        this.energy=energy;
        this.genotype = drawGenotype();
        this.direction = rand.nextInt(8);
        this.orientation = setOrientation(this.direction);
        this.moveEnergy = moveEnergy;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int energy, int[] genotype, int moveEnergy){
        this.map = map;
        this.position = initialPosition;
        this.energy=energy;
        this.genotype = genotype;
        this.direction = rand.nextInt(8);
        this.orientation = setOrientation(this.direction);
        this.moveEnergy = moveEnergy;
    }

    private int[] drawGenotype(){
        int[] genotype = new int[32];
        for (int i=0; i<32;i++){
            int randNumber = rand.nextInt(8);
            genotype[i] = randNumber;
        }
        Arrays.sort(genotype);
        return genotype;
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

    public int[] getGenotype(){
        return genotype;
    }


    public void useGenotype(){
        int randInt = rand.nextInt(32);
        int move = this.genotype[randInt];
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

    @Override
    public String getImageUrl(){
        switch (orientation){
            case NORTH, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST -> {
                return "up.png";
            }
            case EAST -> {
                return "right.png";
            }
            case SOUTH -> {
                return "down.png";
            }
            case WEST -> {
                return "left.png";
            }
            default -> throw new IllegalArgumentException("Direction not found");
        }
    }

    public Animal reproduction(Animal father){
        int[] fatherGenotype = father.getGenotype();
        int[] childGenotype = new int[32];
        Random random = new Random();
        int motherSide = random.nextInt(2);
        int genotypeBorder = energy*32/(energy+ father.getEnergy());
        if (motherSide == 0){
            System.arraycopy(genotype, 0, childGenotype, 0, genotypeBorder);
            System.arraycopy(fatherGenotype, genotypeBorder, childGenotype, genotypeBorder, 32 - genotypeBorder);
        }
        else{
            System.arraycopy(fatherGenotype, 0, childGenotype, 0, genotypeBorder);
            System.arraycopy(genotype, genotypeBorder, childGenotype, genotypeBorder, 32 - genotypeBorder);
        }
        Arrays.sort(childGenotype);
        this.addEnergy(-energy/4);
        father.addEnergy(-father.getEnergy()/4);
        return new Animal(map, position, (energy+ father.getEnergy())/4, childGenotype, moveEnergy);
    }
}
