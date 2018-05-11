# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksella pelataan suosittua miinaharavointipeliä. Sovelluksessa on voi määrittää kentän koon, miinojen määrän, sekä aikarajan ennen pelin alkua. Jos pelaaja voittaa pelin, hän voi tallettaa suorituksensa (johon kuuluu kentän koko, miinojen määrä ja aika) kirjoittamalla nimensä tulostaulukkoon. Tulokset tallettuvat tietokantaan, ja pysyvät siellä vaikka sulkisi pelin. Tulostaulukkoa voi vilkaista myös suoraan päävalikosta (ilman että tarvitsee pelata).


## Käyttöliittymäluonnos

Sovelluksen käyttöliittymässä on neljä näkymää:

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kuvat/MinesweeperOTM.png">

Sovelluksen päävalikossa määritellään kentän koko, miinojen määrä ja aikaraja. Tältä näytöltä voi siirtyä suoraan peliin tai tulostaulukkoon. Pelin päätyttyä (mikäli voittaa) on mahdollisuus tallettaa suoritus tulostaulukkoon syöttämällä nimellä. Tulostaulukkoon pääsee myös suoraan päävalikosta.

## Perusversion tarjoama toiminnallisuus

### Aloitusnäyttö

- Päävalikossa voit valita kentän koon, miinojen määrän, ja aikarajan
- Pääset suoraan peliin painamalla _Pelaa!_
- Valikosta pääset myös suoraan tulostaulukkoon
- Voit myös poistua pelistä

### Pelaaminen

- Pelissä voi hiiren vasemmalla näppäimellä avata ruutuja
  - Jos avaat miinaruudun, häviät pelin
  - Muissa tapauksissa ruutujen takaa paljastuu numeroita, joiden avulla voit välttää miinaruutuja
  - Jos avaat ns. tyhjän ruudun (ei yhtäkään miinaa ympärillä), peli avaa myös kaikki ympärillä olevat tyhjät ruudut automaattisesti
- Hiiren oikealla näppäimellä voi asettaa lipun mahdollisen miinaruudun kohdalle
- Ikkuna skaalautuu kentän koon mukaan
- Yläkulmassa tikittää kello pelaajan asettamalla aikarajalla
  - Jos aika loppuu (ja ei-miinaruutuja on vielä avaamatta), häviät pelin.
  - Jos siinä kohdassa lukee nolla, ei ole aikarajaa.

### Pelin jälkeen

- Jos häviät, siirryt suoraan päävalikkoon
- Jos voitat, siirryt voittoruutuun
  - Täällä näet suorituksesi, ja voit kirjata sen Tulostaulukkoon

### Tulostaulukko

- Täällä näet aiemmat tuloksesi
- Taulukon rivejä voi järjestää klikkaamalla sarakkeiden otsikoita

## Jatkokehitysideoita

- Grafiikan kehittäminen
- Tulostaulukko
  - Suoritusten poistaminen taulukosta
  - Suoritusten hakeminen m.m. nimellä
- Vihjeruutujen lisääminen käyttöliittymään jos esim. syöttää vääränlaista tietoa kenttiin
