Minesweeper

Tällä sovelluksella pelataan Minesweeperia!

## Dokumentaatio

[Käyttöohje](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/arkkitehtuurikuvaus.md)

[Testausdokumentti](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/stentho/otm-harjoitustyo/blob/master/Minesweeper/dokumentaatio/tuntikirjanpito.md)

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
