package agh.ics.oop;

import java.util.LinkedHashMap;
import java.util.Random;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected final LinkedHashMap<Vector2d, Animal> animals = new LinkedHashMap<>();
    protected final LinkedHashMap<Vector2d, Grass> fieldsOfGrass = new LinkedHashMap<>();
    protected final int width;
    protected final int height;
    protected final int jungleWidth;
    protected final int jungleHeight;
    protected final Vector2d startPoint;
    protected final Vector2d endPoint;
    protected final Vector2d jungleStartPoint;
    protected final Vector2d jungleEndPoint;


    public AbstractWorldMap(int width, int height, int jungleWidth, int jungleHeight){
        this.width = width;
        this.height = height;
        this.startPoint = new Vector2d(0, 0);
        this.endPoint = new Vector2d(width, height);
        this.jungleWidth = jungleWidth;
        this.jungleHeight = jungleHeight;
        this.jungleStartPoint = new Vector2d(width/2-jungleWidth/2, height/2-jungleHeight/2);
        this.jungleEndPoint = (new Vector2d(jungleWidth, jungleHeight)).add(jungleStartPoint);
    }

    public void placeGrass(int width, int height, Vector2d startingVector){
        Random rand = new Random();
        while(true){
            int next = rand.nextInt(width * height +1);
            Vector2d vector = (new Vector2d(next % width, next/ width)).add(startingVector);
            if (!isOccupied(vector)){
                Grass grass = new Grass(vector);
                fieldsOfGrass.put(vector, grass);
                break;
            }
        }
    }

    public void growOfPlants(){
        placeGrass(this.width, this.height, this.startPoint);
        placeGrass(this.jungleWidth, this. jungleHeight, this.jungleStartPoint);
    }

    @Override
    public boolean canMoveTo(Vector2d vector) {
        return true;
    }

    @Override
    public void place(Animal animal) {
        animals.put(animal.getPosition(), animal);
        animal.addObserver(this);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return (animals.containsKey(position) || fieldsOfGrass.containsKey(position));
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (animals.containsKey(position)){
            return animals.get(position);
        }
        if (fieldsOfGrass.containsKey(position)){
            return fieldsOfGrass.get(position);
        }
        return null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }
}
