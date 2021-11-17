package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap implements IWorldMap{

    private final int width;
    private final int height;
    private final ArrayList<Animal> animals = new ArrayList<>();

    public RectangularMap(int width,int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (isOccupied(position)){
            return false;
        }
        return(position.follows(new Vector2d(0,0)) && position.precedes(new Vector2d(width, height)));
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal: animals) {
            if (animal.isAt(position)) {return true;}
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal: animals) {
            if (animal.isAt(position)) {return animal;}
        }
        return null;
    }


    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0), new Vector2d(width,height));
    }


}
