package a01a.e2;

import java.util.*;

public class LogicImpl implements Logic {
    
    private final List<Position> marks = new LinkedList<>();
    private final List<Boolean> lastClicks = new LinkedList<>();

    @Override
    public boolean isOver() {
        if (lastClicks.size() >= 3 && marks.size() >= 3) {
        boolean consecutive = false;
        if (lastClicks.get(lastClicks.size() - 1) && lastClicks.get(lastClicks.size() - 2) && lastClicks.get(lastClicks.size() - 3)) {
            consecutive = true;
        } else {
            consecutive = false;
        }


        List<Position> lastThree = new LinkedList<>();
        lastThree.add(marks.get(marks.size() - 1));
        lastThree.add(marks.get(marks.size() - 2));
        lastThree.add(marks.get(marks.size() - 3));

        if (consecutive) {
            for (Position position : marks) {
                if (lastThree.contains(new Position(position.x() + 1, position.y() - 1)) && lastThree.contains(new Position(position.x() - 1, position.y() + 1)) && lastThree.contains(position)) {
                    return true;
                } else if (lastThree.contains(new Position(position.x() - 1, position.y() - 1)) && lastThree.contains(new Position(position.x() + 1, position.y() + 1)) && lastThree.contains(position)) {
                    return true;
                }
            }
        }
    }
        return false;
    }

    @Override
    public void hit(Position pos) {
        if (marks.contains(pos)) {
            marks.remove(pos);
            lastClicks.add(false);
        } else {
            marks.add(pos);
            lastClicks.add(true);
        }
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
