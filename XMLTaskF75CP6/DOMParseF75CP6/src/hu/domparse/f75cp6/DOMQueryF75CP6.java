
package hu.domparse.f75cp6;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMQueryF75CP6 {

    public static void QueryPrescribedDetails(String filePath)

    {
        Document doc = null;

        try {

            // Fájl beolvasása
            filePath = ("DOMParseF75CP6\\XMLF75CP6.xml");
            File inputFile = new File(filePath);

            // Ez létrehoz egy singleton objektumot, amely lehetővé teszi a dokumentumok
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Ez a dokumentumépítő példányok létrehozására szolgál
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // A dokumentum beolvasása
            doc = dBuilder.parse(inputFile);

            // A dokumentum normalizálása
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1. lekérdezés
        System.out.println();
        System.out.println("\n1. Lekérdezés:");
        System.out.println();
        // Kiírja, hogy "Összes a termék:"
        System.out.println("Összes termék:");

        // Lekéri az összes "termékek" elemet az XML-ből
        NodeList termekekList = doc.getElementsByTagName("termekek");

        // Végigmegy az összes termék elemen
        for (int i = 0; i < termekekList.getLength(); i++)

        {
            Node node = termekekList.item(i);

            // Lellenőrzi, hogy az elemem az tényleg elem típusú-e tehát nem szöveg vagy
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element termekek = (Element) node;

                // Kiírja a termékek ID-ját és fajtáit
                System.out.println("Termekék ID: " + termekek.getAttribute("Term_ID"));
                System.out.println("Fajták: " + termekek.getElementsByTagName("fajtak").item(0).getTextContent());
            }
        }

        // 2. lekérdezés
        System.out.println();
        System.out.println("\n2. Lekérdezés:");
        System.out.println();
        System.out.println("Kiírja azoknak a Szállításoknak az ID-ját, ahol a szállítási költség nagyobb, mint 3000");

        // Lekéri az összes "szállítás" elemet az XML-ből
        NodeList szallitasList = doc.getElementsByTagName("szallitas");

        // Végigmegy az összes szállítás elemen
        for (int i = 0; i < szallitasList.getLength(); i++) {

            Element szallitasElement = (Element) szallitasList.item(i);
            int szallitasi_koltseg = Integer
                    .parseInt(szallitasElement.getElementsByTagName("szallitasi_koltseg").item(0).getTextContent());

            // Ha a szállítási költség több, mint 3000, akkor kiírja a szállítás ID-ját
            if (szallitasi_koltseg > 3000) {
                String Szall_ID = szallitasElement.getAttribute("Szall_ID");
                System.out.println("Szall_ID:" + Szall_ID);
            }
        }

        // 3. lekérdezés
        System.out.println();
        NodeList szallitasRendelesList = doc.getElementsByTagName("szallitas");
        System.out.println("\n3. Lekérdezés:");
        System.out.println();
        System.out.println("Kiírja a megrendelőknek az adatait, ahol házhoz szállításal kérték a terméket.");

        for (int i = 0; i < szallitasRendelesList.getLength(); i++) {
            Element szallitasElement = (Element) szallitasRendelesList.item(i);
            String szallitasModja = szallitasElement.getElementsByTagName("modja").item(0).getTextContent();
            String szallitasID = szallitasElement.getAttribute("Szall_ID");

            // Csak a "Házhoz szállítás" módú szállításokra vagyunk kíváncsiak
            if ("Házhoz szállítás".equals(szallitasModja)) {
                // Megrendelő elemek lekérdezése a megfelelő Szall_ID alapján
                NodeList megrendeloList = doc.getElementsByTagName("megrendelo");
                for (int j = 0; j < megrendeloList.getLength(); j++) {
                    Element megrendeloElement = (Element) megrendeloList.item(j);
                    String megrendeloSzállID = megrendeloElement.getAttribute("Szall_ID");

                    // Ha a Szall_ID megegyezik és a Szállítási mód "Házhoz szállítás", akkor kiírja
                    // a megrendelő adatait
                    if (szallitasID.equals(megrendeloSzállID)) {
                        System.out.println("Megrendelő adatai Szall_ID=" + szallitasID + ":");
                        System.out.println(
                                "Név: " + megrendeloElement.getElementsByTagName("nev").item(0).getTextContent());

                        // Cím elem lekérdezése
                        Element cimElement = (Element) megrendeloElement.getElementsByTagName("cim").item(0);
                        if (cimElement != null) {
                            System.out.println("Cím: " +
                                    cimElement.getElementsByTagName("telepules").item(0).getTextContent() + ", " +
                                    cimElement.getElementsByTagName("utca").item(0).getTextContent() + " " +
                                    cimElement.getElementsByTagName("hazszam").item(0).getTextContent());
                        }

                        // Telefonszámok lekérdezése
                        NodeList telefonList = megrendeloElement.getElementsByTagName("telefonszam");
                        for (int k = 0; k < telefonList.getLength(); k++) {
                            System.out.println("Telefonszám: " + telefonList.item(k).getTextContent());
                        }

                        System.out.println("E-mail cím: " +
                                megrendeloElement.getElementsByTagName("e-mail_cim").item(0).getTextContent());

                    }
                }
            }
        }

        // 4. lekérdezés
        System.out.println();
        NodeList cukraszList = doc.getElementsByTagName("cukrasz");
        System.out.println("\n4. Lekérdezés:");
        System.out.println("\nCukrászok nevei minősítéssel együtt:");

        for (int i = 0; i < cukraszList.getLength(); i++) {
            Element cukraszElement = (Element) cukraszList.item(i);
            String cukraszNev = cukraszElement.getElementsByTagName("nev").item(0).getTextContent();
            String minosites = cukraszElement.getElementsByTagName("minosites").item(0).getTextContent();

            System.out.println("Cukrász: " + cukraszNev + ", Minősítés: " + minosites);
        }

        System.out.println();
        NodeList cukraszdaList = doc.getElementsByTagName("cukraszda");
        NodeList tulajdonosList = doc.getElementsByTagName("tulajdonos");

        System.out.println("\nCukrászdák nevei tulajdonosok neveivel együtt:");

        for (int i = 0; i < cukraszdaList.getLength(); i++) {
            Element cukraszdaElement = (Element) cukraszdaList.item(i);
            String cukraszdaNev = cukraszdaElement.getElementsByTagName("nev").item(0).getTextContent();
            String tulajdonosID = cukraszdaElement.getAttribute("Tul_ID");

            // Tulajdonos nevének lekérdezése a megfelelő Tul_ID alapján
            for (int j = 0; j < tulajdonosList.getLength(); j++) {
                Element tulajdonosElement = (Element) tulajdonosList.item(j);
                String tulajdonosIDinXML = tulajdonosElement.getAttribute("Tul_ID");

                // Ha a Tul_ID megegyezik, akkor kiírja a cukrászda nevét és tulajdonos nevét
                if (tulajdonosID.equals(tulajdonosIDinXML)) {
                    String tulajdonosNev = tulajdonosElement.getElementsByTagName("nev").item(0).getTextContent();
                    System.out.println("Cukrászda: " + cukraszdaNev + ", Tulajdonos: " + tulajdonosNev);
                    break; // Kilép a belső ciklusból, mert megtaláltuk a megfelelő tulajdonost
                }
            }
        }

        // 5. lekérdezés
        System.out.println();

        NodeList csomagList = doc.getElementsByTagName("csomag");
        System.out.println("\n5. Lekérdezés:");
        System.out.println("\nCsomagok szállításának adatai:");

        for (int i = 0; i < csomagList.getLength(); i++) {
            Element csomagElement = (Element) csomagList.item(i);
            String termID = csomagElement.getAttribute("Term_ID");
            String szallID = csomagElement.getAttribute("Szall_ID");

            // Dátum kiírása
            String datum = csomagElement.getElementsByTagName("datum").item(0).getTextContent();
            System.out.println("Csomag Szall_ID=" + szallID + ", Term_ID=" + termID + ":");
            System.out.println("datum: " + datum);

            // Szállítás adatainak kiírása
            NodeList szallitasCsomagList = doc.getElementsByTagName("szallitas");
            for (int j = 0; j < szallitasCsomagList.getLength(); j++) {
                Element szallitasElement = (Element) szallitasCsomagList.item(j);
                String szallIDinXML = szallitasElement.getAttribute("Szall_ID");

                // Ha a Szall_ID megegyezik, akkor kiírja a szállítás adatait
                if (szallID.equals(szallIDinXML)) {
                    String szallitasModja = szallitasElement.getElementsByTagName("modja").item(0).getTextContent();
                    System.out.println("Szállítási mód: " + szallitasModja);

                    // Termék fajtájának kiírása
                    NodeList termekList = doc.getElementsByTagName("termekek");
                    for (int k = 0; k < termekList.getLength(); k++) {
                        Element termekElement = (Element) termekList.item(k);
                        String termIDinXML = termekElement.getAttribute("Term_ID");

                        // Ha a Term_ID megegyezik, akkor kiírja a termék fajtáját
                        if (termID.equals(termIDinXML)) {
                            String termekFajta = termekElement.getElementsByTagName("fajtak").item(0).getTextContent();
                            System.out.println("Termék fajta: " + termekFajta);
                            break; // Kilép a belső ciklusból, mert megtaláltuk a megfelelő terméket
                        }
                    }
                    break; // Kilép a középső ciklusból, mert megtaláltuk a megfelelő szállítást
                }
            }
            System.out.println();
        }

    }
}