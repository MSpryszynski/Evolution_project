package agh.ics.oop;

import java.util.Arrays;

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
                default ->counter -= 1;
            }
            counter += 1;
        }
        return Arrays.copyOfRange(result,0, counter);
    }
}
