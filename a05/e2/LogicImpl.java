package a06.e2;

import java.util.Random;

public class LogicImpl implements Logic {

    private final int size;
    private final Random rand = new Random();
    private Position player;
    private Position enemy;

    private boolean neighbours(Position p1, Position p2) {
        return Math.abs(p1.x() - p2.x()) <= 1 && Math.abs(p1.y() - p2.y()) <= 1;
    }

    public LogicImpl(int size) {
        this.size = size;
        this.player = new Position(rand.nextInt(size), rand.nextInt(size));
        this.enemy = new Position(rand.nextInt(size), rand.nextInt(size));
        while (neighbours(player, enemy)) {
            this.enemy = new Position(rand.nextInt(size), rand.nextInt(size));           
        }
    }

    @Override
    public boolean isOver() {
        if (player.equals(enemy)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void move(Position pos) {
        if (neighbours(player, pos) && pos.x() < size && pos.y() < size && pos.x() >= 0 && pos.y() >= 0) {
            this.player = pos;
            Position newEnemyPos = new Position(rand.nextInt(size), rand.nextInt(size));
            int counter = 0;
            boolean noEscape = false;
            while (neighbours(player, newEnemyPos) || !neighbours(newEnemyPos, this.enemy)) {
                counter++;
                newEnemyPos = new Position(rand.nextInt(size), rand.nextInt(size));   
                if (counter > 1000) {
                    noEscape = true;
                    break;
                }   
            }
            if (!noEscape) {
            this.enemy = newEnemyPos;
            }
        }
    }

    @Override
    public String getValue(Position pos) {
        if (pos.equals(player)) {
            return "P";
        } else if (pos.equals(enemy)) {
            return "E";
        } else {
            return "";
        }
    }

}
