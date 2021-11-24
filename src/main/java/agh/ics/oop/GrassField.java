package agh.ics.oop;

import java.util.LinkedHashSet;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{

    public GrassField(int amountOfGrassFields){
        int width = (int) sqrt(amountOfGrassFields * 10);
        int height = (int) sqrt(amountOfGrassFields * 10);

        Random rand = new Random();
        LinkedHashSet<Integer> generated = new LinkedHashSet<>();
        while (generated.size() < amountOfGrassFields)
        {
            int next = rand.nextInt(width * height +1);
            if (generated.add(next)){
                Vector2d vector = new Vector2d(next % width, next/ width);
                mapElements.add(new Grass(vector));
            }
        }
    }
}
