package minesweeper.game;

import javafx.scene.layout.StackPane;

// Yksi Square-olio on yksi ruutu pelissä. Tässä luokassa määritellään sen logiikka.
// SquarePane hakee istelleen oliomuuttujakseen Square-olion.
public class Square {

    private int x;
    private int y;
    boolean bomb;
    boolean isOpen;
    private int adjacentBombs;

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

    public boolean isBomb() {
        return bomb;
    }

    public int getAdjacentBombs() {
        return adjacentBombs;
    }

    public boolean IsOpen() {
        return isOpen;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", bomb: " + bomb;
    }
}
