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


    public Vector2d toUnitVector(){
        switch(this){
            case NORTH -> { return new Vector2d(0,1); }
            case NORTHEAST -> {return new Vector2d(1,1);}
            case EAST -> {return new Vector2d(1,0);}
            case SOUTHEAST -> {return new Vector2d(1,-1);}
            case SOUTH -> { return new Vector2d(0,-1); }
            case SOUTHWEST -> {return new Vector2d(-1,-1);}
            case WEST -> { return new Vector2d(-1,0); }
            case NORTHWEST -> {return new Vector2d(-1,1);}
            default -> {return new Vector2d(0,0);}
        }
    }
}


