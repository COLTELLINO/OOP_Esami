package a06.e2;

import java.util.LinkedList;
import java.util.List;

public class LogicImpl implements Logic {

    private final List<Position> asterisks = new LinkedList<>();
    private int size;
    private final List<Position> marks = new LinkedList<>();

    public LogicImpl(int size) {
        this.size = size;
    }

    public void drawAsterisks() {
        for (int y = 0; y < size; y++) {
            if ((y + 1) % 2 == 0) {
                for (int x = 0; x < size; x++) {
                    asterisks.add(new Position(x, y));
                }
            } else {
                for (int x = 0; x < size; x++) {
                    if ((x + 1) % 2 == 0) {
                        asterisks.add(new Position(x, y));
                    }
                }
            }
        }
    }

    public String getValue(Position pos) {
        if (asterisks.contains(pos)) {
            return "*";
        } else if (marks.contains(pos)) {
            return "O";
        } else {
            return "";
        }
    }

    @Override
    public void hit(Position pos) {
        if (asterisks.contains(pos) || marks.contains(pos)) {
            return;
        } else {
            marks.add(pos);
        }
    }

    public boolean isOver() {
        List<Position> revMarks = marks.reversed();
        if (revMarks.size() < 4) {
            return false;
        }
        if (Math.abs(revMarks.get(0).x() - revMarks.get(1).x()) <= 2 &&
        Math.abs(revMarks.get(1).x() - revMarks.get(2).x()) <= 2 &&
        Math.abs(revMarks.get(2).x() - revMarks.get(3).x()) <= 2 &&
        Math.abs(revMarks.get(3).x() - revMarks.get(1).x()) <= 2 &&
        Math.abs(revMarks.get(0).x() - revMarks.get(2).x()) <= 2 &&
        Math.abs(revMarks.get(0).x() - revMarks.get(3).x()) <= 2 &&
        Math.abs(revMarks.get(3).x() - revMarks.get(1).x()) <= 2 &&
        Math.abs(revMarks.get(2).x() - revMarks.get(1).x()) <= 2 &&
        Math.abs(revMarks.get(3).y() - revMarks.get(1).y()) <= 2 &&
        Math.abs(revMarks.get(1).y() - revMarks.get(2).y()) <= 2 &&
        Math.abs(revMarks.get(2).y() - revMarks.get(3).y()) <= 2 &&
        Math.abs(revMarks.get(0).y() - revMarks.get(1).y()) <= 2 &&
        Math.abs(revMarks.get(0).y() - revMarks.get(2).y()) <= 2 &&
        Math.abs(revMarks.get(0).y() - revMarks.get(3).y()) <= 2 &&
        Math.abs(revMarks.get(3).y() - revMarks.get(1).y()) <= 2 &&
        Math.abs(revMarks.get(2).y() - revMarks.get(1).y()) <= 2){
            return true;
        } else {
            return false;
        }
    }
}
