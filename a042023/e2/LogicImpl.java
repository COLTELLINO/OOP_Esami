package a06.e2;

import java.util.*;

public class LogicImpl implements Logic {

    private final List<Position> marks = new LinkedList<>();
    private final int size;
    private final Random rand = new Random();
    public LogicImpl(int size) {
        this.size = size;
    }

    public boolean isOver() {

        return canReachLastRow(marks.get(0), marks, this.size);
    }
    
    private boolean canReachLastRow(Position current, List<Position> selectedCells, int size) {

        if (current.x() == size - 1) {
            return true;
        }

        int nextRow = current.x() + 1;
        Position left = new Position(nextRow, current.y() - 1);
        Position right = new Position(nextRow, current.y() + 1);
    
        boolean leftPath = selectedCells.contains(left) && canReachLastRow(left, selectedCells, size);
        boolean rightPath = selectedCells.contains(right) && canReachLastRow(right, selectedCells, size);
    
        return (leftPath || rightPath);
    }
    

    @Override
    public void hit(Position pos) {
        if (!marks.contains(pos)) {
            marks.add(pos);
        }
    }

    @Override
    public void start() {
        marks.add(new Position(0, rand.nextInt(size - 1)));
    }

    @Override
    public String getValue(Position pos) {
        if (marks.contains(pos)) {
            return "*";
        } else {
            return "";
        }
    }

}
