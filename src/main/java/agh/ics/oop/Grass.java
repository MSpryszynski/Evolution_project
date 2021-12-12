package agh.ics.oop;

public class Grass extends AbstractWorldMapElement{

    public Grass(Vector2d position){
        this.position=position;
    }

    public String toString(){
        return "*";
    }

    @Override
    public String getImageUrl() {
        return "grass.png";
    }
}
