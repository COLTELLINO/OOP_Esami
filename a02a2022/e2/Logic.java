package a02a.e2;

public interface Logic {

    public boolean isOver();
    
    public String getValue(Position pos);
    
    public void hit(Position pos);

    public void restart(Position pos);
}
