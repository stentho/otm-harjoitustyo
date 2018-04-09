
package minesweeper.game;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;


public class MinesweeperGame {
    private Square[][] field;

    public MinesweeperGame(int W, int H) {
        Square[][] field = new Square[W][H];
    }
    
    
    
    // tähän luokkaan luodaan pelilogiikka
    
    public Square[][] createField(Pane pane) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Square square = new Square(x,y, Math.random() < 0.2);
                field[x][y] = square;
            }
        }
        return field;
    }
    
    
}
