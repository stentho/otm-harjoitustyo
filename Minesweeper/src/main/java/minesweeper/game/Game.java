package minesweeper.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Yksittäistä peliä kuvaava luokka.
 */
public class Game {

    private Square[][] field;
    private int squaresX;
    private int squaresY;
    private double mineFreq;
    private int time;
    private int remainingTime;
    private IntegerProperty remainingTimeProperty;
    private Timer timer;
    private int gameState;

    Game(int x, int y, double mF) {
        if (x >= 0) {
            squaresX = x;
        } else {
            squaresX = 0;
        }

        if (y >= 0) {
            squaresY = y;
        } else {
            squaresY = 0;
        }

        if (mF > 1) {
            mineFreq = 1;
        } else if (mF < 0) {
            mineFreq = 0;
        } else {
            mineFreq = mF;
        }
    }

    public Game(int x, int y, double mF, int time) {
        this(x, y, mF);

        if (time <= 0) {
            this.time = 0;
        } else {
            this.time = time;
        }
        timer = new Timer();
        remainingTime = 0;
        remainingTimeProperty = new SimpleIntegerProperty();
        gameState = 0;
    }

    public int getSquaresX() {
        return squaresX;
    }

    public int getSquaresY() {
        return squaresY;
    }

    public double getMineFreq() {
        return mineFreq;
    }

    public Square[][] getField() {
        return field;
    }

    public int getTime() {
        return time;
    }

    public int getGameState() {
        return gameState;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public IntegerProperty getRemainingTimeProperty() {
        return remainingTimeProperty;
    }

    public void setSquaresX(int squaresX) {
        this.squaresX = squaresX;
    }

    public void setSquaresY(int squaresY) {
        this.squaresY = squaresY;
    }

    public void setMineFreq(double mineFreq) {
        this.mineFreq = mineFreq;
    }

    public void setField(Square[][] field) {
        this.field = field;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    
    public void setRemainingTimeProperty(int remainingTime) {
        this.remainingTimeProperty.setValue(remainingTime);
    }


    @Override
    public String toString() {
        return "Minesweeper (leveys " + squaresX + ", korkeus " + squaresY + ", miinoja " + (int) (mineFreq * 100) + "%)";
    }

    // luodaan pelin kenttä luomalla array täynnä Square-olioita. Yksi Square on
    // yksi ruutu pelissä. Tässä on määritelty 20% todennäköisyys, että mikä
    // tahansa ruutu on pommi. Sitä varmaan muutetaan tulevaisuudessa.
    public Square[][] createField() {
        field = new Square[squaresX][squaresY];
        placeBombs();
        calculateNumbersForField();
        return field;
    }

    private Square[][] placeBombs() {
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {
                Square square = new Square(x, y, Math.random() < mineFreq);
                field[x][y] = square;
            }
        }
        return field;
    }

    // lasketaan numerot ja liitetään ne kenttään (field-array)
    private Square[][] calculateNumbersForField() {
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {
                Square square = field[x][y];

                int b = calculateAdjacentBombs(square);
                square.setAdjacentBombs(b);

                field[x][y] = square;
            }
        }
        return field;
    }

    // Tämä metodi palauttaa ArrayListin parametriruudun vierekkäistä ruuduista.
    public ArrayList<Square> getAdjacentSquares(Square square) {
        ArrayList<Square> adjacentSquares = new ArrayList<>();

        // tässä tehdään ArrayList kokonaisluvuista, millä saadaan viereisten
        // ruutujen koordinaatit. Esim jos nykyinen ruutu on (4,5) paikassa,
        // niin sen suoraan yläpuolella oleva ruutu on (0,-1) suhteessa tähän jne.
        ArrayList<Integer> constants = getRelativeAdjacentPositions();

        for (int i = 0; i < constants.size(); i++) {

            int adjacentSquareX = square.getX() + constants.get(i);
            i++;
            int adjacentSquareY = square.getY() + constants.get(i);

            // jos ei ole pelin reunojen ulkopuolella, lisätään ArrayListiin.
            if (adjacentSquareX >= 0 && adjacentSquareX < squaresX
                    && adjacentSquareY >= 0 && adjacentSquareY < squaresY) {
                adjacentSquares.add(field[adjacentSquareX][adjacentSquareY]);
            }
        }
        return adjacentSquares;
    }

    // lasketaan edellisen metodin avulla kuinka monta pommia on parametriruudun 
    // ympärillä.
    public int calculateAdjacentBombs(Square square) {
        ArrayList<Square> adjacentSquares = getAdjacentSquares(square);
        int b = 0;
        for (int i = 0; i < adjacentSquares.size(); i++) {
            if (adjacentSquares.get(i).isBomb()) {
                b++;
            }
        }
        if (b > 0) {
            square.setAdjacentBombs(b);
        }
        return b;
    }

    // Tässä metodissa avataan kaikki tyhjät vierekkäiset ruudut, sekä niiden
    // ympärillä olevat ruudut (ei pommeja kuitenkaan).
    public void openAdjacentSquaresIfZero(Square square) {
        int b = calculateAdjacentBombs(square);

        if (b != 0 && !square.isBomb()) {
            square.setOpen(true);
            return;
        }

        if (b == 0 && !square.isOpen()) {
            square.setOpen(true);
            ArrayList<Square> adjSq = getAdjacentSquares(square);

            for (int i = 0; i < adjSq.size(); i++) {
                Square sq = adjSq.get(i);
                openAdjacentSquaresIfZero(sq);
            }
        }
    }

    // tässä tehdään ArrayList kokonaisluvuista, millä saadaan viereisten
    // ruutujen koordinaatit. Esim jos nykyinen ruutu on (4,5) paikassa,
    // niin sen suoraan yläpuolella oleva ruutu on (0,-1) suhteessa tähän jne.
    public ArrayList<Integer> getRelativeAdjacentPositions() {
        ArrayList<Integer> constants = new ArrayList<>();
        constants.addAll(Arrays.asList(
                -1, -1,
                0, -1,
                1, -1,
                -1, 0,
                1, 0,
                -1, 1,
                0, 1,
                1, 1));
        return constants;
    }

    public int numberOfUnopenedSquares() {
        int i = 0;
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {
                Square square = field[x][y];
                if (!square.isOpen()) {
                    i++;
                }
            }
        }
        return i;
    }

    // Palauttaa miinojen määrän.
    public int numberOfBombs() {
        int i = 0;
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {
                Square square = field[x][y];
                if (square.isBomb()) {
                    i++;
                }
            }
        }
        return i;
    }

    public boolean isGameWon() {
        return this.numberOfUnopenedSquares() == this.numberOfBombs();
    }

    public void gameEnd() {
        timer.cancel();
        if (isGameWon()) {
            gameState = 1;
        } else {
            gameState = -1;
        }
    }

    public void startCountdown() {
        remainingTime = time;
        remainingTimeProperty = new SimpleIntegerProperty(remainingTime);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        remainingTime = updateTime();
                        remainingTimeProperty.setValue(remainingTime);
                    }
                });
            }

        }, 1000, 1000);
    }

    private int updateTime() {
        if (remainingTime == 1) {
            gameEnd();
        }
        return --remainingTime;
    }
}
