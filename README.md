## Heise Academy

# Einführung in Spring GraphQL

In diesem Repository findest Du die unterschiedlichen Beispiele und Source-Code-Stände des
Videokurses "Einführung in Spring GraphQL" bei heise academy.

![GraphiQL](./screenshot-graphiql.png)

## Verzeichnisse

- `hello-graphql-java` und `hello-spring-graphql`

Diese beiden Verzeichnisse enthalten das Hello-World-Beispiel für graphql-java
bzw. spring-graphql aus dem ersten Kapitel.

- `publy`

Das `publy`-Verzeichnis enthält die fertige Beispiel-Anwendung, die eine etwas
vollständigere API enthält, als die Anwendung, die wir im Kurs Schritt-für-Schritt
bauen. Außerdem findest Du hier auch ein Frontend. Weitere Informationen dazu in der
README-Datei in dem Verzeichnis

- `publy-workspace`

Hierin findest Du die Source-Code-Stände nach den einzelnen Kapiteln bzw. Lektionen
des Videokurses.
Die Verzeichnisse enthalten jeweils einen vollständigen Stand, den Du in deiner IDE
öffnen kannst.

Für die Anwendung musst Du eine Datenbank starten mittels docker-compose.
_Das musst Du nur einmal tun_; die Datenbank (Konfiguration und Inhalt) ist über den kompletten Kurs identisch.
Ab Kapitel 4 musst Du außerdem den UserService starten (ebenfalls eine Spring Boot-Anwendung,
die Du einfach aus deiner IDE starten kannst).
Die Anwendung, die wir bauen, liegt jeweils im `publy-backend`-Unterverzeichnis. Darin enthalten
eine Spring Boot-Anwendung, die Du mit der Klasse `PublyApplication` starten kannst.

Wenn Du die einzelnen Schritte nachvollziehen möchtest, kannst Du einfach zwei Verzeichnisse
vergleichen.

Wenn Du die Schritte auch selbst **programmieren** möchtest, startest Du am Besten im Verzeichnis
`00-Startpunkt`.

Um eine **Übung** zu machen, öffnest Du am besten den Stand der Lektion vor der Übung
und ergänzt diesen. Wenn Du die einzelnen Schritte ohnehin auch bei dir mitprogrammierst,
kannst Du die Übungen natürlich auch auf "deinem" Stand machen.

## Starten eines Standes lokal

(Zum Starten der fertigen Anwendung im `publy`-Verzeichnis, siehe README dort)

Die einzelnen Stände kannst Du wie folgt lokal bei dir starten:

**Voraussetzungen:**

- Java 17
- Docker
- [yarn](https://yarnpkg.com/) optional, nur für das Frontend

**Repository klonen**

- Bitte klone zunächst dieses Repository mit deinem Git Client.

Alle folgenden Schritte dann jeweils in dem gewünschten Unterverzeichnis unterhalb von `publy-workspace` ausführen:

**Schritt 2: Datenbank starten (mit Docker)**

Im Root-Verzeichnis eines Standes:

- `docker-compose -f docker-compose -f docker-compose.yml up -d`

Hinweis: das brauchst Du nur einmal machen, und es ist egal, in welchem Verzeichnis du das machst,
da Datenbank, -Konfiguration und -Inhalt überall dieselbe ist. Die Datenbank kannst du also
die ganze Zeit laufen lassen.

**Schritt 3: Userservice starten (erst ab Kapitel 4 notwendig)**

Achtung! Port 8091 darf nicht belegt sein:

- `./gradlew publy-userservice:bootRun`

Hinweis: Du kannst den User aus jedem beliebigen Verzeichnis starten, und es reicht, ihn einmal zu starten. Er wird im Laufe
des Kurses nicht verändert.

**Schritt 4: Backend starten**

Achtung! Port 8090 darf nicht belegt sein:

- `./gradlew publy-backend:bootRun`

Du kannst nun über `http://localhost:8090` den GraphiQL-Explorer
aufmachen und Queries und Mutations ausführen.

## Öffnen in der IDE

Du kannst jedes Unterverzeichnis in IDEA öffnen, dann wird das Projekt als Gradle-Projekt erkannt und von IDEA automatisch richtig konfiguriert.

Dann kannst Du u.a. die beiden Server-Prozesse als Java-Anwendungen starten:

- `nh.graphql.publy.userservice.UserserviceApplication`: Der User-Service
- `nh.publy.backend.PublyApplication`: Das (GraphQL-)Backend

Wichtig: In jedem Fall bitte zuerst die Datenbank wie oben beschrieben mit `docker-compose` starten!

# 2. Ausführen von Queries in GraphiQL

Du kannst GraphiQL benutzen, um GraphQL Queries mit der Beispiel-Anwendung auszuführen.

Beispiel 1:

```graphql
query {
  members {
    id
    skills
  }
}
```

## Authorization-Tokens (für Mutations ab Kapitel 4)

Wenn Du **Mutations** ab Kapitel vier ausführen willst (mit Security), erwartet das Backend einen Benutzer-Token.
Zum Testen kannst Du einen der folgenden Tokens verwenden, die immer gültig sind:

- User: `nils`
- Rolle: `ROLE_EDITOR` und `ROLE_USER` (darf alles)

```json
{
  "Authorization": "Bearer eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiVTFcIixcInVzZXJuYW1lXCI6XCJuaWxzXCIsXCJuYW1lXCI6XCJOaWxzIEhhcnRtYW5uXCIsXCJlbWFpbFwiOlwia29udGFrdEBuaWxzaGFydG1hbm4ubmV0XCIsXCJyb2xlc1wiOltcIlJPTEVfVVNFUlwiLFwiUk9MRV9FRElUT1JcIl0sXCJndWVzdFwiOmZhbHNlfSIsImlhdCI6MTY0MTM2OTg0NywiZXhwIjozMjMyOTg3OTg3fQ.lsLAqfAdMID_2QFL7OimWEHcunaPy18zWVYZUDiEJYVwR9PQG5qf8_gRrNAwkd9w33dwunJC3bswR1W0zMtTID9DyeaXGLws2AtmwF_ZecD6x5TVDEBcFJV1-WrRt2yRWoo-hNywGqDUqR49dJoICoQ-aoP_7GOgYo5zYIYCRe2Xbn4DbX0xzGLyiRNzJzZhq8l6KE_Hb5Ern0hTZhTZXq4jmrCjf8wztuF37rsRJ-nZ9vozUaQU7vwhl93g1gvAMb3zJWBo_m9ujd3RKSZ5fjMuOVb-kVQI6NP9hLoEYO2mkcDSoHNIXBgJHr3TYNzeGtJ3Nt3bTXp-o_P-bSzXYQ"
}
```

- User: `murphy`
- Rolle: `ROLE_USER` (darf Kommentare und Reaktion geben, aber keine Stories erzeugen)

```json
{
  "Authorization": "Bearer eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiVTdcIixcInVzZXJuYW1lXCI6XCJtdXJwaHlcIixcIm5hbWVcIjpcIkV0aGVseW4gTXVycGh5XCIsXCJlbWFpbFwiOlwiZXRoZWx5bi5tdXJwaHlAZXhhbXBsZS5jb21cIixcInJvbGVzXCI6W1wiUk9MRV9VU0VSXCIsXCJST0xFX0dVRVNUXCJdLFwiZ3Vlc3RcIjp0cnVlfSIsImlhdCI6MTY0MTM2OTg0NywiZXhwIjozMjMyOTg3OTg3fQ.A1SHxkgGCfdo-v-kCGRSFuYngMW6438o1alkg4DAdwWBYuy1E7axYbpzGKghP5gR19b7qoc98Y9gY9-zekFxo35yrzDaEmWMYR0UmprYI27M_eh06OJzct2NJt9voldnUlPdCed8mn4vPs56IXHTxd6zGGwSA7JGYSIfswQh2w-3y1d3WCFR3ZPju0f9ZripR_4NQFOltm4NNbHC7CbcWgUtixJx-h5BiAeLfZcJDFoNqUq6obf8jkUzOX_2PEJaeRzxW6WTXd88EbSqjMns5PqmM5BosSJmyuZSjfGGbLaFBqPLrgMLNHHNFwGn6VBtarMvmHsU7zbGYmZXG31XAg"
}
```

Den kompletten Authorization-JSON-String kannst Du in GraphiQL unten in den "**Request Headers**"-Tab einfügen.
Der Header wird dann bei jedem Query/jeder Mutation mitgesendet.

![Request Header in GraphiQL setzen](./graphiql-request-header.png)

# Übungen

Für die Übungen gibt es entweder eigene Ausgangsverzeichnisse oder Du verwendest den zuletzt implementierten Stand als Ausgang.

Öffne dann das entsprechende Verzeichnis und wie oben beschrieben und implementiere die Übung. Die Lösung befindet sich jeweils
im nächsten Verzeichnis.
