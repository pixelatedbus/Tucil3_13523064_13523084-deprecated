package src;

public class Piece {
    private Character id;
    private Coords[] position;

    public Piece(Character id, Coords[] position) {
        this.id = id;
        this.position = position;
    }

    public Character getId() {
        return id;
    }

    public Coords[] getPosition() {
        return position;
    }

    public Boolean isHorizontal() {
        return position[0].getY() == position[1].getY();
    }

    public void move(boolean forward){
        int mult = forward ? 1 : -1;
        if (isHorizontal()) {
            for (Coords coord : position) {
                coord.addX(mult);
            }
        } else {
            for (Coords coord : position) {
                coord.addY(mult);
            }
        }
    }

    public boolean isIntersecting(Coords other) {
        for (Coords coord : position) {
            if (coord.isIntersecting(other)) {
                return true;
            }
        }
        return false;
    }
}
// "A:5:2;B:4:3" -> state