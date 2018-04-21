# Arkkitehtuurikuvaus

## Sovelluslogiikka

Minesweeper-sovelluksessa on sovelluslogiikka ja käyttöliittymä erikseen. Tulevissa versioissa tulee myös olemaan DAO-pakkaus, joka toimii logiikan ja tietokannan välimaastona.

Alla olevasta alustavasta pakkauskaaviosta voi vilkaista eri luokkien välisiä suhteita:

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/pakkauskaavio.png">

### Päätoiminnallisuudet

#### Miinaruudun klikkaaminen

Kun pelataan Minesweeperia, ja klikataan ruutua, joka on miina, sovellus etenee näin:

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/sekvenssikaavio_leftclickbomb.png">

Kun ruutua klikataan pelissä, kutsutaan ruutujen klikkaamista (vasemmalla hiiripainikkeella) varten luotua tapahtumankäsittelijää. Tämä tapahtumankäsittelijä tarkistaa ensin, onko ruudussa lippu kutsumalla _SquarePane_-luokan _getFlag().isVisible()_-metodia. _SquarePane_-luokka palauttaa true, mikä johtaa siihen, että piiloitetaan lippu metodilla _showFlag(false)_.
Tämän jälkeen tapahtumankäsittelijä tarkistaa, onko ruutu jo avattu _isOpen()_-kutsulla. _SquarePane_ kutsuu _Square_-luokalta saman myös. Palauttaa false, eli ruutua ei ole vielä avattu.
Viimeiseksi tarkistetaan, onko ruudussa pommi vai ei. Kutsutaan _isBomb()_, joka palauttaa true. Peli on hävitty, eli siirrytään takaisin päävalikkoon (_scene.setRoot(mainMenu)_).
