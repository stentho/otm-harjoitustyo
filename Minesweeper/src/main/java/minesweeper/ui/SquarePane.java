package minesweeper.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import minesweeper.game.Square;

// Tässä luokassa määritellään Square-oliolle graafisia ominaisuuksia.
// Käytetään StackPanea, koska sen voi helposti lisätä MinesweeperUi-luokan gridPaneen.
public class SquarePane extends StackPane {

    private Square square;
    private Rectangle tile;
    private Rectangle edge;
    private Polygon flag;
    private Rectangle flagpole;
    private Rectangle flagbase;
    private Text text = new Text();
    private boolean isOpen = false;
    private int SQUARE_SIZE;

    public SquarePane(Square square, int SQUARE_SIZE) {
        this.SQUARE_SIZE = SQUARE_SIZE;
        this.square = square;

        setUpSquareGraphics();
        setUpFlagGraphics();

        getChildren().add(tile);
        getChildren().add(text);
        getChildren().add(edge);
        getChildren().add(flagbase);
        getChildren().add(flagpole);
        getChildren().add(flag);
        setTranslateX(square.getX() * SQUARE_SIZE);
        setTranslateY(square.getY() * SQUARE_SIZE);
    }

    private void setUpSquareGraphics() {
        tile = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
        tile.setFill(Color.web("0xcccccc"));
        tile.setStroke(Color.web("0x828282"));
        tile.setStrokeWidth(2);

        edge = new Rectangle(SQUARE_SIZE - 4, SQUARE_SIZE - 4);
        edge.setFill(Color.web("0xcccccc"));
        edge.setStrokeWidth(4);
        edge.setStroke(Color.web("0xf2f2f2"));

        text.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        
        if (square.isBomb()) {
            text.setText("\u23FA");
        } else {
            text.setText("");
        }
//        text.setVisible(false);
    }

    private void setUpFlagGraphics() {
        flag = new Polygon();
        flag.getPoints().addAll(new Double[]{
            0.0, 10.0,
            15.0, 0.0,
            15.0, 15.0
        });
        flag.setTranslateY(-8);
        flag.setTranslateX(-6);
        flag.setFill(Color.RED);
        flag.setVisible(false);

        flagpole = new Rectangle(3, SQUARE_SIZE * 0.5);
        flagpole.setFill(Color.RED);
        flagpole.setVisible(false);

        flagbase = new Rectangle(SQUARE_SIZE * 0.5, SQUARE_SIZE * 0.1);
        flagbase.setTranslateY(12);
        flagbase.setVisible(false);
    }

    public void showFlag(Boolean value) {
        flag.setVisible(value);
        flagpole.setVisible(value);
        flagbase.setVisible(value);
    }

    public void showEdge(Boolean value) {
        edge.setVisible(value);
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
    
    public Polygon getFlag() {
        return flag;
    }

    public Rectangle getTile() {
        return tile;
    }

    public void setOpen(boolean bool) {
        square.setIsOpen(bool);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
