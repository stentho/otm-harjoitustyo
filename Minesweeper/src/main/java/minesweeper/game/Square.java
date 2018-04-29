package minesweeper.game;

/**
 * Yksittäistä peliruutua kuvaava luokka.
 */

public class Square {

    private int x;
    private int y;
    boolean bomb;
    boolean open;
    private int adjacentBombs;

    public Square(int x, int y, boolean bomb) {
        if (x >= 0) {
            this.x = x;
        } else {
            this.x = 0;
        }
        
        if (y >= 0) {
            this.y = y;
        } else {
            this.y = 0;
        }
        
        this.bomb = bomb;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBomb() {
        return bomb;
    }

    public int getAdjacentBombs() {
        return adjacentBombs;
    }

    public boolean isOpen() {
        return open;
    }

    public void setX(int x) {
        if (x >= 0) {
            this.x = x;
        }
    }

    public void setY(int y) {
        if (y >= 0) {
            this.y = y;
        }
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", bomb: " + bomb + ", open: " + open;
    }
}
