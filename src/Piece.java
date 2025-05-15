package src;

public class Piece {
    private String id;
    private Coords[] position;

    public Piece(String id, Coords[] position) {
        this.id = id;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public Coords[] getPosition() {
        return position;
    }

    public Boolean isHorizontal() {
        return position[0].getY() == position[1].getY();
    }

    public Boolean isVertical() {
        return position[0].getX() == position[1].getX();
    }
}
