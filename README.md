# alkfejl2018
## Projektötlet

###### FELADAT
**Egy webes alkalmazás elkészítése, amellyel a bejelentkezett felhasználók receptjeiket szakácskönyvekbe szervezhetik. Saját recepteket és szakácskönyveket hozhatnak létre, módosíthatják és törölhetik azokat. Adminisztrátorként a felhasználókat törölhetjük és lekérhetjük.**

Funkcionális követelmények

* Felhasználó: 
	* regisztráció
	* belépés
	* személyes adatok módosítása
	* regisztráció törlése

	* recept létrehozása
	* a felhasználóhoz tartozó recept törlése
	* egy szakácskönyvhöz tartozó receptek lekérése
	* recept módosítása
				
	* szakácskönyv létrehozása
	* a felhasználóhoz tartozó szakácskönyv(ek) törlése
	* szakácskönyvek lekérése
	* szakácskönyv módosítása

* Adminisztrátor: 
	* egy vagy az összes felhasználó törlése		
	* egy vagy az összes felhasználó lekérése
				
* Közös: 
	* bejelentkezés után a funkciók használata 
	* ezeket egy előre megadott listából, vagy LDAP - authentikációval kell elvégezni

Nem funkcionális követelmények

* Felhasználóbarát, ergonomikus elrendezés és kinézet. Gyors működés. Biztonságos működés: jelszavak tárolása, funkciókhoz való hozzáférés.

Szerepkörök

* felhasználó: a saját maga által létrehozott fiók, receptek és szakácskönyvek módosítása és törlése
* adminisztrátor: felhasználók, szakácskönyvek és receptek törlése, felhasználói fiókokhoz való teljes hozzáférés

## Backend megvalósítása
Fejlesztői környezet bemutatása, beállítása, használt technológiák
Adatbázis-terv: táblák kapcsolati UML diagramja
Alkalmazott könyvtárstruktúra bemutatása
Végpont-tervek és leírások
1 db végpont működésének leírása, mi történik, milyen lépések követik egymást (szekvenciadiagram)
