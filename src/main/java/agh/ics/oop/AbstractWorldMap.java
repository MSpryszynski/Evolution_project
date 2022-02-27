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
    protected long lifetime;
    protected int deadAnimals;
    protected int currentEnergy;
    protected int children;
    private Animal trackedAnimal;
    private String trackedStatus = "Alive";
    private boolean isMagic;
    private int chancesLeft = 3;
    private final Comparator<Animal> comparator = new Compare();


    public AbstractWorldMap(int width, int height, int jungleWidth, int jungleHeight, int plantEnergy, int startEnergy, int moveEnergy, boolean isMagic){
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
        this.lifetime = 0;
        this.deadAnimals = 0;
        this.currentEnergy = 0;
        this.children = 0;
        this.isMagic = isMagic;
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

    public void placeMagicAnimal(int width, int height, Genotype genotype){
        Random rand = new Random();
        while(true){
            int next = rand.nextInt((width+1) * (height+1));
            Vector2d vector = (new Vector2d(next % (width+1), next/ (width+1)));
            if (!isOccupied(vector)){
                Animal animal = new Animal(this, vector, startEnergy, genotype, moveEnergy);
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
        currentEnergy = 0;
        for (Animal animal:animalList) {
            animal.useGenotype();
            animal.updateLifeTime();
            currentEnergy += animal.getEnergy();
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
                fieldsOfGrass.remove(vector);
            }
        }
    }

    public void doReproductions(){
        for (var entry : animals.entrySet()) {
            ArrayList<Animal> tempAnimals = entry.getValue();
            if (tempAnimals.size() >= 2 && tempAnimals.get(1).getEnergy() >= startEnergy/2){
                children += 2;
                Animal child = tempAnimals.get(0).reproduction(tempAnimals.get(1));
                tempAnimals.add(child);
                tempAnimals.sort(comparator);
                child.addObserver(this);
                animals.put(entry.getKey(), tempAnimals);
                animalList.add(child);
            }
        }
    }

    public void deleteDeadAnimals(){
        for (Animal animal:animalList) {
            if (animal.getEnergy() <= 0) {
                deleteAnimal(animal.getPosition(), animal);
                if(animal.isTracked()){
                    trackedStatus = "Dead";
                }
                deadAnimals += 1;
                lifetime += animal.getLifeTime();
                children -= animal.getChildren();
            }
        }
        animalList.removeIf(animal -> animal.getEnergy() <= 0);
        if(isMagic && animalList.size()<= 5 && chancesLeft>0){
            chancesLeft -= 1;
            if(animalList.size()>0){
                ArrayList<Genotype> genotypes = new ArrayList<>();
                int iterator = 0;
                while (genotypes.size()+animalList.size()<10){
                    genotypes.add(animalList.get(iterator % animalList.size()).getGenotype());
                    iterator += 1;
                }
                for (Genotype genotype:genotypes){
                    placeMagicAnimal(width, height, genotype);
                }
            }
            else{
                for(int i=0;i<10;i++){
                    placeAnimals(width, height);
                }
            }
        }
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

    public Integer getNumberOfAnimals(){
        return animalList.size();
    }

    public Integer getNumberOfPlants(){
        return fieldsOfGrass.size();
    }

    public Genotype getBestGenotype(){
        HashMap<Genotype,Integer> genesMap = new HashMap<>();
        Integer highestValue = 0;
        Genotype strongestGenotype = null;
        for(Animal animal:animalList){
            Genotype genotype = animal.getGenotype();
            if(genesMap.containsKey(genotype)){
                genesMap.put(genotype, genesMap.get(genotype)+1);
                if (highestValue < genesMap.get(genotype)){
                    highestValue = genesMap.get(genotype);
                    strongestGenotype = genotype;
                }
            }
            else {
                genesMap.put(genotype, 1);
                if (highestValue == 0){
                    highestValue = 1;
                    strongestGenotype = genotype;
                }
            }
        }
        return strongestGenotype;
    }


    public String getStrongestGenotype(){
        HashMap<Genotype,Integer> genesMap = new HashMap<>();
        Integer highestValue = 0;
        Genotype strongestGenotype = null;
        for(Animal animal:animalList){
            Genotype genotype = animal.getGenotype();
            if(genesMap.containsKey(genotype)){
                genesMap.put(genotype, genesMap.get(genotype)+1);
                if (highestValue < genesMap.get(genotype)){
                    highestValue = genesMap.get(genotype);
                    strongestGenotype = genotype;
                }
            }
            else {
                genesMap.put(genotype, 1);
                if (highestValue == 0){
                    highestValue = 1;
                    strongestGenotype = genotype;
                }
            }
        }
        if (strongestGenotype != null){
            return strongestGenotype.toString() + ' ' + highestValue.toString();
        }
        return "All animals are dead";
    }

    public ArrayList<Animal> getAnimalList(){
        return animalList;
    }

    public Integer getAverageEnergy(){
        if(animalList.size()>0){
            return currentEnergy/animalList.size();
        }
        else{
            return 0;
        }
    }

    public Double averageChildren(){
        double survivors = animalList.size();
        if(survivors>0){
            return children/survivors;
        }
        else return (double) 0;
    }

    public Double averageLifeTime(){
        if (deadAnimals == 0){
            return (double)0;
        }
        return lifetime/(double)deadAnimals;
    }

    public void resetTrackedStatus(){
        trackedStatus = "Alive";
    }

    public String getTrackedStatus(){
        return trackedStatus;
    }

    public Animal sendTrackedAnimal(){
        return trackedAnimal;
    }

    public void setTrackedAnimal(Animal animal){
        trackedAnimal = animal;
    }

    public String sendMagicInfo(){
        if(!isMagic){
            return "Map isn't magic";
        }
        if(chancesLeft == 1 ){
            return chancesLeft + " chance left";
        }
        return chancesLeft + " chances left";
    }

    public Vector2d getJungleEndPoint() {
        return jungleEndPoint;
    }

    public Vector2d getJungleStartPoint() {
        return jungleStartPoint;
    }

    static class Compare implements Comparator<Animal> {
        public int compare(Animal animal1, Animal animal2){
            if (animal1.getEnergy() < animal1.getEnergy()) return -1;
            else if (animal1.getEnergy() > animal2.getEnergy()) return 1;
            return 0;
        }
    }
}
