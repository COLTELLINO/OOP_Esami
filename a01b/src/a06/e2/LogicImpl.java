package a06.e2;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    private List<Position> marks = new LinkedList<>();
    private boolean over = false;
        private boolean moving = false;
        private boolean right = false;
        private int size;
        
        public LogicImpl(int size) {
            this.size = size;
        }
        
        @Override
        public boolean isOver() {
            return this.over;
        }
    
        @Override
        public boolean isMoving() {
            return this.moving;
        }
    
        @Override
        public void hit(Position pos) {
            if (marks.size() < 5) {
                marks.add(pos);
            } else if (!right) {
                moving = true;
                moveLeft();
            } else {
                moveRight();
            }
        }
        
        @Override
        public int getValue(Position pos) {
            return marks.indexOf(pos) + 1;
        }
        
        @Override
        public void moveRight() {
            this.marks = marks.stream().map(mark -> new Position(mark.x() + 1, mark.y())).collect(Collectors.toList());
            if(this.marks.stream().anyMatch(p -> p.x() == size)) {
                over = true;
        }
    }

    @Override
    public void moveLeft() {
        this.marks = marks.stream().map(mark -> new Position(mark.x() - 1, mark.y())).collect(Collectors.toList());
        if(this.marks.stream().anyMatch(p -> p.x() == 0)) {
            right = true;
        }
    }
}
