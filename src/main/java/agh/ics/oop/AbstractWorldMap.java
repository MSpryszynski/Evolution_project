package agh.ics.oop;

import java.util.*;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected final HashMap<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    protected final HashMap<Vector2d, Grass> fieldsOfGrass = new HashMap<>();
    protected final ArrayList<Animal> animalList = new ArrayList<>();
    protected final int width;
    protected final int height;
    protected final int jungleWidth;
    protected final int jungleHeight;
    protected final Vector2d startPoint;
    protected final Vector2d endPoint;
    protected final Vector2d jungleStartPoint;
    protected final Vector2d jungleEndPoint;
    protected final int plantEnergy;
    protected final int startEnergy;
    protected final int moveEnergy;
    private final Comparator<Animal> comparator = new Compare();


    public AbstractWorldMap(int width, int height, int jungleWidth, int jungleHeight, int plantEnergy, int startEnergy, int moveEnergy){
        this.width = width;
        this.height = height;
        this.startPoint = new Vector2d(0, 0);
        this.endPoint = new Vector2d(width, height);
        this.jungleWidth = jungleWidth;
        this.jungleHeight = jungleHeight;
        this.jungleStartPoint = new Vector2d(width/2-jungleWidth/2, height/2-jungleHeight/2);
        this.jungleEndPoint = (new Vector2d(jungleWidth, jungleHeight)).add(jungleStartPoint);
        this.plantEnergy = plantEnergy;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
    }




    public void placeGrass(int width, int height, Vector2d startingVector){
        Random rand = new Random();
        for (int i=0;i<width*height*10;i++){
            int next = rand.nextInt((width+1) * (height+1));
            Vector2d vector = (new Vector2d(next % (width+1), next/(width+1)).add(startingVector));
            if (!isOccupied(vector)){
                Grass grass = new Grass(vector);
                fieldsOfGrass.put(vector, grass);
                break;
            }
        }
    }

    public void placeAnimals(int width, int height){
        Random rand = new Random();
        while(true){
            int next = rand.nextInt((width+1) * (height+1));
            Vector2d vector = (new Vector2d(next % (width+1), next/ (width+1)));
            if (!isOccupied(vector)){
                Animal animal = new Animal(this, vector, startEnergy, moveEnergy);
                ArrayList<Animal> tempAnimals = new ArrayList<>();
                tempAnimals.add(animal);
                animal.addObserver(this);
                animals.put(vector, tempAnimals);
                animalList.add(animal);
                break;
            }
        }
    }

    public void moveAnimals(){
        for (Animal animal:animalList) {
            animal.useGenotype();
        }
    }

    public void growOfPlants(){
        placeGrass(this.width, this.height, this.startPoint);
        placeGrass(this.jungleWidth, this. jungleHeight, this.jungleStartPoint);
    }

    public void eatPlants(){
        Set<Vector2d> keySet = animals.keySet();
        for(Vector2d vector : keySet){
            if(fieldsOfGrass.containsKey(vector)){
                fieldsOfGrass.remove(vector);
                ArrayList<Animal> tempAnimals = animals.get(vector);
                int maxEnergy = tempAnimals.get(0).getEnergy();
                int iter=0;
                while(iter<tempAnimals.size() && tempAnimals.get(iter).getEnergy() == maxEnergy){
                    iter += 1;
                }
                for(int i=0; i<iter; i++){
                    tempAnimals.get(i).addEnergy(plantEnergy/iter);
                }
                animals.put(vector, tempAnimals);
            }
        }
    }

    public void doReproductions(){
        for (var entry : animals.entrySet()) {
            ArrayList<Animal> tempAnimals = entry.getValue();
            if (tempAnimals.size() >= 2 && tempAnimals.get(1).getEnergy() >= startEnergy/2){
                Animal child = tempAnimals.get(0).reproduction(tempAnimals.get(1));
                tempAnimals.add(child);
                tempAnimals.sort(comparator);
                animals.put(entry.getKey(), tempAnimals);
                animalList.add(child);
            }
        }
    }

    public void deleteDeadAnimals(){
        Set<Vector2d> set = animals.keySet();
        for (Animal animal:animalList){
            if (!set.contains(animal.getPosition())){
                System.out.println("Kurwa");
                System.out.println(animal.getPosition());
                for(Vector2d vector:set){
                    System.out.println(vector);
                }
                System.out.println(animal.getEnergy());
            }
            if (animal.getEnergy()<=0){
                deleteAnimal(animal.getPosition(), animal);
            }
        }
        animalList.removeIf(animal -> animal.getEnergy() <= 0);
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
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        ArrayList<Animal> tempAnimals = animals.remove(oldPosition);
        tempAnimals.remove(animal);
        if (tempAnimals.size()>0){
            animals.put(oldPosition, tempAnimals);
        }
        if (animals.get(newPosition) != null){
            ArrayList<Animal> tempAnimalsNewPos = animals.get(newPosition);
            tempAnimalsNewPos.add(animal);
            tempAnimalsNewPos.sort(comparator);
            animals.put(newPosition, tempAnimalsNewPos);
        }
        else{
            ArrayList<Animal> tempAnimalsNewPos = new ArrayList<>();
            tempAnimalsNewPos.add(animal);
            animals.put(newPosition, tempAnimalsNewPos);
        }
    }

    @Override
    public void deleteAnimal(Vector2d position, Animal animal){
        ArrayList<Animal> tempAnimals = animals.get(position);
        tempAnimals.remove(animal);
        animal.removeObserver(this);
        animals.remove(position);
        if(tempAnimals.size()>0) {
            animals.put(position, tempAnimals);
        }
    }

    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.follows(startPoint) && position.precedes(endPoint));
    }


    static class Compare implements Comparator<Animal> {
        public int compare(Animal animal1, Animal animal2){
            if (animal1.getEnergy() < animal1.getEnergy()) return -1;
            else if (animal1.getEnergy() > animal2.getEnergy()) return 1;
            return 0;
        }
    }


}
