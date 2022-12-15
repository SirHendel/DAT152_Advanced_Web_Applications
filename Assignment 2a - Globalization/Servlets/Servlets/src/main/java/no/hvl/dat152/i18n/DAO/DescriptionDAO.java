package no.hvl.dat152.i18n.DAO;

import no.hvl.dat152.i18n.model.Description;
import no.hvl.dat152.i18n.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DescriptionDAO {
    public static List<Description> getDescriptionsByProductId(Long product_id) {
        List<Description> descriptions = new ArrayList<>();
        switch (product_id.intValue()) {
            case 1:
                Product product1 = new Product(1L, "Razer Gaming Keyboard", 56.23, "Razer-keyboard.jpeg");
                Description desc1 = new Description(1L, "cs_CZ", "Perfektní klávesnice s krásným barevným RGB podsvícením. První volba pro opravdové hráče.", product1);
                Description desc2 = new Description(2L, "en_US", "Perfect keyboard with nice colorful RGB lights. Number one for true gamers.", product1);
                Description desc3 = new Description(3L, "de_DE", "Perfekte Tastatur mit schönen bunten RGB-Lichtern. Nummer eins für echte Gamer.", product1);
                Description desc4 = new Description(4L, "es_ES", "Teclado perfecto con unas coloridas luces RGB. Número uno para los verdaderos jugadores.", product1);
                descriptions.add(desc1);
                descriptions.add(desc2);
                descriptions.add(desc3);
                descriptions.add(desc4);
                break;
            case 2:
                Product product2 = new Product(2L, "HyperX Gaming Keyboard", 61.15, "HyperX-keyboard.jpeg");
                Description desc5 = new Description(5L, "cs_CZ", "Klávesnice s rychlou odezvou a RGB podsvícením. Je vhodná pro hráče všechny hráče.", product2);
                Description desc6 = new Description(6L, "en_US", "Keyboard with quick respond and RGB lights. It's suitable for all players.", product2);
                Description desc7 = new Description(7L, "de_DE", "Tastatur mit schneller Reaktion und RGB-Beleuchtung. Für alle Spieler geeignet.", product2);
                Description desc8 = new Description(8L, "es_ES", "Teclado con rápida respuesta y luces RGB. Adecuado para todos los jugadores.", product2);
                descriptions.add(desc5);
                descriptions.add(desc6);
                descriptions.add(desc7);
                descriptions.add(desc8);
                break;
        }

        return descriptions;
    }
}
