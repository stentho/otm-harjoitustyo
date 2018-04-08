
package minesweeper.game;


public class Square {
    int x;
    int y;
    boolean bomb;

    public Square(int x, int y, boolean bomb) {
        this.x = x;
        this.y = y;
        this.bomb = bomb;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", bomb: " + bomb;
    }
    
    
}
