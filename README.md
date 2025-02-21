# Webbshop för Skor

Detta projekt syftar till att skapa en databas för en webbshop som säljer skor. Databasen kommer att hålla reda på produkter, kategorier, kunder och beställningar. Den inkluderar en lagrad procedur, `AddToCart`, för att hantera beställningar.

## Databasstruktur

### ER-diagram
Ett ER-diagram som beskriver databasens struktur, inklusive entiteter, relationer och attribut. 

### Relationsmodell
En relationsmodell som beskriver databasen, inklusive tabeller och relationer.

## Lagrade Procedurer

### AddToCart
En lagrad procedur som hanterar skapande och uppdatering av beställningar. Den tar följande inparametrar:
- `customerId`: ID för kunden
- `orderId`: ID för beställningen (kan vara NULL)
- `productId`: ID för produkten

#### Funktionalitet
- Om `orderId` är NULL eller inte existerar, skapas en ny beställning och produkten läggs till.
- Om beställningen redan finns, läggs produkten till i beställningen.
- Om produkten redan finns i beställningen, ökas antalet av produkten.
- Lagerantalet av produkten minskar för varje tillagd produkt.

## Databasändringar

### Tabeller
- En kolumn för `password` har lagts till i `Customer`-tabellen.
- En kolumn för `orderStatus` har lagts till i `Order`-tabellen med värdena 'BETALD' och 'AKTIV'.

## Användargränssnitt

Ett Java-program som låter användare lägga till produkter i en beställning. Gränssnittet är enkelt och kan köras i kommandoraden. Användare uppmanas att logga in med användarnamn och lösenord, välja produkter och lägga till dem i sin beställning.

### Konsolgränssnitt
Programmet ger användaren möjlighet att:
1. Logga in med användarnamn och lösenord.
2. Välja en produkt från lagret.
3. Lägga till produkten i en beställning eller skapa en ny beställning om ingen aktiv beställning finns.

## Frågor och Data

Projektet innehåller databasfrågor för att hämta relevant information, inklusive:
- Kunder som köpt specifika produkter.
- Antal produkter per kategori.
- Total summa av kunders inköp.
- Topp-5 mest sålda produkter.
- Månad med störst försäljning.


## Installation

För att installera och köra projektet:
1. Klona repositoriet.
2. Skapa databasen med hjälp av DDL-skriptet.
3. Fyll databasen med data från DML-skriptet.
4. Kör Java-programmet för att interagera med databasen.

