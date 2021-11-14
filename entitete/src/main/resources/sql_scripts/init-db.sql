INSERT INTO uporabnik (uporabnik_ime, uporabnik_priimek, kontakt, je_lastnik) VALUES ('Petra', 'Kos', 'pkos@gmail.com', TRUE);
INSERT INTO uporabnik (uporabnik_ime, uporabnik_priimek, kontakt, je_lastnik) VALUES ('Miha', 'Novak', 'mihanovak@gmail.com', FALSE);
INSERT INTO polnilnica (polnilnica_ime, cena, delovni_cas, st_prikljuckov) VALUES ('Polnilnica 3', 10, '7:00-19:00',4);
INSERT INTO polnilnica (polnilnica_ime, cena, delovni_cas, st_prikljuckov) VALUES ('Polnilnica 2', 11, '8:00-18:00',2);
INSERT INTO najem (termin,polnilnica_id,uporabnik_id) VALUES ('04.11.2021,8:00-15:00',1,1);
INSERT INTO najem (termin,polnilnica_id,uporabnik_id) VALUES ('04.11.2021,14:50-15:15',2,2);