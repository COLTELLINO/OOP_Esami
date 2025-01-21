package a05.e2;

import java.util.*;

public class LogicImpl implements Logic {

    private Position h;
    private Position c;
    private boolean turn; 
    private final int size;
    private final List<Position> disabled = new LinkedList<>();
    private final Random random = new Random();

    public LogicImpl(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i%2 == 0 && j%2 == 1) ||(i%2 == 1 && j%2 == 0)) {
                    disabled.add(new Position(i, j));
                } 
            }
        }
        c = new Position(random.nextInt(size - 1), random.nextInt(2));
        while (disabled.contains(c)) {
            c = new Position(random.nextInt(size - 1), random.nextInt(2));
        }
        h = new Position(random.nextInt(size - 1), size - 1);
        while (disabled.contains(h)) {
            h = new Position(random.nextInt(size - 1), size - 1); 
        }
    }

    private boolean neighbours(Position p1, Position p2) {
        return Math.abs(p1.x() - p2.x()) <= 1 && Math.abs(p1.y() - p2.y()) <= 1;
    }

    @Override
    public void isOver() {
        if (h.y() == 0) {
            System.out.println("Vittoria!");
            c = new Position(random.nextInt(size - 1), random.nextInt(2));
            while (disabled.contains(c)) {
                c = new Position(random.nextInt(size - 1), random.nextInt(2));
            }
            h = new Position(random.nextInt(size - 1), size - 1);
            while (disabled.contains(h)) {
                h = new Position(random.nextInt(size - 1), size - 1); 
            }
            turn = false;
        }
        if (h.equals(c) && turn) {
            System.out.println("Vittoria!");
            c = new Position(random.nextInt(size - 1), random.nextInt(2));
            while (disabled.contains(c)) {
                c = new Position(random.nextInt(size - 1), random.nextInt(2));
            }
            h = new Position(random.nextInt(size - 1), size - 1);
            while (disabled.contains(h)) {
                h = new Position(random.nextInt(size - 1), size - 1); 
            }
            turn = false;
        } else if (h.equals(c) && !turn) {
            System.out.println("Sconfitta!");
            c = new Position(random.nextInt(size - 1), random.nextInt(2));
            while (disabled.contains(c)) {
                c = new Position(random.nextInt(size - 1), random.nextInt(2));
            }
            h = new Position(random.nextInt(size - 1), size - 1);
            while (disabled.contains(h)) {
                h = new Position(random.nextInt(size - 1), size - 1); 
            }
            turn = false;
        }
    }

    @Override
    public void hit(Position pos) {
        if (neighbours(pos, h) && !disabled.contains(pos) && pos.y() < h.y()) {
            h = pos;
            if (h.equals(c)) {
                turn = true;
            }
            if (neighbours(c, h)) {
                h = c;
                turn = false;
            } else {
                if (c.x() > 0 &&  c.x() < size - 1) {
                    if (random.nextInt(2) == 0) {
                        c = new Position(c.x() + 1, c.y() + 1);
                    } else {
                        c = new Position(c.x() - 1, c.y() + 1);
                    }
                } else if (c.x() > 0 ) {
                    c = new Position(c.x() - 1, c.y() + 1);
                } else {
                    c = new Position(c.x() + 1, c.y() + 1);
                }
            }
        }
    }

    @Override
    public String getValue(Position pos) {
        if(pos.equals(c)) {
            return "C";
        } else if (pos.equals(h)) {
            return "H";
        } else {
            return "";
        }
    }

    @Override
    public boolean isDisabled(Position pos) {
        if (disabled.contains(pos)) {
            return true;
        } else {
            return false;
        }
    }

}
