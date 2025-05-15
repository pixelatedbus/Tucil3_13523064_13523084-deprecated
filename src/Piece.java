package src;
import java.util.ArrayList;
import java.util.List;

public class Piece {
    private char id;
    private List<Coords> position;

    public Piece(char id) {
        this.id = id;
        this.position = new ArrayList<>();
    }

    public char getId() {
        return id;
    }

    public void addCoord(Coords coord){ position.add(coord); }

    public List<Coords> getPosition() {
        return position;
    }

    public Boolean isHorizontal() {
        return position[0].getY() == position[1].getY();
    }

    public Boolean isVertical() {
        return position[0].getX() == position[1].getX();
    }
}
