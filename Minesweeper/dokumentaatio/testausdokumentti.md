# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Sovelluslogiikan pakkauksen [minesweeper.game](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/src/main/java/minesweeper/game) luokkia testaavat integraatiotestit [GameTest](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/src/test/java/minesweeper/game/GameTest.java) ja  [GameServiceTest](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/src/test/java/minesweeper/game/GameServiceTest.java) joiden määrittelevät testitapaukset testaavat sovelluksen toiminnallisuuksia [GameService](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/src/main/java/minesweeper/game/GameService.java)-olion avulla.

Tietokantaintegraatiota ja datan pysyvyyttä on testattu [DatabaseTest](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/src/test/java/minesweeper/database/DatabaseTest.java) ja [ScoreDaoTest](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/src/test/java/minesweeper/database/DatabaseTest.java) -testiluokkien avulla.

Sovelluslogiikan luokalle [Square](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/src/main/java/minesweeper/game/Square.java) on myös tehty yksikkötestejä.

### DAO-luokat

Molempien DAO-luokkien toiminnallisuus on testattu luomalla testeissä tilapäinen tiedosto hyödyntäen JUnitin [TemporaryFolder](https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html)-ruleja.

### Testauskattavuus

Rivikattavuus on 82% ja haarautumakattavuus 79% lukuunottamatta käyttöliittymäpakkausta.

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/rivikattavuus.jpg" width="800">

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja kanfigurointi

Sovellusta on testattu [käyttöohjeen](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kayttoohje.md) kuvaamalla tavalla Windowsilla.

Sovellusta on testattu sekä ilman olemassaolevaa tietokantatiedostoa (tulostaulukko) ja sellaisen kanssa.

### Toiminnallisuudet

[Määrittelydokumentissa](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/vaatimusmaarittely.md) ja käyttöohjeessa listatut toiminnallisuudet on käyty läpi.

## Sovellukseen jääneet laatuongelmat

Peli ei anna informatiivisia virheilmoituksia, mitkä auttaisivat käyttäjää. Tulee myös erroreita, jos syöttää kenttiin väärän muotoista informaatiota tai jättää tyhjäksi.
