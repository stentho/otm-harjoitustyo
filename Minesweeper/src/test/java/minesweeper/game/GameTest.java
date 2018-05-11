package minesweeper.game;


import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    public GameTest() {
    }

    @Before
    public void setUp() {
        game = new Game(10, 10, 0.2);
        game.createField();
    }

    @Test
    public void noNegativeWidthInConstructor() {
        game = new Game(-8, 10, 0.2);
        assertEquals(game.getSquaresX(), 0);
    }

    @Test
    public void noNegativeHeightInConstructor() {
        game = new Game(10, -1, 0.2);
        assertEquals(game.getSquaresY(), 0);
    }

    @Test
    public void minesNoLessThanZeroPercent() {
        game = new Game(10, 10, -1);
        assertEquals(0.0, game.getMineFreq(), 0.001);
    }

    @Test
    public void minesNoMoreThanHundredPercent() {
        game = new Game(10, 10, 2);
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
    
    @Test
    public void doNotOpenSurroundingSquaresIfNotZeroAndNotBomb() {
        // Asetetaan yksi miina ruudun alapuolelle, niin että numero ei voi olla
        // nolla. Pidetään myös huoli, että tarkasteltava ruutu ei ole miina.
        game.getField()[3][3] = new Square(3, 3, false);
        game.getField()[3][4] = new Square(3, 4, true);
        Square sq = game.getField()[3][3];
        
        game.openAdjacentSquaresIfZero(sq);
        ArrayList<Square> adjSq = game.getAdjacentSquares(sq);
        for (Square s : adjSq) {
            assertEquals(false, s.isOpen());
        }
    }
    
    @Test
    public void numberOfUnopenedSquaresCorrect() {
        int squaresTotal = game.getSquaresX() * game.getSquaresY();
        assertEquals(squaresTotal, game.numberOfUnopenedSquares());
        
        game.getField()[3][3].setOpen(true);
        game.getField()[3][4].setOpen(true);
        
        assertEquals(squaresTotal - 2, game.numberOfUnopenedSquares());
        
        game.getField()[3][4].setOpen(true);
        
        assertEquals(squaresTotal - 2, game.numberOfUnopenedSquares());
    }
    
    @Test
    public void numberOfBombsCorrect() {
        // Luodaan kenttä missä 100% miinoja.
        game = new Game(10, 10, 1);
        game.createField();
        int bombsTotal = game.getSquaresX() * game.getSquaresY();
        assertEquals(bombsTotal, game.numberOfBombs());
        
        game.getField()[3][3].setBomb(false);
        game.getField()[3][4].setBomb(false);
        
        assertEquals(bombsTotal - 2, game.numberOfBombs());
    }
    
    @Test
    public void toStringCorrect() {
        String g = game.toString();
        
        assertEquals(g, "Minesweeper (leveys 10, korkeus 10, miinoja 20%)");
    }
}
