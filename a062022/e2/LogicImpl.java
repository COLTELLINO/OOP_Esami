package a06.e2;

import java.util.*;

public class LogicImpl implements Logic {

    private final int size;
    private final Map<Position,Integer> values = new HashMap<>();
    private final Random random = new Random();
    
    public LogicImpl(int size) {
        this.size = size;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Position pos = new Position(j, i);
                int value = random.nextInt(2) + 1;
                this.values.put(pos, value);
            }
        }
    }

    @Override
    public boolean isOver() {
        boolean over = true;
        for (int j = 0; j < size; j++) {
            for (int i = size - 1; i > 0; i--) {
                if (values.get(new Position(j, i)) == values.get(new Position(j, i - 1)) && values.get(new Position(j, i)) != 0) {
                    over = false;
                    break;
                }
            }
        }
        return over;
    }

    @Override
    public void hit() {
        for (int j = 0; j < size; j++) {
            for (int i = size - 1; i > 0; i--) {
                int value = values.get(new Position(j, i - 1));
                if (values.get(new Position(j, i)) == value && values.get(new Position(j, i)) != 0 ) {
                    values.replace(new Position(j, i - 1), value*2);
                    for (int k = size - 1; k > 0; k--) {
                        values.replace(new Position(j, k), values.get(new Position(j, k - 1)));
                    }
                    values.replace(new Position(j, 0), 0);
                    break;
                }
            }
        }
    }

    @Override
    public int getValue(Position pos) {
        if (values.containsKey(pos)) {
            return values.get(pos);
        } else {
            return 0;
        }
    }

}
