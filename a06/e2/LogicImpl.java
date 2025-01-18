package a06.e2;

import java.util.*;

public class LogicImpl implements Logic {

    private final int size;
    Map<Position,Integer> cells = new HashMap<>();
    private final Random random = new Random();
    List<Position> showing = new LinkedList<>();
    List<Position> found = new LinkedList<>();

    public LogicImpl(int size) {
        this.size = size;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                cells.put(new Position(i, j), random.nextInt(6) + 1);
            }
        }
    }

    @Override
    public boolean isOver() {
        List<Integer> tmp = new LinkedList<>();
        for (Position position : cells.keySet()) {
            if (!found.contains(position)) {
                if (tmp.contains(cells.get(position))) {
                    return false;
                }
                tmp.add(cells.get(position));
            }
        }
        return true;
    }

    @Override
    public void hit(Position pos) {
        if (showing.size() > 1) {
            showing.clear();
        }  
        if (showing.isEmpty()) {
            showing.add(pos);
        } else if (cells.get(pos).equals(cells.get(showing.get(0))) && !pos.equals(showing.get(0))) {
            found.add(pos);
            found.add(showing.get(0));
            showing.clear();
        } else {
            showing.add(pos);
        }
    }

    @Override
    public int getValue(Position pos) {
        if (showing.contains(pos) || found.contains(pos)) {
            return cells.get(pos);
        } else {
            return 0;
        }
    }  
}
