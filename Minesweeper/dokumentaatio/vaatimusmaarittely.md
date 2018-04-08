# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksella pelataan suosittua miinaharavointipeliä. Sovelluksessa on tarkoitus voida määrittää kentän koon, miinojen määrän, sekä aikarajan ennen pelin alkua. Jos pelaaja voittaa pelin, hänellä on mahdollisuus tallettaa suorituksensa (johon kuuluu kentän koko, miinojen määrä ja aika) kirjoittamalla nimensä tulostaulukkoon. Tulostaulukkoa on tarkoitus voida vilkaista myös erikseen (ilman että tarvitsee voittaa pelin).


## Käyttöliittymäluonnos

Sovelluksen käyttöliittymässä on neljä näkymää:

<img src="https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/MinesweeperOTM.png">

Sovelluksen aloitusnäytössä määritellään kentän koko, miinojen määrä (ei voi olla suurempi kuin kentän koko) ja aikaraja. Tältä näytöltä voi siirtyä suoraan peliin tai tulostaulukkoon. Pelin päätyttyä (mikäli voittaa) on mahdollisuus tallettaa suoritus tulostaulukkoon syöttämällä nimellä.

## Perusversion tarjoama toiminnallisuus

### Aloitusnäyttö

- Perusversiossa ei voi valita kentän kokoa, miinojen määrää, eikä aikarajaa
- Voi pelkästään painaa Pelaa!-painiketta

### Pelaaminen

- Pelissä voi normaalisti hiiren vasemmalla näppäimellä tarkistaa ruutukentällä olevia ruutuja miinojen varalta
- Hiiren oikealla näppäimellä voi asettaa lipun mahdollisen miinaruudun kohdalle
- Kaikki toiminnot eivät vielä tule olemaan sovelluksessa, mutta oleelliset kyllä
  - Perusversiossa tulee olemaan vain yhden kokoinen (esim. 10x10) miinakenttä.
  - Miinoja tulee olemaan vain tietty (esim 10) määrä
  - Miinat kuitenkin sijoitetaan satunnaisesti joka pelin alkaessa
  - Miinoja jäljellä -palkki tulee toimimaan
  - Ei ole aikalaskentaa

### Pelin jälkeen

- Perusversiossa pelin jälkeen palataan aloitusnäyttöön suoraan

## Jatkokehitysideoita

Seuraavat toiminnallisuudet lisätään sovellukseen ajan myötä:

- Kentän koon, miinojen määrän (ei saa ylittää kentän kokonaisruutumäärää) ja aikarajan säätäminen aloitusnäytössä
  - Kentän koon implementaatio itse peliin niin että ruutu skaalautuu oikein sen mukaan
  - Miinojen määrän implementaatio itse peliin
  - Aikarajan implementaatio itse peliin (jos aika loppuu niin peli päättyy ja palataan aloitusruutuun)
- Tulostaulukon luominen niin että sinne pääsee suoraan aloitusnäytöstä
  - Tietokannan luominen ja integraatio (Daoilla)
  - Tulostaulukon sisällön suodatus esim (nimen, kenttäkoon, miinamäärän ja ajan mukaan)
  - Pelin voittamisen jälkeen pitää pystyä kirjaamaan oman suorituksensa tulostaulukkoon antamalla nimellä
    - Ei pakollista, voi painaa myös peruuta, jonka seurauksena mennään takaisin aloitysnäyttöön
  - Tämän jälkeen siirryytään suoraan tulostaulukkonäyttöön
