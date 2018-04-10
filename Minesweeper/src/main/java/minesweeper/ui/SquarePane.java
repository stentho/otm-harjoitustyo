package minesweeper.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import minesweeper.game.Square;

// Tässä luokassa määritellään Square-oliolle graafisia ominaisuuksia.
// Käytetään StackPanea, koska sen voi helposti lisätä MinesweeperUi-luokan gridPaneen.
public class SquarePane extends StackPane {
    
    private Square square;
    private Rectangle edge;
    private Text text = new Text();
    private boolean isOpen = false;

    public SquarePane(Square square, int SQUARE_SIZE) {

        this.square = square;
        edge = new Rectangle(SQUARE_SIZE - 2, SQUARE_SIZE - 2);
        edge.setFill(Color.web("0x2b2b2b"));
        edge.setStroke(Color.web("0x828282"));

        text.setFont(Font.font(18));
        if (square.isBomb()) {
            text.setText("X");
        } else {
            text.setText("");
        }
        text.setVisible(false);

        getChildren().add(edge);
        getChildren().add(text);
        setTranslateX(square.getX() * SQUARE_SIZE);
        setTranslateY(square.getY() * SQUARE_SIZE);
    }

    public boolean isOpen() {
        return square.IsOpen();
    }

    public Text getText() {
        return text;
    }

    public Rectangle getEdge() {
        return edge;
    }

    public Square getSquare() {
        return square;
    }

    public void setOpen(boolean bool) {
        isOpen = bool;
    }
    
    public void setText(String text) {
        this.text.setText(text);
    }
}
