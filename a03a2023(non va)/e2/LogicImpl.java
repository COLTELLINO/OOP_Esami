package a06.e2;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {

    private final int width;
    private final int height;
    private final List<Position> shoot = new LinkedList<>();
    private Position startPos;
    Random rand = new Random();

    public LogicImpl(int width, int height) {
        this.width = width;
        this.height = height;
        this.startPos = new Position(this.width - 1, rand.nextInt(this.height));
    }

    @Override
    public void hit(Position pos) {
        return;
    }

    @Override
    public String getValue(Position pos) {
        if (pos.equals(this.startPos)) {
            return "O";
        } else {
            return "";
        }
    }

    @Override
    public boolean isOver() {
        return false;
    }
}
