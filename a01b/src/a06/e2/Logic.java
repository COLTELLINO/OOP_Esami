package a06.e2;

public interface Logic {
    boolean isOver();
    boolean isMoving();
    void moveRight();
    void moveLeft();
    void hit(Position pos);
    int getValue(Position pos);
}
