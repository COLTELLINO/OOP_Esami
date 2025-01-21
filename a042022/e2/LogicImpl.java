package a04.e2;

import java.awt.TrayIcon.MessageType;
import java.util.*;

public class LogicImpl implements Logic {

    private Position k;
    private Position r;
    private Random random = new Random();
    private boolean turn;
    private final int size;

    public LogicImpl(int size) {
        this.size = size;
        k = new Position(random.nextInt(size - 1), random.nextInt(size - 1));
        r = new Position(random.nextInt(size - 1), random.nextInt(size - 1));
        while (r.equals(k)) {
            r = new Position(random.nextInt(size - 1), random.nextInt(size - 1));         
        }
    }

    private boolean neighbours(Position p1, Position p2) {
        return Math.abs(p1.x() - p2.x()) <= 1 && Math.abs(p1.y() - p2.y()) <= 1;
    }

    @Override
    public void isOver() {
        if (r.equals(k) && turn) {
            System.out.println("Vittoria!");
            k = new Position(random.nextInt(size - 1), random.nextInt(size - 1));
            r = new Position(random.nextInt(size - 1), random.nextInt(size - 1));
            while (r.equals(k)) {
                r = new Position(random.nextInt(size - 1), random.nextInt(size - 1));         
            }
            turn = false;
        } else if (r.equals(k) && !turn) {
            System.out.println("Sconfitta!");
            k = new Position(random.nextInt(size - 1), random.nextInt(size - 1));
            r = new Position(random.nextInt(size - 1), random.nextInt(size - 1));
            while (r.equals(k)) {
                r = new Position(random.nextInt(size - 1), random.nextInt(size - 1));         
            }
            turn = false;
        }
    }

    @Override
    public String getValue(Position pos) {
        if (pos.equals(k)) {
            return "K";
        } else if (pos.equals(r)) {
            return "R";
        } else {
            return "";
        }
    }

    @Override
    public void hit(Position pos) {
        if (pos.x() == k.x() && pos.y() > k.y()) {
        } else if (pos.y() == k.y() && pos.x() > k.x()) {
        } else {
        if (!pos.equals(r) && pos.x() == r.x() || pos.y() == r.y()) {
           r = pos;
           if (r.equals(k)) {
                turn = true;
            } else if (neighbours(r, k)) {
                turn = false;
                k = r;
            } else {
                Position lastPos = k;
                k = new Position(random.nextInt(size - 1), random.nextInt(size - 1));
                while (!neighbours(k, lastPos)) {
                    k = new Position(random.nextInt(size - 1), random.nextInt(size - 1));
                }
            }
        }
    }
}
}
