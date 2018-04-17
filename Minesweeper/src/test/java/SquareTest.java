
import minesweeper.game.Square;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SquareTest {
    private Square sq;

    public SquareTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        sq = new Square(1,1,false);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ruudunXSijaintiEiOleNegatiivinen() {
        sq = new Square(-1,1,false);
    }
    
    @Test
    public void ruudunYSijaintiEiOleNegatiivinen() {
        sq = new Square(1,-1,false);
    }
}
