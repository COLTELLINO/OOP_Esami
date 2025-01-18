package a06.e2;

import javax.swing.JButton;

public interface Logics {

    public boolean neighbours(Position p1, Position p2);

    public void hit(Position position);

    public void move();

    public int getValue(Position position);

    public boolean isMoving();

    public boolean isOver();
}
