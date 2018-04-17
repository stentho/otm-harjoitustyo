package minesweeper.ui;

import java.util.Arrays;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import minesweeper.game.MinesweeperGame;
import minesweeper.game.Square;

public class MinesweeperUi extends Application {

    MinesweeperGame game;
    final static int SQUARE_SIZE = 40;
    Scene scene;
    BorderPane mainMenu;
    Pane gridPane;
    int squaresX;
    int squaresY;

    // tässä luodaan ruudukko
    public Pane createGrid() {
        gridPane = new Pane();
        gridPane.setPrefSize(SQUARE_SIZE * squaresX, SQUARE_SIZE * squaresY);

        //ensin luodaan kenttä (logiikkatasolla), ja plaseerataan pommit satunnaisesti.
        game.createField();

        //sitten lasketaan ruutujen numeroarvot.
        Square[][] grid = game.calculateNumbersForField();

        //tehdään kentästä visuaalinen ruudukko
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {

                SquarePane squarePane = new SquarePane(grid[x][y], SQUARE_SIZE);

                squarePane.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton btn = event.getButton();
                        if (btn == MouseButton.PRIMARY) {
                            leftClick(squarePane);
                        } else if (btn == MouseButton.SECONDARY) {
                            rightClick(squarePane);
                        }
                    }
                });

                int b = squarePane.getSquare().getAdjacentBombs();

                //mikäli vierekkäisten pommien määrä on positiivinen, ja
                //ei ole itse pommi, niin kirjoitetaan numero ruutuun.
                if (b > 0 && !squarePane.getSquare().isBomb()) {
                    squarePane.setText(Integer.toString(b));
                    setSquareTextColor(squarePane, b);
                }
                gridPane.getChildren().add(squarePane);
            }
        }
        return gridPane;
    }

    // tässä asetetaan numeroille omat värit, niin että ne erottuvat toisistaan.
    private static void setSquareTextColor(SquarePane squarePane, int b) {
        if (b == 1) {
            squarePane.getText().setFill(Color.web("0x0000ff"));
        }
        if (b == 2) {
            squarePane.getText().setFill(Color.web("0x007b00"));
        }
        if (b == 3) {
            squarePane.getText().setFill(Color.web("0xff0000"));
        }
        if (b == 4) {
            squarePane.getText().setFill(Color.web("0x010080"));
        }
        if (b == 5) {
            squarePane.getText().setFill(Color.web("0x810102"));
        }
        if (b == 6) {
            squarePane.getText().setFill(Color.web("0x008081"));
        }
        if (b == 7) {
            squarePane.getText().setFill(Color.web("0x000000"));
        }
        if (b == 8) {
            squarePane.getText().setFill(Color.web("0x808080"));
        }
    }

    public void leftClick(SquarePane sqPane) {

        if (sqPane.getFlag().isVisible()) {
            sqPane.showFlag(false);
        }

        // Jos ruutua on jo klikattu, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }

        // Jos ruutu on pommi, hypätään takaisin päävalikkoon.
        if (sqPane.getSquare().isBomb()) {

            revealAllBombs();
            System.out.println("Hähää, hävisit.");
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(2000),
                    ae -> scene.setRoot(mainMenu)));
            timeline.play();

            return;
        }

        // Muissa tapauksissa paljastetaan alla oleva teksti (numero) ja
        // värjätään tausta valkoiseksi, niin että numero erottuu selvästi.
        sqPane.setOpen(true);
        sqPane.showEdge(false);

    }

    private void rightClick(SquarePane sqPane) {

        // Jos ruutua on jo auki, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }

        // Jos 
        if (sqPane.getFlag().isVisible()) {
            sqPane.showFlag(false);
            return;
        }

        if (!sqPane.getFlag().isVisible()) {
            sqPane.showFlag(true);
        }
    }

    public void revealAllBombs() {
        for (int i = 0; i < (squaresX * squaresY); i++) {
            Node n = gridPane.getChildren().get(i);
            SquarePane squarePane = (SquarePane) n;

            // Avataan kaikki ruudut siksi, että silloin niitä ei voi enää avata
            // klikkaamalla. Muuten voisi jatkaa pelaamista pelin päätyttyään.
            squarePane.getSquare().setIsOpen(true);

            if (squarePane.getSquare().isBomb()) {
                squarePane.getTile().setFill(Color.RED);
                squarePane.showEdge(false);
            }
        }
    }

    private static HBox createSizeHBox(TextField sizeX, TextField sizeY) {
        sizeX.setMaxWidth(40);
        sizeY.setMaxWidth(40);
        Label labelS = new Label("Kentän koko:");
        Label labelX = new Label(" x ");
        HBox hbS = new HBox();
        hbS.getChildren().addAll(labelS, sizeX, labelX, sizeY);
        hbS.setSpacing(10);
        return hbS;
    }
    
    private static HBox createMinesHBox(TextField mines) {
        mines.setMaxWidth(40);
        Label labelM = new Label("Miinoja (%):");
        HBox hbM = new HBox();
        hbM.getChildren().addAll(labelM, mines);
        hbM.setSpacing(10);
        return hbM;
    }
    
    private static HBox createTimeHBox(TextField time) {
        time.setMaxWidth(40);
        Label labelT = new Label("Aikaraja:");
        HBox hbT = new HBox();
        hbT.getChildren().addAll(labelT, time);
        hbT.setSpacing(10);
        return hbT;
    }

    @Override
    public void start(Stage stage) throws Exception {

        //aloitusnäyttö
        Text title = new Text("Minesweeper");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        
        TextField sizeX = new TextField("20");
        TextField sizeY = new TextField("20");
        TextField mines = new TextField("0.2");
        TextField time = new TextField();

        HBox hbS = createSizeHBox(sizeX, sizeY);
        HBox hbM = createMinesHBox(mines);
        HBox hbT = createTimeHBox(time);

        Button playButton = new Button();
        playButton.setText("Pelaa!");
        playButton.setFont(Font.font(25));
        playButton.setOnAction(e -> {

            //kun klikkaa play-nappulaa, luo pelinäytön
            squaresX = Integer.parseInt(sizeX.getText());
            squaresY = Integer.parseInt(sizeY.getText());
            game = new MinesweeperGame(squaresX, squaresY, Double.parseDouble(mines.getText()));
            stage.setWidth(SQUARE_SIZE * squaresX);
            stage.setHeight(SQUARE_SIZE * squaresY);
            scene.setRoot(createGrid());
        });

        mainMenu = new BorderPane();

        VBox centerVbox = new VBox();
        centerVbox.setPadding(new Insets(100));

        centerVbox.setMargin(title, new Insets(0, 0, 70, 0));
        centerVbox.setMargin(hbS, new Insets(0, 0, 20, 0));
        centerVbox.setMargin(hbM, new Insets(0, 0, 20, 0));
        centerVbox.setMargin(hbT, new Insets(0, 0, 70, 0));

        centerVbox.getChildren().addAll(Arrays.asList(title, hbS, hbM, hbT, playButton));

        mainMenu.setCenter(centerVbox);

        scene = new Scene(mainMenu, 800, 600);

        //aloitusnäytön setup
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
