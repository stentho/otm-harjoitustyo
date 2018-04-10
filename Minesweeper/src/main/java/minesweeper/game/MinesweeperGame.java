package minesweeper.game;

import java.util.ArrayList;
import java.util.Arrays;

// Tässä luokassa määritellään pelilogiikka.
public class MinesweeperGame {

    private Square[][] field;
    int squaresX;
    int squaresY;

    public MinesweeperGame(int x, int y) {
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
    }

    public int getSquaresX() {
        return squaresX;
    }

    public int getSquaresY() {
        return squaresY;
    }

    // luodaan pelin kenttä luomalla array täynnä Square-olioita. Yksi Square on
    // yksi ruutu pelissä. Tässä on määritelty 20% todennäköisyys, että mikä
    // tahansa ruutu on pommi. Sitä varmaan muutetaan tulevaisuudessa.
    public Square[][] createField() {
        field = new Square[squaresX][squaresY];
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {
                Square square = new Square(x, y, Math.random() < 0.2);
                field[x][y] = square;
            }
        }
        return field;
    }

    // lasketaan numerot ja liitetään ne kenttään (field-array)
    public Square[][] calculateNumbersForField() {
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

    // tässä tehdään ArrayList kokonaisluvuista, millä saadaan viereisten
    // ruutujen koordinaatit. Esim jos nykyinen ruutu on (4,5) paikassa,
    // niin sen suoraan yläpuolella oleva ruutu on (0,-1) suhteessa tähän jne.
    public ArrayList<Square> getAdjacentSquares(Square square) {
        ArrayList<Square> adjacentSquares = new ArrayList<>();

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

        for (int i = 0; i < constants.size(); i++) {

            int adjacentSquareX = square.getX() + constants.get(i);
            i++;
            int adjacentSquareY = square.getY() + constants.get(i);

            // jos ei ole pelin reunan ulkopuolella, lisätään ArrayListiin.
            if (adjacentSquareX >= 0 && adjacentSquareX < squaresX
                    && adjacentSquareY >= 0 && adjacentSquareY < squaresY) {
                adjacentSquares.add(field[adjacentSquareX][adjacentSquareY]);
            }
        }
        return adjacentSquares;
    }

    // lasketaan edellisen metodin avulla kuinka monta pommia on ruudun ympärillä.
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
}
