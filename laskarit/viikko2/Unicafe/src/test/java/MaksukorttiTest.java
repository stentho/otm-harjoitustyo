
import com.mycompany.unicafe.Maksukortti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaksukorttiTest {
    
    Maksukortti maksukortti;

    public MaksukorttiTest() {
    }

    @Before
    public void setUp() {
        maksukortti = new Maksukortti(100);
    }

    @Test
    public void kortinSaldoOnAlussaOikein() {
        assertEquals("saldo: 1.0", maksukortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        maksukortti.lataaRahaa(100);
        assertEquals("saldo: 2.0", maksukortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikein() {
        maksukortti.otaRahaa(50);
        assertEquals("saldo: 0.50", maksukortti.toString());
    }
    
    @Test
    public void rahatEiRiitaJaSaldoEiMuutuNegatiiviseksi() {
        int saldo = maksukortti.saldo();
        maksukortti.otaRahaa(saldo);
        maksukortti.otaRahaa(1);
        assertEquals("saldo: 0.0", maksukortti.toString());
    }
    
    @Test
    public void rahatRiittaaJaPalauttaaTrue() {
        boolean onnistui = maksukortti.otaRahaa(1);
        assertEquals(true,onnistui);
    }
    
    @Test
    public void rahatEiRiitaJaPalauttaaFalse() {
        int saldo = maksukortti.saldo();
        boolean onnistui = maksukortti.otaRahaa(saldo + 1);
        assertEquals(false,onnistui);
    }
}
