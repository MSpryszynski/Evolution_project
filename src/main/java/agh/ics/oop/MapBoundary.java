package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{

    private final TreeSet<Vector2d> mapElementsX = new TreeSet<>(new CompareX());
    private final TreeSet<Vector2d> mapElementsY = new TreeSet<>(new CompareY());
    private final AbstractWorldMap worldMap;

    public MapBoundary(AbstractWorldMap worldMap){
        this.worldMap=worldMap;
    }


    public void addElement(Vector2d vector){
        mapElementsX.add(vector);
        mapElementsY.add(vector);
    }

    public void removeElement(Vector2d vector){
        mapElementsX.remove(vector);
        mapElementsY.remove(vector);
    }


    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        this.removeElement(oldPosition);
        this.addElement(newPosition);
    }

    public Vector2d getLowerLeft(){
        return mapElementsY.first().lowerLeft(mapElementsX.first());
    }

    public Vector2d getUpperRight(){
        return mapElementsY.last().upperRight(mapElementsX.last());
    }


    static class CompareX implements Comparator<Vector2d> {
        public int compare(Vector2d vector1, Vector2d vector2){
            if (vector1.x < vector2.x) return -1;
            else if (vector1.x > vector2.x) return 1;
            else {
                if (vector1.y < vector2.y) return -1;
                else if(vector1.y > vector2.y) return 1;
            }
            return 0;
        }
    }

    static class CompareY implements Comparator<Vector2d> {
        public int compare(Vector2d vector1, Vector2d vector2){
            if (vector1.y < vector2.y) return -1;
            else if (vector1.y > vector2.y) return 1;
            else {
                if (vector1.x < vector2.x) return -1;
                else if(vector1.x > vector2.x) return 1;
            }
            return 0;
        }
    }
}
