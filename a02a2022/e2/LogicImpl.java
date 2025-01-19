package a02a.e2;

import java.util.*;

public class LogicImpl implements Logic {

    private final int size; 
    private final List<Position> marks = new LinkedList<>();

    public LogicImpl(int size) {
        this.size = size;
    } 

    @Override
    public boolean isOver() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Position pos = new Position(i, j);
                boolean covered = false;
                for (Position position : marks) {
                    List<Position> tmp = new LinkedList<>();
                    for (int k = 0; k < size; k++) {
                        tmp.add(new Position(position.x() + k, position.y() + k));
                        tmp.add(new Position(position.x() - k, position.y() + k));
                        tmp.add(new Position(position.x() + k, position.y() - k));
                        tmp.add(new Position(position.x() - k, position.y() - k));
                    }
                    if(tmp.contains(pos)) {
                        covered = true;
                    }
                }
                if (!covered) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public String getValue(Position pos) {
        if (marks.contains(pos)) {
            return "B";
        } else {
            return "";
        }
    }
    @Override
    public void hit(Position pos) {
        boolean free = true;
        for (Position position : marks) {
            List<Position> tmp = new LinkedList<>();
            for (int i = 0; i <= size; i++) {
                tmp.add(new Position(position.x() + i, position.y() + i));
                tmp.add(new Position(position.x() - i, position.y() + i));
                tmp.add(new Position(position.x() + i, position.y() - i));
                tmp.add(new Position(position.x() - i, position.y() - i));
            }
            if(tmp.contains(pos)) {
                free = false;
                break;
            }
        }
        if (!marks.contains(pos) && free) {
            marks.add(pos);
        }
    }

    @Override
    public void restart(Position pos) {
        if (this.marks.contains(pos)) {
            this.marks.clear();
        }
    }

}
