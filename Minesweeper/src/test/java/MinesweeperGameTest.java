
import minesweeper.game.MinesweeperGame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class MinesweeperGameTest {
    
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
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void eiLuoNegatiivisenKokoistaPeliä() {
        MinesweeperGame game = new MinesweeperGame(-8, 10, 0.2);
        assertEquals(game.getSquaresX(), 0);
    }
}
