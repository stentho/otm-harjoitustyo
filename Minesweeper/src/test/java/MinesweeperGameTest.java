
import minesweeper.game.MinesweeperGame;
import minesweeper.game.Square;
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
    public void eiLuoPeliaMissaNegatiivinenLeveys() {
        game = new MinesweeperGame(-8, 10, 0.2);
        assertEquals(game.getSquaresX(), 0);
    }
    
    @Test
    public void eiLuoPeliaMissaNegatiivinenKorkeus() {
        game = new MinesweeperGame(10, -1, 0.2);
        assertEquals(game.getSquaresY(), 0);
    }
    
    @Test
    public void miinojenMaaraEiPienempiKuinKymmenenProsenttia() {
        game = new MinesweeperGame(10, 10, -1);
        assertEquals(0.1, game.getMineFreq(), 0.001);
    }
    
    @Test
    public void miinojenMaaraEiSuurempiKuinProsenttia() {
        game = new MinesweeperGame(10, 10, 2);
        assertEquals(1, game.getMineFreq(), 0.001);
    }
    
    @Test
    public void kentanLeveysOnOikea() {
        game.createField();
        Square[][] field = game.getField();
        assertEquals(field[0].length, 10);
    }
    
    @Test
    public void kentanKorkeusOnOikea() {
        Square[][] field = game.getField();
        assertEquals(field.length, 10);
    }
}
