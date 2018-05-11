# Pelin asentaminen

- Lataa pelin uusin julkaisu aloitussivulta kohdasta Releaset. Voit tallettaa zip-tiedoston mihin vain. Pura tiedosto ja käynnistä peli tuplaklikkaamalla minesweeperapp.jar. Komentoriviltä voit myös käynnistää pelin komennolla

```
java -jar minesweeperapp.jar
```

- Saadaksesi pelin tulostaulukon toimimaan, sinun pitää ensin ladata Xerialin SQLite JDBC -ajuri (versio 3.18.0) [tästä](https://oss.sonatype.org/content/repositories/releases/org/xerial/sqlite-jdbc/3.18.0/sqlite-jdbc-3.18.0.jar). Laita ajuritiedosto samaan kansioon minne purit pelitiedostot.

# Peliohje

## Pelivalikko

Tässä valitset kentän koon, miinojen määrän sekä aikarajan. Voit myös halutessasi mennä suoraan tulostaulukkoon (_Tulostaulukko_) tai poistua pelistä (_Lopeta_).

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/mainscreen.jpg">

## Pelaaminen

Pelin tarkoitus on löytää kaikki miinat ruutukentästä. Peli on voitettu, kun on klikannut auki kaikki ruudut, kuitenkin välttäen miinaruutuja.

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/game.jpg">

### Vasen hiirinäppäin

Vasemmalla hiirinäppäimellä avaat ruutuja. Ruutuja avaamalla voi paljastua numeroita, jotka kertovat täsmälleen montako miinaa on sen ruudun ympärillä. Numeroiden perusteella voi hahmottaa, missä miinat voisivat olla. Ole kuitenkin varovainen, sillä miinaruudun avaaminen johtaa pelin häviämiseen.  

Pelin häviäminen:

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/game_lose.jpg">

### Oikea hiirinäppäin

Oikealla hiiripainikkeella voit merkata ruutuja punaisilla lipuilla. Tarkoitus on merkata ne ruudut, jotka ovat mielestäsi miinaruutuja. Tämä helpottaa  Tämän tekeminen ei avaa ruutua.

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/game_flag.jpg">

## Pelin voittaminen

Jos voitat pelin, siirryt automaattisesti voittoruutuun. Tässä näet suorituksesi, ja voit halutessasi kirjata suorituksesi tulostaulukkoon kirjoittamalla nimesi tekstikenttään ja painamalla Lähetä.

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/game_win.jpg">

## Tulostaulukko

Täällä näet aikaisemmat suorituksesi. Rivien järjestystä voi muuttaa klikkaamalla sarakkeiden otsikkoja.

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/game_scores.jpg">
