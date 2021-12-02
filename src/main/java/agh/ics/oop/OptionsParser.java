package agh.ics.oop;


public class OptionsParser {
    public MoveDirection[] parse(String[] directions){
        int len = directions.length;
        MoveDirection[] result = new MoveDirection[len];
        int counter = 0;
        for (String direction : directions) {
            switch (direction) {
                case "f", "forward" -> result[counter] = MoveDirection.FORWARD;
                case "b", "backward" ->result[counter] = MoveDirection.BACKWARD;
                case "l", "left" ->
                    result[counter] = MoveDirection.LEFT;
                case "r", "right" -> result[counter] = MoveDirection.RIGHT;
                default -> throw new IllegalArgumentException(direction + " is not legal move specification");
            }
            counter += 1;
        }
        return result;
    }
}
