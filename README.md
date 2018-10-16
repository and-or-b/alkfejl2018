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

* Eclipse Phonton:
	* attól függően, hogy milyen nyelven szeretnénk fejleszteni, (Java, C/C++, Javascript, stb.)
	letölthető a nyelvenek megfelelő integrált fejlesztői környezetet: "Eclipse IDE for Java Developers" 
	* fejlesztői környezeten belül elérhető az "Eclipse Marketplace", ahonna további eszközök telepíthetőek
		
* Spring Boot:
	* Spring Boot használatához: "Spring Tools 3 Add-On (aka Spring Tool Suite 3) 3.9.6 RELEASE"
	* projekt indítása: jobb klikk a projektre -> "Run as" -> "Spring Boot App"
		
* Maven:
	* szoftverprojektek menedzseléséhez
	
* Postman:						
	* végpontok kipróbálásához
							
* Git/GitHub:
	* verziókövetéshez

Adatbázis-terv: táblák kapcsolati UML diagramja
![Screenshot](db.jpg)

Alkalmazott könyvtárstruktúra bemutatása
![Screenshot](folders.bmp)

Végpont-tervek és leírások

1 db végpont működésének leírása, mi történik, milyen lépések követik egymást (szekvenciadiagram)

/admin/getAllUser/deleteUseById/{user_id}
public ResponseEntity<Void> deleteUserByAdmin(@PathVariable("user_id") Integer userId)

- kérés érkezik a userRepository-hoz, hogy a user_id alapján adja vissza a megfelelő felhasználót(user)
	- ha a felhasználó létezik, akkor kérés érkezik a cookBookRepository-hoz, hogy adja vissza a felhasználó
	szakácskönyveit(cookbook)
	- ezek egy listába kerülnek, amin végigiterálva törüljük a user_id és a cookbook_id alapján a felhasználóhoz tartozó
	szakácsönyveket és azok tartalmát. Ez a következőképpen történik:
		- kérés érkezik cookBookRepository-hoz, hogy user_id-ja és a cookbook_id alapján adja vissza a szakácsköny
		tartalmát, azaz a recepteket(recipe)
		- ha van legalább egy, az bekerül egy iterálható tárolóba
		- végigiterálunk a tároló, menet közben lekérjük a recepteket
			- a receptet először eltávolítjuk a szakácskönyvből
			- a receptetből eltávolítjuk a szakácskönyvet
			- a recept mentésre kerül recipeRepository-ba
		- ezután lekérjük ezt a receptet recipe_id alapján a recipeRepository-ból, ha nincs szakácsköny, ami hivatkozna
		rá, akkor recipe_id alapján töröljük recipeRepository-ból
		- az iteráció végén a már üres szakácskönyvet töröljük
- ezt addig ismételjük, amíg van a felhasználónak szakácskönyve
- töröljük az üres szakácsönyveket
- töröljük a felhasználót 
	
![Screenshot](endpoint.png)
