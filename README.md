# alkfejl2018
## Projektötlet

###### FELADAT
**Egy webes alkalmazás elkészítése, amellyel a bejelentkezett felhasználók receptjeiket szakácskönyvekbe szervezhetik. Saját recepteket és szakácskönyveket hozhatnak létre, módosíthatják és törölhetik azokat. Adminisztrátorként minden felhasználót, szakácskönyvet és receptet törölhetünk, a felhasználói fiókokhoz hozzáférhetünk.**

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
	* bárki fiókjához hozzáférhet, azok tartalmát törölheti, módosíthatja, új tartalmat adhat hozzá 
	* egy vagy az összes felhasználó törlése
	* recept törlése szakácskönyvből
	* egy felhasználóhoz tartozó szakácskönyv(ek) törlése
				
	* egy vagy az összes felhasználó, szakácskönyv, recept lekérése
				
* Közös: 
	* bejelentkezés után a funkciók használata 
	* ezeket egy előre megadott listából, vagy LDAP - authentikációval kell elvégezni

Nem funkcionális követelmények

* Felhasználóbarát, ergonomikus elrendezés és kinézet. Gyors működés. Biztonságos működés: jelszavak tárolása, funkciókhoz való hozzáférés.

* Szerepkörök

	* felhasználó: a saját maga által létrehozott fiók, receptek és szakácskönyvek módosítása és törlése
	* adminisztrátor: felhasználók, szakácskönyvek és receptek törlése, felhasználói fiókokhoz való teljes hozzáférés
