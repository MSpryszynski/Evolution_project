package agh.ics.oop;

public enum MapDirection {
    NORTH,
    NORTHWEST,
    NORTHEAST,
    SOUTH,
    SOUTHWEST,
    SOUTHEAST,
    WEST,
    EAST;
    public String toString() {
        switch(this){
            case NORTH -> { return "Północ"; }
            case SOUTH -> { return "Południe"; }
            case WEST -> { return "Zachód"; }
            case EAST -> { return "Wschód"; }
            default -> { return "Coś nie tak"; }
        }
    }

    public MapDirection previous(){
        switch(this){
            case NORTH -> { return WEST; }
            case SOUTH -> { return EAST; }
            case WEST -> { return SOUTH; }
            default -> {return NORTH; }
        }
    }

    public Vector2d toUnitVector(){
        switch(this){
            case NORTH -> { return new Vector2d(0,1); }
            case SOUTH -> { return new Vector2d(0,-1); }
            case WEST -> { return new Vector2d(-1,0); }
            default -> {return new Vector2d(1,0); }
        }
    }
}


