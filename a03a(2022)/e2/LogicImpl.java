package a03a.e2;

import java.util.Random;

public class LogicImpl implements Logic {

    private Position player;
    private Position computer;
    private final int size;
    private final Random random = new Random();
    private boolean turn = true;

    public LogicImpl(int size) {
        this.size = size;
        player = new Position(random.nextInt(size), random.nextInt(size));
        computer = new Position(random.nextInt(size), random.nextInt(size));
        while (computer.x() == player.x() || computer.y() == player.y()) {
            computer = new Position(random.nextInt(size), random.nextInt(size));
        }
    }

    @Override
    public void isOver() {
        if (player.equals(computer) && turn) {
            System.out.println("Umano vince");
            player = new Position(random.nextInt(size), random.nextInt(size));
            computer = new Position(random.nextInt(size), random.nextInt(size));
            while (computer.x() == player.x() && computer.y() == player.y()) {
                computer = new Position(random.nextInt(size), random.nextInt(size));
            }
        } else if (player.equals(computer) && !turn) {
            System.out.println("Computer vince");
            player = new Position(random.nextInt(size), random.nextInt(size));
            computer = new Position(random.nextInt(size), random.nextInt(size));
            while (computer.x() == player.x() && computer.y() == player.y()) {
                computer = new Position(random.nextInt(size), random.nextInt(size));
            }
        }
    }

    @Override
    public void hit(Position pos) {
        if (!pos.equals(player) && pos.x() == player.x() || pos.y() == player.y()) {
            this.player = pos;
            if (player.equals(computer)) {
                computer = player;
                this.turn = true;
            } else if (player.x() == computer.x() || player.y() == computer.y()) {
                computer = player;
                this.turn = false;
            } else {
                Position lastComputerPos = computer;
                computer = new Position(random.nextInt(size), random.nextInt(size));
                while (computer.x() != lastComputerPos.x() && computer.y() != lastComputerPos.y() || computer.equals(lastComputerPos) ) {
                    computer = new Position(random.nextInt(size), random.nextInt(size));
                }
            } 
        }
    }

    @Override
    public String getValue(Position pos) {
        if (pos.equals(player)) {
            return "*";
        } else if (pos.equals(computer)) {
            return "O";
        } else {
            return "";
        }
    }

}
