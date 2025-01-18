package a06.e2;

import java.util.*;;

public class LogicImpl implements Logic {

    List<Position> marks = new LinkedList<>();
    private final int size;
    private boolean over;

    public LogicImpl(int size) {    
        this.size = size;
    } 

    @Override
    public boolean isOver() {
        return this.over;
    }

    @Override
    public void hit(Position pos) {
        if (marks.contains(pos)) {
            marks.remove(pos);
        } else {
            marks.add(pos);
        }
    }

    @Override
    public void hitCheck() {

        for (var mark : marks) {

            int counter = 0;
            int counterMoves = 0;

            while (counterMoves < size) {

                if (mark.x() - counterMoves >= 0 && mark.y() - counterMoves >= 0) {
                    if (marks.contains(new Position(mark.x() - counterMoves, mark.y() - counterMoves))) {
                        counter++;
                    }
                }
                if (mark.x() + counterMoves <= size && mark.y() + counterMoves <= size) {
                    if (marks.contains(new Position(mark.x() + counterMoves, mark.y() + counterMoves))) {
                        counter++;
                    }
                }
                counterMoves++;
            } 

            if (counter == 4) {
                this.over = true;
            }
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
