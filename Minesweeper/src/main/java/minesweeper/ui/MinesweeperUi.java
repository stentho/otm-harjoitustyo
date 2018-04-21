package minesweeper.ui;

import java.util.Arrays;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private MinesweeperGame game;
    private final static int SQUARE_SIZE = 20;
    private Stage stage;
    private Scene scene;
    private BorderPane mainMenu;
    private BorderPane winScreen;
    private Pane gridPane;
    private int squaresX;
    private int squaresY;

    // tässä luodaan ruudukko
    public Pane createGrid() {
        System.out.println(game);
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
                addClickability(squarePane);
                int b = squarePane.getAdjacentBombs();

                //mikäli vierekkäisten pommien määrä on positiivinen, ja
                //ei ole itse pommi, niin piirretään numero ruutuun.
                if (b > 0 && !squarePane.getSquare().isBomb()) {
                    squarePane.setText(Integer.toString(b));
                    setSquareTextColor(squarePane, b);
                }
                gridPane.getChildren().add(squarePane);
            }
        }
        return gridPane;
    }

    // Lisätään parametriruudulle klikattavuustoiminnot.
    private void addClickability(SquarePane squarePane) {
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
    }

    // Tässä asetetaan numeroille omat värit, niin että ne erottuvat toisistaan.
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

        // Jos ruudussa on lippu, se poistetaan kun ruutu aukaistaan.
        if (sqPane.getFlag().isVisible()) {
            sqPane.showFlag(false);
        }

        // Jos ruutua on jo klikattu, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }

        // Jos ruutu on pommi, pelaaja häviää pelin.
        if (sqPane.isBomb()) {
            loseGame();
            return;
        }

        // Muissa tapauksissa paljastetaan alla oleva teksti (numero) piiloittamalla
        // "kannen" (edge). Asetetaan ruutu "auki"-tilaan myös logiikassa.
        sqPane.setOpen(true);
        sqPane.showEdge(false);
        
//        System.out.println("Unopened: " + game.numberOfUnopenedSquares());
//        System.out.println("Bombs: " + game.numberOfBombs());
        
        // Peli laskee joka klikkauksen jälkeen, montako avaamatonta ruutua on
        // vielä jäljellä. Mikäli määrä on sama kuin pommien määrä, peli on 
        // voitettu.
        if (game.numberOfUnopenedSquares() == game.numberOfBombs()) {
            winGame();
        }

    }

    private void rightClick(SquarePane sqPane) {

        // Jos ruutua on jo auki, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }

        // Jos ruudussa on jo lippu, se poistetaan.
        if (sqPane.getFlag().isVisible()) {
            sqPane.showFlag(false);
            return;
        }

        // Jos ruudussa ei ole lippua, siihen lisätään sellainen.
        if (!sqPane.getFlag().isVisible()) {
            sqPane.showFlag(true);
        }
    }

    // Paljastetaan kaikki pommiruudut.
    public void revealAllBombs() {
        for (int i = 0; i < (squaresX * squaresY); i++) {
            Node n = gridPane.getChildren().get(i);
            SquarePane squarePane = (SquarePane) n;

            // Avataan kaikki ruudut siksi, että silloin niitä ei voi enää avata
            // klikkaamalla. Muuten voisi jatkaa pelaamista pelin päätyttyään.
            squarePane.setOpen(true);

            if (squarePane.getSquare().isBomb()) {
                squarePane.getTile().setFill(Color.RED);
                squarePane.showEdge(false);
            }
        }
    }

    // Luodaan "Kentän koko"-elementti päävalikkoon.
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

    // Luodaan "Miinoja"-elementti päävalikkoon.
    private static HBox createMinesHBox(TextField mines) {
        mines.setMaxWidth(50);
        Label labelM = new Label("Miinoja:");
        Label labelP = new Label("%");
        HBox hbM = new HBox();
        hbM.getChildren().addAll(labelM, mines, labelP);
        hbM.setSpacing(10);
        return hbM;
    }

    // Luodaan "Aikaraja"-elementti päävalikkoon.
    private static HBox createTimeHBox(TextField time) {
        time.setMaxWidth(40);
        Label labelT = new Label("Aikaraja:");
        HBox hbT = new HBox();
        hbT.getChildren().addAll(labelT, time);
        hbT.setSpacing(10);
        return hbT;
    }

    @Override
    public void start(Stage st) throws Exception {
        stage = st;

        // Luodaan päävalikon ruutu mainMenu.
        mainMenu = new BorderPane();
        mainMenu.setPrefSize(800, 600);

        // Luodaan Vbox missä on kaikki päävalikon elementit, ja lisätään ne
        // päävalikkoruutuun.
        mainMenu.setCenter(createMainMenuVBox());
        
        // Luodaan voittoruutu.
        winScreen = new BorderPane();
        winScreen.setPrefSize(800, 600);
        winScreen.setCenter(createWinScreenVBox());

        // Luodaan scene. Asetetaan nimeksi Minesweeper ja laitetaan se stageen.
        scene = new Scene(mainMenu);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    // Tässä muutetaan ikkunan kokoa kentän koon mukaan. Nuo +16 ja +39
    // viittaavat automaattisesti luoduihin reunoihin sekä yläpalkkiin.
    // Ne lasketaan stagen leveyteen ja korkeuteen.
    public void resetScreenSize(int width, int height) {
        stage.setWidth(width + 16);
        stage.setHeight(height + 39);
    }

////     Testaukseen tarkoitettu metodi. Tämä tulostaa ikkunan leveyden ja korkeuden, 
////     aina kun jompikumpi muuttuu.
//    public void addStageSizeListeners() {
//        scene.widthProperty().addListener(new ChangeListener<Number>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//                System.out.println("Width: " + newSceneWidth);
//            }
//        });
//        scene.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//                System.out.println("Height: " + newSceneHeight);
//            }
//        });
//    }

    // Luodaan pelinäkymän reunat
    public BorderPane createGameBorder() {
        BorderPane border = new BorderPane();
        border.setCenter(createGrid());

        border.setBottom(createHBox(new Insets(20)));
        border.setTop(createHBox(new Insets(20)));
        border.setLeft(createVBox(new Insets(20)));
        border.setRight(createVBox(new Insets(20)));

        return border;
    }

    public HBox createHBox(Insets inset) {
        HBox hb = new HBox();
        hb.setPadding(inset);
        return hb;
    }

    public VBox createVBox(Insets inset) {
        VBox vb = new VBox();
        vb.setPadding(inset);
        return vb;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loseGame() {
        revealAllBombs();
        System.out.println("Hävisit.");
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> scene.setRoot(mainMenu)),
                new KeyFrame(
                        Duration.millis(2500),
                        ae -> resetScreenSize(800, 600)));
        timeline.play();
    }

    private void winGame() {
        System.out.println("Voitit!");
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> scene.setRoot(winScreen)),
                new KeyFrame(
                        Duration.millis(1000),
                        ae -> resetScreenSize(800, 600)));
        timeline.play();
    }

    private VBox createMainMenuVBox() {
        // Luodaan päävalikon elementit.
        Text title = new Text("Minesweeper");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 26));

        TextField sizeX = new TextField("20");
        TextField sizeY = new TextField("20");
        TextField mines = new TextField("20");
        TextField time = new TextField();

        HBox hbS = createSizeHBox(sizeX, sizeY);
        HBox hbM = createMinesHBox(mines);
        HBox hbT = createTimeHBox(time);

        Button playButton = new Button();
        playButton.setText("Pelaa!");
        playButton.setFont(Font.font(25));
        playButton.setOnAction(e -> {

            // Kun klikkaa pelaa-nappulaa, luo pelinäytön antamilla arvoilla.
            squaresX = Integer.parseInt(sizeX.getText());
            squaresY = Integer.parseInt(sizeY.getText());
            game = new MinesweeperGame(squaresX, squaresY, 0.01 * Double.parseDouble(mines.getText()));

            resetScreenSize(SQUARE_SIZE * squaresX + 80, SQUARE_SIZE * squaresY + 80);
            BorderPane gamePane = createGameBorder();
            scene.setRoot(gamePane);
        });
        VBox centerVbox = new VBox();
        centerVbox.setPadding(new Insets(100));

        centerVbox.setMargin(title, new Insets(0, 0, 70, 0));
        centerVbox.setMargin(hbS, new Insets(0, 0, 20, 0));
        centerVbox.setMargin(hbM, new Insets(0, 0, 20, 0));
        centerVbox.setMargin(hbT, new Insets(0, 0, 70, 0));

        centerVbox.getChildren().addAll(Arrays.asList(title, hbS, hbM, hbT, playButton));
        return centerVbox;
    }
    
    private VBox createWinScreenVBox() {
        Text title = new Text("Voitit, onneksi olkoon!");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        
        Text score = new Text("Pisteet:");
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        
        Text size = new Text("Kentän koko:");
        size.setFont(Font.font("Verdana", 18));
        
        Text mines = new Text("Miinoja: " + "%");
        mines.setFont(Font.font("Verdana", 18));
        
        Text time = new Text("Aika: ");
        time.setFont(Font.font("Verdana", 18));
        
        TextField name = new TextField();
        name.setMaxWidth(200);
        createNameHBox(name);
        HBox hbN = createNameHBox(name);
        
        Button submitName = new Button("Submit");
        
        VBox centerVbox = new VBox();
        centerVbox.setPadding(new Insets(100));

        centerVbox.setMargin(title, new Insets(0, 0, 70, 0));
        centerVbox.setMargin(score, new Insets(0, 0, 20, 0));
        centerVbox.setMargin(size, new Insets(0, 0, 20, 0));
        centerVbox.setMargin(mines, new Insets(0, 0, 20, 0));
        centerVbox.setMargin(time, new Insets(0, 0, 20, 0));
        centerVbox.setMargin(hbN, new Insets(50, 0, 0, 0));
        centerVbox.setMargin(submitName, new Insets(20, 0, 0, 0));

        centerVbox.getChildren().addAll(Arrays.asList(title, score, size, mines, time, hbN, submitName));
        return centerVbox;
    }

    private HBox createNameHBox(TextField name) {
        Label labelN = new Label("Nimi:");
        HBox hbN = new HBox();
        hbN.getChildren().addAll(labelN, name);
        hbN.setSpacing(10);
        return hbN;
    }
}
