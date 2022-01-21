# prpo-vaje-polnilne-postaje

*Projekt pri predmetu PRPO (Postopki razvoja programske opreme). Gre za zaledni sistem, ki bi omogocal
upravljanje sistema elektricnih polnilnic. Gre za Maven projekt.* 

Uporabljene tehnologije:
- JavaEE (CDI, JPA, JAX-RS, Servlet)
- KumuluzEE
- OpenAPI
- Keycloak
- Docker

Struktura projekta:

```
/api - REST viri
/entitete - JPA entitete
/storitve - poslovna logika z CDI zrni
```

Vzpostavitev projekta:

1. Zagon Docker postgre-jdbc kontejnerja
2. Kreiranje in konfiguracija PostgreSQL datasourca (definiran v config.yaml v /entitete)
3. Zagon projekta:
```
cd [projekt] # zamenjava z dejansko potjo
mvn clean package
java -jar ./api/target/api-1.0.0-SNAPSHOT.jar
```

Avtorja:

Brin Colnar (shogunishere),
Rok Kondič  (rokkondic)
