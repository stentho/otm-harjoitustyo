package minesweeper.game;


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
        sq = new Square(1, 1, false);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void xOfSquareIsNotNegativeInConstructor() {
        sq = new Square(-1, 1, false);
        assertEquals(0, sq.getX());
    }

    @Test
    public void yOfSquareIsNotNegativeInConstructor() {
        sq = new Square(1, -1, false);
        assertEquals(0, sq.getY());
    }

    @Test
    public void xCannotBeSetToNegative() {
        int x = sq.getX();
        sq.setX(-1);
        assertEquals(x, sq.getX());
    }

    @Test
    public void yCannotBeSetToNegative() {
        int y = sq.getY();
        sq.setY(-1);
        assertEquals(y, sq.getY());
    }

    @Test
    public void xCanBeSetToPositive() {
        sq.setX(3);
        assertEquals(3, sq.getX());
    }

    @Test
    public void yCanBeSetToPositive() {
        sq.setY(3);
        assertEquals(3, sq.getY());
    }

    @Test
    public void toStringWorksCorrectly() {
        sq.setX(3);
        sq.setY(4);
        sq.setBomb(true);
        sq.setOpen(true);
        String stringB = "3, 4, bomb: true, open: true";
        assertEquals(stringB, sq.toString());
    }
}
