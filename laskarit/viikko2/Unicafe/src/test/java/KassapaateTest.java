
import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    Kassapaate kassapaate;
    Maksukortti maksukortti;

    public KassapaateTest() {
    }

    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        maksukortti = new Maksukortti(1000);
    }

    @Test
    public void uudenKassapaatteenRahamaaraOikein() {
        assertEquals(100000,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void uudenKassapaatteenEdullisiaMyytyNolla() {
        assertEquals(0,kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void uudenKassapaatteenMaukkaitaMyytyNolla() {
        assertEquals(0,kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maksuOnRiittavaJaRahamaaraKasvaaOikeinEdullisesti() {
        kassapaate.syoEdullisesti(300);
        assertEquals(100240,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void maksuOnRiittavaJaRahamaaraKasvaaOikeinMaukkaasti() {
        kassapaate.syoMaukkaasti(500);
        assertEquals(100400,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void maksuOnRiittavaJaVaihtorahaOikeaEdullisesti() {
        int vaihtoraha = kassapaate.syoEdullisesti(300);
        assertEquals(300-240,vaihtoraha);
    }
    
    @Test
    public void maksuOnRiittavaJaVaihtorahaOikeaMaukkaasti() {
        int vaihtoraha = kassapaate.syoMaukkaasti(500);
        assertEquals(500-400,vaihtoraha);
    }
    
    @Test
    public void maksuOnRiittavaJaMyytyjenMaaraKasvaaEdullisesti() {
        kassapaate.syoEdullisesti(300);
        assertEquals(1,kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maksuOnRiittavaJaMyytyjenMaaraKasvaaMaukkaasti() {
        kassapaate.syoMaukkaasti(500);
        assertEquals(1,kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maksuEiRiitaJaRahamaaraEiMuutuEdullisesti() {
        kassapaate.syoEdullisesti(230);
        assertEquals(100000,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void maksuEiRiitaJaRahamaaraEiMuutuMaukkaasti() {
        kassapaate.syoMaukkaasti(390);
        assertEquals(100000,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void maksuEiRiitaJaVaihtorahaPalautetaanOikeinEdullisesti() {
        int vaihtoraha = kassapaate.syoEdullisesti(230);
        assertEquals(230,vaihtoraha);
    }
    
    @Test
    public void maksuEiRiitaJaVaihtorahaPalautetaanOikeinMaukkaasti() {
        int vaihtoraha = kassapaate.syoMaukkaasti(390);
        assertEquals(390,vaihtoraha);
    }
    
    @Test
    public void maksuEiRiitaJaMyytyjenMaaraEiMuutuEdullisesti() {
        kassapaate.syoEdullisesti(230);
        assertEquals(0,kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maksuEiRiitaJaMyytyjenMaaraEiMuutuMaukkaasti() {
        kassapaate.syoMaukkaasti(390);
        assertEquals(0,kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortillaOnTarpeeksiRahaaJaSummaVeloitetaanKortiltaEdullisesti() {
        kassapaate.syoEdullisesti(maksukortti);
        int saldo = maksukortti.saldo();
        assertEquals(1000-240,saldo);
    }
    
    @Test
    public void kortillaOnTarpeeksiRahaaJaSummaVeloitetaanKortiltaMaukkaasti() {
        kassapaate.syoMaukkaasti(maksukortti);
        int saldo = maksukortti.saldo();
        assertEquals(1000-400,saldo);
    }
    
    @Test
    public void kortillaOnTarpeeksiRahaaJaPalautetaanTrueEdullisesti() {
        boolean onnistui = kassapaate.syoEdullisesti(maksukortti);
        assertEquals(true,onnistui);
    }
    
    @Test
    public void kortillaOnTarpeeksiRahaaJaPalautetaanTrueMaukkaasti() {
        boolean onnistui = kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(true,onnistui);
    }
    
    @Test
    public void kortillaOnTarpeeksiRahaaJaMyytyjenLounaidenMaaraKasvaaEdullisesti() {
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(1,kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kortillaOnTarpeeksiRahaaJaMyytyjenLounaidenMaaraKasvaaMaukkaasti() {
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(1,kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortillaEiTarpeeksiRahaaJaKortinRahamaaraEiMuutuEdullisesti() {
        maksukortti.otaRahaa(800);
        int saldo = maksukortti.saldo();
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(saldo,maksukortti.saldo());
    }
    
    @Test
    public void kortillaEiTarpeeksiRahaaJaKortinRahamaaraEiMuutuMaukkaasti() {
        maksukortti.otaRahaa(700);
        int saldo = maksukortti.saldo();
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(saldo,maksukortti.saldo());
    }
    
    @Test
    public void kortillaEiTarpeeksiRahaaJaMyytyjenMaaraEiMuutuEdullisesti() {
        maksukortti.otaRahaa(800);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(0,kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kortillaEiTarpeeksiRahaaJaMyytyjenMaaraEiMuutuMaukkaasti() {
        maksukortti.otaRahaa(700);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(0,kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortillaEiTarpeeksiRahaaJaPalautetaanFalseEdullisesti() {
        maksukortti.otaRahaa(800);
        boolean onnistui = kassapaate.syoEdullisesti(maksukortti);
        assertEquals(false,onnistui);
    }
    
    @Test
    public void kortillaEiTarpeeksiRahaaJaPalautetaanFalseMaukkaasti() {
        maksukortti.otaRahaa(700);
        boolean onnistui = kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(false,onnistui);
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKortillaOstettaessaEdullisesti() {
        int kassa = kassapaate.kassassaRahaa();
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(kassa,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKortillaOstettaessaMaukkaasti() {
        int kassa = kassapaate.kassassaRahaa();
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(kassa,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kortilleRahaaLadattaessaKortinSaldoMuuttuuOikein() {
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(2000,maksukortti.saldo());
    }
    
    @Test
    public void kortilleEiVoiLadataNegatiivistaSaldoa() {
        kassapaate.lataaRahaaKortille(maksukortti, -1);
        assertEquals(1000,maksukortti.saldo());
    }
    
    
    @Test
    public void kortilleRahaaLadattaessaKassanRahamaaraKasvaaOikein() {
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(101000,kassapaate.kassassaRahaa());
    }
}
