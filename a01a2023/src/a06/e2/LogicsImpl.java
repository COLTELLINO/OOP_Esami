package a06.e2;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {
    
    private final int size;
    private List<Position> marks = new LinkedList<>();
    private boolean over = false;
    private boolean moving = false;

    public LogicsImpl(int size) {
        this.size = size;
    } 

    @Override
    public boolean neighbours(Position p1, Position p2) {
        return Math.abs(p1.x()-p2.x()) <= 1 && Math.abs(p1.y()-p2.y()) <= 1;
    }

    @Override
    public void hit(Position position) {
        if (marks.stream().anyMatch(p -> neighbours(p, position))) {
            move(); 
        } else {
            marks.add(position);
        }
    }

    @Override
    public void move() {
        this.marks = marks.stream().map(p -> new Position(p.x() + 1, p.y() - 1)).collect(Collectors.toList());
        this.moving = true;
        if (marks.stream().anyMatch(p -> p.x() == this.size || p.y() == -1)) {
            this.over = true;
        }
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public int getValue(Position position) {
        return marks.indexOf(position) + 1;
    }

    @Override
    public boolean isMoving() {
        return this.moving;
    }

}
