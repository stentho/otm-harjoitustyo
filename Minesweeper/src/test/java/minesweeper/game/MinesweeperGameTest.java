package minesweeper.game;


import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MinesweeperGameTest {

    private MinesweeperGame game;

    public MinesweeperGameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        game = new MinesweeperGame(10, 10, 0.2);
        game.createField();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void noNegativeWidthInConstructor() {
        game = new MinesweeperGame(-8, 10, 0.2);
        assertEquals(game.getSquaresX(), 0);
    }

    @Test
    public void noNegativeHeightInConstructor() {
        game = new MinesweeperGame(10, -1, 0.2);
        assertEquals(game.getSquaresY(), 0);
    }

    @Test
    public void minesNoLessThanZeroPercent() {
        game = new MinesweeperGame(10, 10, -1);
        assertEquals(0.0, game.getMineFreq(), 0.001);
    }

    @Test
    public void minesNoMoreThanHundredPercent() {
        game = new MinesweeperGame(10, 10, 2);
        assertEquals(1, game.getMineFreq(), 0.001);
    }

    @Test
    public void fieldWidthCorrect() {
        game.createField();
        Square[][] field = game.getField();
        assertEquals(field[0].length, 10);
    }

    @Test
    public void fieldHeightCorrect() {
        Square[][] field = game.getField();
        assertEquals(field.length, 10);
    }

    @Test
    public void adjacentSquaresCorrectPlacementNotAtEdge() {
        Square sq = game.getField()[3][3];
        ArrayList<Square> adjSq = game.getAdjacentSquares(sq);
        // valittiin ruutu (3,3), eli sen ympärillä olevat ruudut ovat (jos 
        // mennään riveittäin ylhäältä alas vasemmalta oikealle, sq on (3,3)):

        // (2,2), (3,2), (4,2)
        // (2,3),  sq  , (4,3)
        // (2,4), (3,4), (4,4)
        ArrayList<Square> comparison = new ArrayList<>();
        comparison.addAll(Arrays.asList(
                new Square(2, 2, false),
                new Square(3, 2, false),
                new Square(4, 2, false),
                new Square(2, 3, false),
                new Square(4, 3, false),
                new Square(2, 4, false),
                new Square(3, 4, false),
                new Square(4, 4, false)
        ));

        for (int i = 0; i < adjSq.size(); i++) {
            Square sqA = adjSq.get(i);
            Square sqB = comparison.get(i);
            assertEquals(sqA.getX(), sqB.getX());
            assertEquals(sqA.getY(), sqB.getY());
        }
    }

    @Test
    public void adjacentSquaresCorrectPlacementAtXEdge() {
        Square sq = game.getField()[0][3];
        ArrayList<Square> adjSq = game.getAdjacentSquares(sq);
        // valittiin ruutu (0,3), eli sen ympärillä olevat ruudut ovat (jos 
        // mennään riveittäin ylhäältä alas vasemmalta oikealle, sq on (0,3)):

        //  W, (0,2), (1,2)
        //  W,  sq  , (1,3)
        //  W, (0,4), (1,4)
        ArrayList<Square> comparison = new ArrayList<>();
        comparison.addAll(Arrays.asList(
                new Square(0, 2, false),
                new Square(1, 2, false),
                new Square(1, 3, false),
                new Square(0, 4, false),
                new Square(1, 4, false)
        ));

        for (int i = 0; i < adjSq.size(); i++) {
            Square sqA = adjSq.get(i);
            Square sqB = comparison.get(i);
            assertEquals(sqA.getX(), sqB.getX());
            assertEquals(sqA.getY(), sqB.getY());
        }
    }

    @Test
    public void adjacentSquaresCorrectPlacementAtYEdge() {
        Square sq = game.getField()[3][0];
        ArrayList<Square> adjSq = game.getAdjacentSquares(sq);
        // valittiin ruutu (0,3), eli sen ympärillä olevat ruudut ovat (jos 
        // mennään riveittäin ylhäältä alas vasemmalta oikealle, sq on (3,0)):

        //  WWWWWWWWWWWWWWWWWWW
        //  (2,0),  sq  , (4,0)
        //  (2,1), (3,1), (4,1)
        ArrayList<Square> comparison = new ArrayList<>();
        comparison.addAll(Arrays.asList(
                new Square(2, 0, false),
                new Square(4, 0, false),
                new Square(2, 1, false),
                new Square(3, 1, false),
                new Square(4, 1, false)
        ));

        for (int i = 0; i < adjSq.size(); i++) {
            Square sqA = adjSq.get(i);
            Square sqB = comparison.get(i);
            assertEquals(sqA.getX(), sqB.getX());
            assertEquals(sqA.getY(), sqB.getY());
        }
    }
}
