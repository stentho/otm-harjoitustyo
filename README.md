# Minesweeper

Tällä sovelluksella pelataan Minesweeperia! Pelin tarkoitus on klikata auki ruutukentän kaikki ruudut miinaruutuja lukuunottamatta. Ruutuja klikkaamalla voi nähdä numeroja, ja niiden avulla voi päätellä, missä miinat ovat. Jos ruudussa lukee esim 5, se tarkoittaa, että sen ruudun ympärillä olevista ruuduista viidessä on miinoja. Jos klikkaa yhtäkin miinaruutua, peli on hävitty.    

Pelin päävalikossa on mahdollista säätää kentän kokoa, sekä miinojen määrää. Pelissä tulee olemaan ominaisuus, missä voi kirjata oman suorituksensa tulostaulukkoon (Tätä ei ole vielä implementoitu).

## Dokumentaatio

[Käyttöohje](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/stentho/otm-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Testausdokumentti

Sovellusta voi testata seuraavalla komennolla:

```
mvn test
```

Testikattavuusraportin voi luoda komennolla

```
mvn jacoco:report
```
Kattavuusraportti löytyy kansiosta _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _Minesweeper-1.0-SNAPSHOT.jar_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

[Checkstyle.xml](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/checkstyle.xml)-tiedoston määrittelemät tarkistukset suoritetaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

Virheilmoitukset yms. löytyvät tiedostosta _target/site/checkstyle.html_
