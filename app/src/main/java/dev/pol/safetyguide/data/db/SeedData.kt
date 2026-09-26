/*
 * Content based on:
 * - "Poradnik Bezpieczeństwa" (Safety Guide) - Government of the Republic of Poland, 2025
 * - "Instrukcje reagowania - zagrożenie atakiem z powietrza" - MWSWiA/RCB
 * Sources: https://poradnikbezpieczenstwa.gov.pl, https://www.gov.pl/web/rcb
 *
 * Under Article 4 of the Polish Copyright Act, official documents
 * are not protected by copyright and are in the public domain.
 */
package dev.pol.safetyguide.data.db

import dev.pol.safetyguide.data.model.Category
import dev.pol.safetyguide.data.model.ChecklistItem
import dev.pol.safetyguide.data.model.SupplyItem

object SeedData {

    fun getCategories(): List<Category> = listOf(
        Category("personal_prep", "Przygotowanie osobiste", "🏃", 1),
        Category("home_supplies", "Zapasy domowe", "📦", 2),
        Category("home_safety", "Dom i otoczenie", "🏠", 3),
        Category("evac_bag", "Plecak ewakuacyjny", "🎒", 4),
        Category("animals", "Zwierzęta", "🐾", 5),
        Category("school_work", "Szkoła i praca", "🏫", 6),
        Category("alarm_signals", "Sygnały alarmowe", "🚨", 7),
        Category("evacuation", "Ewakuacja", "🚶", 8),
        Category("crowd_safety", "Bezpieczeństwo w tłumie", "👥", 9),
        Category("fire", "Pożar", "🔥", 10),
        Category("flood", "Powódź", "🌊", 11),
        Category("blackout", "Blackout", "🔋", 12),
        Category("air_attack", "Atak z powietrza", "✈️", 13),
        Category("cbrn", "Zagrożenia CBRN", "☢️", 14),
        Category("suspicious", "Niepokojące zachowania", "👁️", 15),
        Category("terrorism", "Zagrożenia terrorystyczne", "⚠️", 16),
        Category("digital", "Zagrożenia cyfrowe", "💻", 17),
        Category("first_aid", "Pierwsza pomoc", "🩹", 18),
        Category("hygiene", "Higiena w kryzysie", "🧼", 19),
        Category("crisis_plan", "Plan na kryzys", "📋", 20),
        Category("safety_decalogue", "Dekalog bezpieczeństwa", "✅", 21)
    )

    fun getChecklistItems(): List<ChecklistItem> = listOf(
        // === 1. Przygotowanie osobiste ===
        ChecklistItem("p1", "personal_prep", "Regularnie się odżywiaj i śpij wystarczająco"),
        ChecklistItem("p2", "personal_prep", "Zadbaj o zdrowie psychiczne", "Na Twoje emocje duży wpływ mają wiadomości, które do Ciebie docierają – analizuj krytycznie ich treść"),
        ChecklistItem("p3", "personal_prep", "Miej przy sobie niezbędne leki i urządzenia wspomagające", "Poinformuj najbliższych o chorobach i alergiach"),
        ChecklistItem("p4", "personal_prep", "Trzymaj dokumentację medyczną w jednym miejscu"),
        ChecklistItem("p5", "personal_prep", "Noś kartę grupy krwi"),
        ChecklistItem("p6", "personal_prep", "Korzystaj z programów profilaktycznych (pacjent.gov.pl)"),
        ChecklistItem("p7", "personal_prep", "Powtarzaj szczepienia (błonica, krztusiec, tężec - co 10 lat)"),
        ChecklistItem("p8", "personal_prep", "Regularnie oddawaj krew (jeśli możliwe)"),
        ChecklistItem("p9", "personal_prep", "Nawiąż kontakt z sąsiadami"),
        ChecklistItem("p10", "personal_prep", "Podtrzymuj kontakty z osobami, na których możesz polegać"),
        ChecklistItem("p11", "personal_prep", "Nie wahaj się korzystać z pomocy psychologicznej"),

        // === 2. Zapasy domowe (checklist items) ===
        ChecklistItem("s16", "home_supplies", "Naładowany telefon"),
        ChecklistItem("s17", "home_supplies", "Powerbank i ładowarka"),
        ChecklistItem("s18", "home_supplies", "Przeglądaj zapasy co kilka miesięcy"),
        ChecklistItem("s19", "home_supplies", "Sprawdzaj terminy ważności"),

        // === 3. Dom i otoczenie ===
        ChecklistItem("h1", "home_safety", "Gaśnica i koc gaśniczy"),
        ChecklistItem("h2", "home_safety", "Czujki dymu, czadu i gazu"),
        ChecklistItem("h3", "home_safety", "Usuń przedmioty z korytarzy i klatek schodowych"),
        ChecklistItem("h4", "home_safety", "Oznacz miejsca odcięcia gazu, prądu i wody"),
        ChecklistItem("h5", "home_safety", "Przećwicz z najbliższymi wyłączanie instalacji"),
        ChecklistItem("h6", "home_safety", "Sprawdź najbezpieczniejsze miejsce w domu (z dala od okien, przy ścianach nośnych)"),
        ChecklistItem("h7", "home_safety", "Przygotuj przedmioty do uszczelniania okien i drzwi"),
        ChecklistItem("h8", "home_safety", "Przeglądy instalacji: kominowej, wentylacyjnej, gazowej, elektrycznej"),
        ChecklistItem("h9", "home_safety", "Ubezpieczenie domu/mieszkania"),
        ChecklistItem("h10", "home_safety", "Sprawny technicznie i zatankowany samochód"),
        ChecklistItem("h11", "home_safety", "Apteczka, gaśnica, trójkąt ostrzegawczy"),
        ChecklistItem("h12", "home_safety", "Koło zapasowe i zestaw naprawczy"),
        ChecklistItem("h13", "home_safety", "Karta ratownicza w pojeździe"),
        ChecklistItem("h14", "home_safety", "CB radio lub walkie-talkie"),
        ChecklistItem("h15", "home_safety", "Papierowa mapa lub atlas samochodowy"),
        ChecklistItem("h16", "home_safety", "Mapy offline pobrane wcześniej"),

        // === 4. Plecak ewakuacyjny ===
        ChecklistItem("e1", "evac_bag", "Dokumenty + kopie na pendrive"),
        ChecklistItem("e2", "evac_bag", "Środki higieniczne i do dezynfekcji"),
        ChecklistItem("e3", "evac_bag", "Gotówka w różnych nominałach"),
        ChecklistItem("e4", "evac_bag", "Latarka i radio na baterie"),
        ChecklistItem("e5", "evac_bag", "Naładowany telefon i powerbank"),
        ChecklistItem("e6", "evac_bag", "Ładowarka i pasujące kable"),
        ChecklistItem("e7", "evac_bag", "Zapasowe baterie"),
        ChecklistItem("e8", "evac_bag", "Ubranie dopasowane do pory roku"),
        ChecklistItem("e9", "evac_bag", "Odzież przeciwdeszczowa"),
        ChecklistItem("e10", "evac_bag", "Śpiwór, karimata, folia termiczna"),
        ChecklistItem("e11", "evac_bag", "Alternatywna łączność (walkie-talkie)"),
        ChecklistItem("e12", "evac_bag", "Żywność wysokoodżywcza (batony, suszone owoce)"),
        ChecklistItem("e13", "evac_bag", "Ważna rzecz osobista (zdjęcie, pamiątka rodzinna)"),
        ChecklistItem("e14", "evac_bag", "Scyzoryk/multitool"),
        ChecklistItem("e15", "evac_bag", "Zapalniczka"),
        ChecklistItem("e16", "evac_bag", "Worki na śmieci"),
        ChecklistItem("e17", "evac_bag", "Mapy drukowane"),

        // === 5. Zwierzęta ===
        // Zwierzęta domowe
        ChecklistItem("a1", "animals", "Dokumentacja zdrowotna i potwierdzenia szczepień"),
        ChecklistItem("a2", "animals", "Sprzęt do transportu (kaganiec, obroża, szelki, smycz, transporter)"),
        ChecklistItem("a3", "animals", "Zapas karmy, wody i leków na kilka dni"),
        ChecklistItem("a4", "animals", "Zwierzę zaczipowane i zarejestrowane"),
        ChecklistItem("a5", "animals", "Dane kontaktowe właściciela na obroży/szelkach"),
        // Zwierzęta gospodarskie
        ChecklistItem("a6", "animals", "Zapas paszy i wody na 14 dni"),
        ChecklistItem("a7", "animals", "Zbiorniki na deszczówkę"),
        ChecklistItem("a8", "animals", "Budynki i ogrodzenia odporne na warunki atmosferyczne"),
        ChecklistItem("a9", "animals", "Materiały do naprawy"),
        ChecklistItem("a10", "animals", "Zwierzęta oznakowane (kolczyki, farba)"),

        // === 6. Szkoła i praca ===
        ChecklistItem("w1", "school_work", "Udział w próbnych alarmach"),
        ChecklistItem("w2", "school_work", "Kontakt do osoby wyznaczonej przez szkołę"),
        ChecklistItem("w3", "school_work", "Sprawdź wyjścia ewakuacyjne w pracy"),
        ChecklistItem("w4", "school_work", "Znajdź miejsca zbiórki, gaśnice, defibrylatory (AED), apteczki"),
        ChecklistItem("w5", "school_work", "Zgłaszaj nieprawidłowości (uszkodzone instalacje, zablokowane drogi)"),
        ChecklistItem("w6", "school_work", "Przećwicz plan awaryjny ze współpracownikami"),
        ChecklistItem("w7", "school_work", "Poinformuj pracodawcę o ewentualnej mobilizacji"),

        // === 7. Sygnały alarmowe ===
        ChecklistItem("al1", "alarm_signals", "Ogłoszenie alarmu: ciągły, modulowany dźwięk syreny (3 minuty)"),
        ChecklistItem("al2", "alarm_signals", "Odwołanie alarmu: ciągły, jednostajny dźwięk syreny (3 minuty)"),
        ChecklistItem("al3", "alarm_signals", "Zainstaluj aplikację RSO (Regionalny System Ostrzegania)"),

        // === 8. Ewakuacja ===
        ChecklistItem("ep1", "evacuation", "Zamknij okna"),
        ChecklistItem("ep2", "evacuation", "Zakręć dopływ wody"),
        ChecklistItem("ep3", "evacuation", "Wyłącz urządzenia elektryczne i gazowe"),
        ChecklistItem("ep4", "evacuation", "Wygaś źródła ognia (piec, kominek, kuchenka)"),
        ChecklistItem("ep5", "evacuation", "Ubierz się stosownie do pogody"),
        ChecklistItem("ep6", "evacuation", "Upewnij się, że dzieci mają dane kontaktowe opiekuna"),
        ChecklistItem("ep7", "evacuation", "Zabierz plecak ewakuacyjny"),
        ChecklistItem("ep8", "evacuation", "Sprawdź, czy sąsiedzi wiedzą o alarmie"),
        ChecklistItem("ep9", "evacuation", "Pomóż osobom ze szczególnymi potrzebami"),
        ChecklistItem("ep10", "evacuation", "Skorzystaj ze zorganizowanego transportu lub idź pieszo"),
        ChecklistItem("ep11", "evacuation", "Nie blokuj dróg ewakuacyjnych samochodem"),
        ChecklistItem("ep12", "evacuation", "Zabezpiecz zwierzęta"),
        ChecklistItem("ep13", "evacuation", "Powiadom bliskich o ewakuacji (sposób, kierunek)"),
        ChecklistItem("ep14", "evacuation", "NIE oddawaj dokumentów osobom oferującym pomoc"),
        ChecklistItem("ep15", "evacuation", "Prześlij bliskim numer rejestracyjny pojazdu i adres"),

        // === 9. Bezpieczeństwo w tłumie ===
        ChecklistItem("c1", "crowd_safety", "Sprawdź wyjścia ewakuacyjne przed wydarzeniem"),
        ChecklistItem("c2", "crowd_safety", "W razie paniki - poruszaj się z tłumem"),
        ChecklistItem("c3", "crowd_safety", "Unikaj ciasnych przejść, szklanych powierzchni, ścian"),
        ChecklistItem("c4", "crowd_safety", "Nie próbuj podnosić przedmiotów z ziemi"),
        ChecklistItem("c5", "crowd_safety", "Jeśli upadniesz - skul się i osłoń głowę"),
        ChecklistItem("c6", "crowd_safety", "Zaginiona osoba → kontakt z ochroną/policją + opis"),

        // === 10. Pożar ===
        ChecklistItem("f1", "fire", "Wezwij straż pożarną (112)"),
        ChecklistItem("f2", "fire", "Próbuj ugasić, jeśli ogień jest nieduży"),
        ChecklistItem("f3", "fire", "Zakręć główny zawór gazu"),
        ChecklistItem("f4", "fire", "Wyłącz główne wyłączniki prądu"),
        ChecklistItem("f5", "fire", "Nie otwieraj okien ani drzwi"),
        ChecklistItem("f6", "fire", "Chroń drogi oddechowe (mokry materiał)"),
        ChecklistItem("f7", "fire", "NIE gaś wodą: urządzeń elektrycznych, olejów, tłuszczu"),
        ChecklistItem("f8", "fire", "Użyj koca gaśniczego na materiały stałe"),
        ChecklistItem("f9", "fire", "Przykryj garnek/patelnię pokrywką (tłuszcze)"),
        ChecklistItem("f10", "fire", "Odetnij dopływ gazu (gazy)"),

        // === 11. Powódź ===
        ChecklistItem("fl1", "flood", "Przygotuj plecak ewakuacyjny"),
        ChecklistItem("fl2", "flood", "Sprawdź, czy sąsiedzi potrzebują pomocy"),
        ChecklistItem("fl3", "flood", "Przygotuj worki z piaskiem"),
        ChecklistItem("fl4", "flood", "Uszczelnij drzwi i okna"),
        ChecklistItem("fl5", "flood", "Przenieś wartościowe rzeczy na górne piętra"),
        ChecklistItem("fl6", "flood", "Wyłącz instalację elektryczną i gazową"),
        ChecklistItem("fl7", "flood", "Zabezpiecz włazy i wyloty kanalizacji"),
        ChecklistItem("fl8", "flood", "Zaparkuj pojazdy w bezpiecznym miejscu"),
        ChecklistItem("fl9", "flood", "Przygotuj zwierzęta do ewakuacji"),
        ChecklistItem("fl10", "flood", "Biała flaga → muszę się ewakuować"),
        ChecklistItem("fl11", "flood", "Czerwona flaga → potrzebuję pomocy medycznej"),
        ChecklistItem("fl12", "flood", "Niebieska flaga → potrzebuję żywności i wody"),

        // === 12. Blackout ===
        ChecklistItem("bl1", "blackout", "Alternatywne źródła światła (latarki + baterie)"),
        ChecklistItem("bl2", "blackout", "Alternatywne ogrzewanie (piecyk gazowy/olejowy, kominek)"),
        ChecklistItem("bl3", "blackout", "Radio na baterie, krótkofalówki, CB radio"),
        ChecklistItem("bl4", "blackout", "Rozważ agregat prądotwórczy"),
        ChecklistItem("bl5", "blackout", "Naładuj powerbanki"),
        ChecklistItem("bl6", "blackout", "Włącz tryb oszczędzania energii w telefonie"),
        ChecklistItem("bl7", "blackout", "Żywność gotowa do spożycia"),
        ChecklistItem("bl8", "blackout", "Gotówka w różnych nominałach"),
        ChecklistItem("bl9", "blackout", "Oszczędzaj ciepło - zbierz domowników w jednym pokoju"),
        ChecklistItem("bl10", "blackout", "Ogranicz otwieranie lodówki/zamrażarki"),
        ChecklistItem("bl11", "blackout", "Odłącz urządzenia elektryczne od zasilania"),

        // === 13. Atak z powietrza ===
        // Przygotowanie przed zagrożeniem
        ChecklistItem("aa1", "air_attack", "Ustal z rodziną sposób postępowania na wypadek zagrożenia", "Przygotowanie"),
        ChecklistItem("aa2", "air_attack", "Ustal miejsce spotkania z rodziną", "Przygotowanie"),
        ChecklistItem("aa3", "air_attack", "Ustal sposób wzajemnego kontaktu", "Przygotowanie"),
        ChecklistItem("aa4", "air_attack", "Sprawdź, gdzie znajduje się najbliższy punkt schronienia (gdziesieukryc.pl)", "Przygotowanie"),
        ChecklistItem("aa5", "air_attack", "Poznaj sygnały alarmowe (aplikacja Mobywatel, gov.pl)", "Przygotowanie"),
        ChecklistItem("aa6", "air_attack", "Przeczytaj \"Poradnik Bezpieczeństwa\"", "Przygotowanie"),
        ChecklistItem("aa7", "air_attack", "Przygotuj plecak ewakuacyjny", "Przygotowanie"),
        // Miejsca schronienia
        ChecklistItem("aa8", "air_attack", "Znajdź najbliższy oznakowany punkt schronienia (wskazany przez służby)", "Miejsca schronienia"),
        ChecklistItem("aa9", "air_attack", "Sprawdź budynek o solidnych ścianach i stropach (bez widocznych uszkodzeń)", "Miejsca schronienia"),
        ChecklistItem("aa10", "air_attack", "Rozważ odpowiednio przygotowany garaż podziemny", "Miejsca schronienia"),
        ChecklistItem("aa11", "air_attack", "Rozważ piwnicę, tunel lub przejście podziemne", "Miejsca schronienia"),
        ChecklistItem("aa12", "air_attack", "Wybierz centralne pomieszczenie własnego mieszkania lub domu (zasada dwóch ścian)", "Miejsca schronienia"),
        ChecklistItem("aa13", "air_attack", "Wybieraj pomieszczenia na najniższej kondygnacji", "Miejsca schronienia"),
        // Decyzja podczas alarmu
        ChecklistItem("aa14", "air_attack", "Jeśli oznaczony punkt schronienia jest dostępny → idź tam", "Decyzja podczas alarmu"),
        ChecklistItem("aa15", "air_attack", "Zabierz plecak ewakuacyjny oraz niezbędne leki", "Decyzja podczas alarmu"),
        ChecklistItem("aa16", "air_attack", "Unikaj wind – poruszaj się wyłącznie schodami", "Decyzja podczas alarmu"),
        ChecklistItem("aa17", "air_attack", "Jeśli nie możesz bezpiecznie dotrzeć do punktu schronienia → pozostań w budynku", "Decyzja podczas alarmu"),
        ChecklistItem("aa18", "air_attack", "Przejdź do centralnego pomieszczenia bez okien, z dala od ścian zewnętrznych", "Decyzja podczas alarmu"),
        ChecklistItem("aa19", "air_attack", "Stosuj zasadę dwóch ścian", "Decyzja podczas alarmu"),
        ChecklistItem("aa20", "air_attack", "Jeśli dotarcie wymagałoby długiego przebywania na zewnątrz → nie próbuj za wszelką cenę", "Decyzja podczas alarmu"),
        ChecklistItem("aa21", "air_attack", "Wykonuj polecenia służb, stosuj się do alertu RCB i oficjalnych komunikatów", "Decyzja podczas alarmu"),
        ChecklistItem("aa22", "air_attack", "Opuść pojazd (gdy jesteś w samochodzie lub innym pojeździe)", "Decyzja podczas alarmu"),
        ChecklistItem("aa23", "air_attack", "Zaparkuj pojazd tak, aby nie utrudniać przejazdu służbom ratowniczym", "Decyzja podczas alarmu"),
        ChecklistItem("aa24", "air_attack", "Na otwartym terenie: padnij na ziemię w zagłębieniu i osłoń głowę (gdy usłyszysz eksplozję)", "Decyzja podczas alarmu"),
        // Wybór pomieszczenia
        ChecklistItem("aa25", "air_attack", "Wybierz pomieszczenie bez okien lub z jak najmniejszą powierzchnią przeszkleń", "Wybór pomieszczenia"),
        ChecklistItem("aa26", "air_attack", "Położone możliwie blisko środka budynku", "Wybór pomieszczenia"),
        ChecklistItem("aa27", "air_attack", "Oddalone od balkonów i ścian zewnętrznych", "Wybór pomieszczenia"),
        ChecklistItem("aa28", "air_attack", "Pozbawione kotła, piecyka gazowego, butli gazowych, dużych luster i ciężkich przedmiotów wiszących", "Wybór pomieszczenia"),
        ChecklistItem("aa29", "air_attack", "Odpowiednie pomieszczenia: korytarz, przedpokój, łazienka, garderoba, piwnica, garaż podziemny, komórka", "Wybór pomieszczenia"),
        ChecklistItem("aa30", "air_attack", "Klatka schodowa służy do przemieszczania się – nie wybieraj jej jako miejsca schronienia, jeśli ma duże okna", "Wybór pomieszczenia"),
        ChecklistItem("aa31", "air_attack", "Łazienkę wybierz tylko gdy nie ma okna, kotła ani piecyka gazowego", "Wybór pomieszczenia"),
        // Piwnica lub garaż podziemny
        ChecklistItem("aa32", "air_attack", "Sprawdź: solidne ściany i stropy, bez widocznych uszkodzeń", "Piwnica/garaż podziemny"),
        ChecklistItem("aa33", "air_attack", "Sprawdź: drożne wejście i bezpieczną drogę wyjścia", "Piwnica/garaż podziemny"),
        ChecklistItem("aa34", "air_attack", "Sprawdź: oświetlenie awaryjne i sprawną wentylację", "Piwnica/garaż podziemny"),
        ChecklistItem("aa35", "air_attack", "Sprawdź: wolna od paliw, butli gazowych, chemicalli i innych materiałów łatwopalnych", "Piwnica/garaż podziemny"),
        ChecklistItem("aa36", "air_attack", "Nie korzystaj z piwnicy z uszkodzonym stropem, pęknięciami konstrukcyjnymi, jednym wyjściem lub instalacją gazową", "Piwnica/garaż podziemny"),
        ChecklistItem("aa37", "air_attack", "Wybierz strefę możliwie daleko od bramy i rampy wjazdowej", "Piwnica/garaż podziemny"),
        ChecklistItem("aa38", "air_attack", "Z dala od ładowarek i pomieszczeń technicznych", "Piwnica/garaż podziemny"),
        ChecklistItem("aa39", "air_attack", "Nie uruchamiaj silników", "Piwnica/garaż podziemny"),
        ChecklistItem("aa40", "air_attack", "Nie blokuj przejść ani dróg wyjazdowych", "Piwnica/garaż podziemny"),
        // Ograniczenie zagrożenia pożarowego
        ChecklistItem("aa41", "air_attack", "Usuń wcześniej: benzynę, paliwa, farby, rozpuszczalniki", "Ograniczenie zagrożenia pożarowego"),
        ChecklistItem("aa42", "air_attack", "Usuń wcześniej: butle gazowe, materiały pirotechniczne", "Ograniczenie zagrożenia pożarowego"),
        ChecklistItem("aa43", "air_attack", "Usuń wcześniej: drewno, papier i inne materiały łatwopalne", "Ograniczenie zagrożenia pożarowego"),
        ChecklistItem("aa44", "air_attack", "Usuń wcześniej: przedmioty, które mogą spaść, stłuc się albo zablokować wyjście", "Ograniczenie zagrożenia pożarowego"),
        ChecklistItem("aa45", "air_attack", "Podczas zagrożenia nie trać czasu na porządkowanie – jak najszybciej zajmij najbezpieczniejsze miejsce", "Ograniczenie zagrożenia pożarowego"),
        ChecklistItem("aa46", "air_attack", "Upewnij się, że drogi do schronienia oraz wyjścia podstawowe i awaryjne są drożne", "Ograniczenie zagrożenia pożarowego"),

        // === 14. Zagrożenia CBRN ===
        ChecklistItem("cb1", "cbrn", "Opuść niebezpieczny obszar"),
        ChecklistItem("cb2", "cbrn", "Zamknij i uszczelnij okna, drzwi, otwory wentylacyjne"),
        ChecklistItem("cb3", "cbrn", "Wyłącz klimatyzację"),
        ChecklistItem("cb4", "cbrn", "W samochodzie: zamknij okna, wyłącz wentylację, wyjedź ze strefy"),
        ChecklistItem("cb5", "cbrn", "Po przyjściu: zdejmij skażone ubranie"),
        ChecklistItem("cb6", "cbrn", "Nie jedz skażonych produktów"),
        ChecklistItem("cb7", "cbrn", "Umyj ręce, potem twarz; weź prysznic"),
        ChecklistItem("cb8", "cbrn", "Wrzuć skażone przedmioty do worków, szczelnie zamknij i oznacz"),
        ChecklistItem("cb9", "cbrn", "Przygotuj się na kilka dni w uszczelnionym budynku"),

        // === 15. Niepokojące zachowania ===
        ChecklistItem("sb1", "suspicious", "Nieuzasadnione próby kontaktu osób oferujących 'łatwy zarobek'"),
        ChecklistItem("sb2", "suspicious", "Zmiany w zachowaniu osób z otoczenia"),
        ChecklistItem("sb3", "suspicious", "Podejrzane oznaczenia (graffiti, symbole na skrzynkach, murach)"),
        ChecklistItem("sb4", "suspicious", "Osoby obserwujące/filmujące obiekty (lotniska, centra handlowe, wojsko)"),
        ChecklistItem("sb5", "suspicious", "Zawiadom Policję lub ABW"),
        ChecklistItem("sb6", "suspicious", "W razie podejrzenia przestępstwa → dzwoń 112"),
        ChecklistItem("sb7", "suspicious", "Skorzystaj z Krajowej Mapy Zagrożeń (policja.pl)"),

        // === 16. Zagrożenia terrorystyczne ===
        ChecklistItem("t1", "terrorism", "UCIEKAJ - nie podchodź, ostrzegaj innych, dzwoń na 112"),
        ChecklistItem("t2", "terrorism", "CHOWAJ SIĘ - z dala od tłumu, za mury, stalowe konstrukcje"),
        ChecklistItem("t3", "terrorism", "Zabarykaduj drzwi, zasłoń okna"),
        ChecklistItem("t4", "terrorism", "Wyłącz światło, wycisz telefon"),
        ChecklistItem("t5", "terrorism", "WALCZ - jeśli nie możesz uciec/chować się"),
        ChecklistItem("t6", "terrorism", "Dzwoń tylko jeśli musisz - nie blokuj sieci"),
        ChecklistItem("t7", "terrorism", "Nie dzwoń do osób w zagrożeniu (możesz zdradzić kryjówkę)"),
        ChecklistItem("t8", "terrorism", "Nie udostępniaj zdjęć i filmów z miejsca zdarzenia"),
        ChecklistItem("t9", "terrorism", "Nie rozpowszechniaj niesprawdzonych informacji"),

        // === 17. Zagrożenia cyfrowe ===
        ChecklistItem("d1", "digital", "Sprawdzaj informacje w kilku niezależnych źródłach"),
        ChecklistItem("d2", "digital", "Nie rozpowszechniaj niesprawdzonych informacji"),
        ChecklistItem("d3", "digital", "Nie publikuj zdjęć wojska, mostów, stacji, magazynów"),
        ChecklistItem("d4", "digital", "Nie klikaj pochopnie w linki/załączniki"),
        ChecklistItem("d5", "digital", "Sprawdzaj adresy nadawców"),
        ChecklistItem("d6", "digital", "Chroń dane logowania i numery kart"),
        ChecklistItem("d7", "digital", "Aktualizuj antywirusowy i aplikacje"),
        ChecklistItem("d8", "digital", "Korzystaj z legalnego oprogramowania"),
        ChecklistItem("d9", "digital", "Nie instaluj aplikacji z niesprawdzonych źródeł"),
        ChecklistItem("d10", "digital", "Ustaw silne hasła (menedżer haseł)"),
        ChecklistItem("d11", "digital", "Korzystaj z weryfikacji dwuetapowej"),
        ChecklistItem("d12", "digital", "Rób kopie zapasowe ważnych plików"),
        ChecklistItem("d13", "digital", "Ostrożność w publicznych sieciach Wi-Fi"),
        ChecklistItem("d14", "digital", "Nie wierz w doniesienia o upadku państwa"),
        ChecklistItem("d15", "digital", "Podejrzane SMS-y → wyślij pod 8080"),
        ChecklistItem("d16", "digital", "Incydenty → incydent.cert.pl lub aplikacja mObywatel"),

        // === 18. Pierwsza pomoc ===
        // Podstawowe kroki
        ChecklistItem("fa1", "first_aid", "Zadbaj o bezpieczeństwo własne i poszkodowanego"),
        ChecklistItem("fa2", "first_aid", "Oceń stan poszkodowanego (potrząśnij, zapytaj 'Słyszysz mnie?')"),
        ChecklistItem("fa3", "first_aid", "Zadzwoń 112 lub wskaż konkretną osobę"),
        // Brak kontaktu
        ChecklistItem("fa4", "first_aid", "Poproś o przyniesienie defibrylatora (AED)"),
        ChecklistItem("fa5", "first_aid", "Sprawdź, czy oddycha (obserwuj ruchy klatki piersiowej)"),
        ChecklistItem("fa6", "first_aid", "Jeśli oddycha → pozycja boczna ustalona, obserwuj"),
        // RKO
        ChecklistItem("fa7", "first_aid", "Połóż na plecach, na twardym podłożu"),
        ChecklistItem("fa8", "first_aid", "Rozepnij odzież, odchyl głowę do tyłu"),
        ChecklistItem("fa9", "first_aid", "Sprawdzaj oddech 10 sekund (obserwuj, nasłuchuj, poczuj na policzku)"),
        ChecklistItem("fa10", "first_aid", "Uciskaj klatkę piersiową: 5-6 cm, 2 uciski/sekundę"),
        ChecklistItem("fa11", "first_aid", "Po 30 uciśnięciach: 2 wdechy (lub kontynuuj bez przerw)"),
        ChecklistItem("fa12", "first_aid", "Użyj AED gdy ktoś przyniesie (urządzenie podpowie)"),
        ChecklistItem("fa13", "first_aid", "Prowadź reanimację do przybycia ratowników"),
        // Obfity krwotok
        ChecklistItem("fa14", "first_aid", "Załóż opatrunek uciskowy"),
        ChecklistItem("fa15", "first_aid", "Załóż opaskę do tamowania powyżej rany (zapisz godzinę)"),
        ChecklistItem("fa16", "first_aid", "Uciskaj ranę dłonią/tkaniną"),
        ChecklistItem("fa17", "first_aid", "Unieś nogi na ~30 cm (jeśli blady i spocony)"),
        ChecklistItem("fa18", "first_aid", "Okryj poszkodowanego"),
        ChecklistItem("fa19", "first_aid", "Monitoruj stan do przybycia ratowników"),

        // === 19. Higiena w kryzysie ===
        ChecklistItem("hy1", "hygiene", "Stwórz prowizoryczną toaletę (wiadro + plastikowa torba)"),
        ChecklistItem("hy2", "hygiene", "Neutralizuj zapachy (ziemia, trociny, żwirek)"),
        ChecklistItem("hy3", "hygiene", "Stosuj chusteczki nawilżane i płyny do dezynfekcji"),
        ChecklistItem("hy4", "hygiene", "Słuchaj komunikatów służb komunalnych"),

        // === 20. Plan na kryzys ===
        ChecklistItem("cp1", "crisis_plan", "Opracuj rodzinny plan na wypadek kryzysu"),
        ChecklistItem("cp2", "crisis_plan", "Ustal dane kontaktowe wszystkich"),
        ChecklistItem("cp3", "crisis_plan", "Ustal miejsca spotkań (w okolicy i poza miejscowością)"),
        ChecklistItem("cp4", "crisis_plan", "Wypełnij rubryki w poradniku lub przygotuj samodzielnie"),
        ChecklistItem("cp5", "crisis_plan", "Regularnie aktualizuj plan"),
        ChecklistItem("cp6", "crisis_plan", "Ćwicz plan z domownikami"),

        // === 21. Dekalog bezpieczeństwa ===
        ChecklistItem("sd1", "safety_decalogue", "Korzystaj z wiarygodnych źródeł informacji (rządowych)"),
        ChecklistItem("sd2", "safety_decalogue", "Opracuj i przećwicz rodzinny plan kryzysowy"),
        ChecklistItem("sd3", "safety_decalogue", "Przygotuj zapasy na minimum 3 dni i przeglądaj je"),
        ChecklistItem("sd4", "safety_decalogue", "Skompletuj apteczkę z lekami"),
        ChecklistItem("sd5", "safety_decalogue", "Naucz się udzielać pierwszej pomocy"),
        ChecklistItem("sd6", "safety_decalogue", "Regularnie rób przeglądy instalacji"),
        ChecklistItem("sd7", "safety_decalogue", "Dla dzieci i seniorów: identyfikatory z danymi kontaktowymi"),
        ChecklistItem("sd8", "safety_decalogue", "Zwierzęta: zaczipuj lub oznakuj"),
        ChecklistItem("sd9", "safety_decalogue", "Sprawdź, gdzie jest najbliższe miejsce schronienia"),
        ChecklistItem("sd10", "safety_decalogue", "Skompletuj plecak ewakuacyjny"),
        ChecklistItem("sd11", "safety_decalogue", "Słuchaj poleceń służb i współdziałaj z innymi")
    )

    fun getSupplyItems(): List<SupplyItem> = listOf(
        // === 2. Zapasy domowe ===
        SupplyItem("ss1", "home_supplies", "Woda butelkowana", 0, "L", 9),
        SupplyItem("ss2", "home_supplies", "Żywność gotowa do spożycia (konserwy, suchary, batony)", 0, "opak", 5),
        SupplyItem("ss3", "home_supplies", "Leki przyjmowane na stałe", 0, "opak", 2),
        SupplyItem("ss4", "home_supplies", "Leki przeciwbólowe, przeciwzapalne, przeciwwymiotne, przeciwbiegunkowe", 0, "opak", 1),
        SupplyItem("ss5", "home_supplies", "Gazy, bandaż, opatrunki na oparzenia", 0, "opak", 2),
        SupplyItem("ss6", "home_supplies", "Rękawiczki jednorazowe", 0, "szt", 10),
        SupplyItem("ss7", "home_supplies", "Środki antyseptyczne", 0, "opak", 1),
        SupplyItem("ss8", "home_supplies", "Opaska do tamowania krwotoków", 0, "szt", 2),
        SupplyItem("ss9", "home_supplies", "Maseczki FFP3", 0, "szt", 10),
        SupplyItem("ss10", "home_supplies", "Folia termiczna", 0, "szt", 2),
        SupplyItem("ss11", "home_supplies", "Papier toaletowy", 0, "opak", 4),
        SupplyItem("ss12", "home_supplies", "Chusteczki nawilżane", 0, "opak", 3),
        SupplyItem("ss13", "home_supplies", "Środki dezynfekujące", 0, "opak", 2),
        SupplyItem("ss14", "home_supplies", "Worki na śmieci", 0, "opak", 2),
        SupplyItem("ss15", "home_supplies", "Latarka i radio na baterie/na korbkę", 0, "szt", 2),
        SupplyItem("ss16", "home_supplies", "Termometr i nożyczki", 0, "szt", 1),
        SupplyItem("ss17", "home_supplies", "Podpaski, pieluchy (jeśli potrzebne)", 0, "opak", 1),
        SupplyItem("ss18", "home_supplies", "Baterie zapasowe", 0, "szt", 8),
        SupplyItem("ss19", "home_supplies", "Świece do użytku domowego", 0, "szt", 6),
        SupplyItem("ss20", "home_supplies", "Koce, śpiwory i ciepła odzież", 0, "szt", 2),
        SupplyItem("ss21", "home_supplies", "Gotówka w różnych nominałach", 0, "kpl", 1),
        SupplyItem("ss22", "home_supplies", "Taśmy, folie, zestawy do uszczelniania", 0, "opak", 2),
        SupplyItem("ss23", "home_supplies", "Wiadro z pokrywą", 0, "szt", 1),
        SupplyItem("ss24", "home_supplies", "Zapalniczka", 0, "szt", 1),
        SupplyItem("ss25", "home_supplies", "Alternatywne źródło ogrzewania (nie na prąd)", 0, "szt", 1),

        // === 4. Plecak ewakuacyjny ===
        SupplyItem("se1", "evac_bag", "Woda butelkowana + filtry/tabletki do uzdatniania", 0, "L", 3),
        SupplyItem("se2", "evac_bag", "Apteczka i leki osobiste", 0, "opak", 1),
        SupplyItem("se3", "evac_bag", "Środki higieniczne i do dezynfekcji", 0, "opak", 1),
        SupplyItem("se4", "evac_bag", "Gotówka w różnych nominałach", 0, "kpl", 1),
        SupplyItem("se5", "evac_bag", "Żywność wysokoodżywcza (batony, suszone owoce, bakalie)", 0, "opak", 2),

        // === 10. Pożar ===
        SupplyItem("sf1", "fire", "Gaśnica ABC", 0, "szt", 1),
        SupplyItem("sf2", "fire", "Koc gaśniczy", 0, "szt", 1),

        // === 12. Blackout ===
        SupplyItem("sb1", "blackout", "Latarka z bateriami", 0, "szt", 2),
        SupplyItem("sb2", "blackout", "Radio na baterie", 0, "szt", 1),
        SupplyItem("sb3", "blackout", "Żywność gotowa do spożycia", 0, "opak", 5),
        SupplyItem("sb4", "blackout", "Gotówka w różnych nominałach", 0, "kpl", 1),

        // === 19. Higiena w kryzysie ===
        SupplyItem("sh1", "hygiene", "Chusteczki nawilżane", 0, "opak", 3),
        SupplyItem("sh2", "hygiene", "Płyny do dezynfekcji", 0, "opak", 2)
    )
}
