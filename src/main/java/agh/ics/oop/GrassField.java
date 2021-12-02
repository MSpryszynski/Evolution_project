package agh.ics.oop;

import java.util.LinkedHashSet;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{
    private final int width;
    private final int height;

    public GrassField(int amountOfGrassFields){
        this.width = (int) sqrt(amountOfGrassFields * 10);
        this.height = (int) sqrt(amountOfGrassFields * 10);

        Random rand = new Random();
        LinkedHashSet<Integer> generated = new LinkedHashSet<>();
        while (generated.size() < amountOfGrassFields)
        {
            int next = rand.nextInt(width * height +1);
            if (generated.add(next)){
                Vector2d vector = new Vector2d(next % width, next/ width);
                mapElements.put(vector, new Grass(vector));
            }
        }
    }

    public void placeGrass(){
        Random rand = new Random();
        while(true){
            int next = rand.nextInt(width * height +1);
            Vector2d vector = new Vector2d(next % width, next/ width);
            if (!isOccupied(vector)){
                mapElements.put(vector, new Grass(vector));
                break;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        Object mapElement = this.objectAt(position);
        if (mapElement instanceof Grass){
            mapElements.remove(position);
            this.placeGrass();
        }
        return !(mapElement instanceof Animal);
    }
}
