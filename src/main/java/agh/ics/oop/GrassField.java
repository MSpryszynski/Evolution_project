package agh.ics.oop;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{
    private final int width;
    private final int height;


    public GrassField(int amountOfGrassFields){
        this.width = (int) sqrt(amountOfGrassFields * 10);
        this.height = (int) sqrt(amountOfGrassFields * 10);
        for (int i=0; i<amountOfGrassFields; i++) {
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
                mapElements.put(vector, grass);
                mapBoundary.addElement(vector);
                break;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        Object mapElement = this.objectAt(position);
        if (mapElement instanceof Grass){
            mapBoundary.removeElement(position);
            mapElements.remove(position);
            this.placeGrass();
        }
        return !(mapElement instanceof Animal);
    }
}
