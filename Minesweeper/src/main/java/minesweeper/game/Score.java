package minesweeper.game;

/**
 * Yksittäistä tulosta kuvaava luokka.
 */

public class Score {
    private int id;
    private String name;
    private int height, width;
    private double mines, time;

    public Score(int id, String name, int height, int width, double mines, double time) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.time = time;
    }
    
    public Score(String name, int height, int width, double mines, double time) {
        this.id = 0;
        this.name = name;
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getMines() {
        return mines;
    }

    public double getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMines(double mines) {
        this.mines = mines;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Nimi: " + name + ", Koko: " + width + " x " + height + 
                ", Miinoja: " + mines * 100 + ", Aika: " + time;
    }
}
