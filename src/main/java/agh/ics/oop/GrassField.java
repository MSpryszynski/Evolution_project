package agh.ics.oop;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{
    private final int width;
    private final int height;


    public GrassField(int amountOfGrassFields){
        this.width = (int) sqrt(amountOfGrassFields * 10);
        this.height = (int) sqrt(amountOfGrassFields * 10);
        for (int i=0; i<amountOfGrassFields*3; i++) {
            placeGrass();
        }
    }

    public void placeGrass(){
        Random rand = new Random();
        while(true){
            int next = rand.nextInt(width * height +1);
            Vector2d vector = new Vector2d(next % width, next/ width);
            if (!isOccupied(vector)){
                Grass grass = new Grass(vector);
                fieldsOfGrass.put(vector, grass);
                break;
            }
        }
    }

    public void doMapEvents(Vector2d position, Animal animal){
        Object mapElement = this.objectAt(position);
        if (mapElement instanceof Grass){
            fieldsOfGrass.remove(position);
            animal.setEnergy(animal.getEnergy()+25);
            this.placeGrass();
            return;
        }
        if (mapElement instanceof Animal){
            animals.remove(position);
            this.placeGrass();
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }


    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Position " + animal.getPosition() + " is occupied");
    }
}
