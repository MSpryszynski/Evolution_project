package agh.ics.oop;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);
    private static final int maxi=4;
    private static final int mini=0;


    @Override
    public String toString() {
        return "Zwierzak znajduje się na pozycji " + position + ", jest skierowany na " + orientation;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public boolean isAt(Vector2d position){
        return (this.position.follows(position) && this.position.precedes(position));
    }

    public void move(MoveDirection[] directions){
        for (MoveDirection direction:directions) {
            switch (direction) {
                case RIGHT:
                    orientation = orientation.next();
                    break;
                case LEFT:
                    orientation = orientation.previous();
                    break;
                case FORWARD:
                    if (orientation.toUnitVector().add(position).x <= maxi
                            && orientation.toUnitVector().add(position).x >= mini
                            && orientation.toUnitVector().add(position).y <= maxi
                            && orientation.toUnitVector().add(position).y >= mini) {
                        position = position.add(orientation.toUnitVector());
                    }
                    break;
                case BACKWARD:
                    if (orientation.toUnitVector().opposite().add(position).x <= maxi
                            && orientation.toUnitVector().opposite().add(position).x >= mini
                            && orientation.toUnitVector().opposite().add(position).y <= maxi
                            && orientation.toUnitVector().opposite().add(position).y >= mini) {
                        position = position.add(orientation.toUnitVector().opposite());
                    }
                    break;
            }
        }
    }
}
