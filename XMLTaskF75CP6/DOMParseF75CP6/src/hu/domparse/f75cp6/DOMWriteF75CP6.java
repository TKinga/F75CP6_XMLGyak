package hu.domparse.f75cp6;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class DOMWriteF75CP6 {

    public static void WriteElementsToFileAndConsole() {

        try {
            // Előkészítjük a dokumentumot
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Ez a dokumentumépítő példányok létrehozására szolgál
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Ez a dokumentum építésére szolgál
            Document doc = builder.newDocument();

            // Root Element létrehozása
            Element rootElement = doc.createElement("F75CP6_Cukraszda");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaF75CP6.xsd");
            doc.appendChild(rootElement);

            // Termékek létrehozása
            addTermekek(doc, rootElement, "1", "11", "01", "101", "Epres csokitorta",
                    "csokis piskóta, étcsokis-tejszínes krémmel, epervelővel", "12000");
            addTermekek(doc, rootElement, "2", "12", "02", "102", "Ishler", "linzer tészta erdeigyümölcsös dzsemmel",
                    "240");
            addTermekek(doc, rootElement, "3", "13", "03", "103", "Macaron",
                    "őrölt mandulából készült korongok sós karamellás krémmel töltve", "300");

            // Szállítás létrehozása
            addSzallitas(doc, rootElement, "901", "Házhoz szállítás", "3250",
                    "A csomag várhatóan 12:55 és 14:55 között érkezik meg.");
            addSzallitas(doc, rootElement, "902", "Személyes átvétel", "0",
                    "Nyitvatartási időben bármikor átveheti termékét.");
            addSzallitas(doc, rootElement, "903", "Házhoz szállítás", "2700",
                    "A csomag várhatóan 11:20 és 13:20 között érkezik meg.");

            // Megrendelő létrehozása
            addMegrendelo(doc, rootElement, "101", "901", "Kis Béla", "Edelény", "Uitz Béla utca", "28",
                    Arrays.asList("36403127492", "36805551232"), "beluka33@citromail.hu");

            addMegrendelo(doc, rootElement, "102", "902", "Afonyi Dóra", "Kakucs", "Arany János utca", "4",
                    Arrays.asList("36803978912", "36408869769"), "dora12@gmail.com");

            addMegrendelo(doc, rootElement, "103", "903", "Stoll Barbara", "Veresegyház", "Petőfi utca", "48",
                    Arrays.asList("36807958169"), "rebarbara18@gmail.com");

            // Cukrász létrehozása
            addCukrasz(doc, rootElement, "01", "11", "Réfi Réka", "2", "rekuci15@gmail.com");
            addCukrasz(doc, rootElement, "02", "12", "Túró Rudolf", "3", "turorudi11@gmail.com");
            addCukrasz(doc, rootElement, "03", "13", "Kerepesi Rebeka", "5", "rebekabeka36@gmail.com");

            // Cukrászda létrehozása

            addCukraszda(doc, rootElement, "11", "001", "Hajnali Álom", "8-16");
            addCukraszda(doc, rootElement, "12", "002", "Éjféli Kívánság", "8-20");
            addCukraszda(doc, rootElement, "13", "003", "Napsütéses Virágoskert", "10-22");

            // Tuladonos létrehozása
            addTulajdonos(doc, rootElement, "001", "Tompa János", "36803974892", "tmpjni56@gmail.com");
            addTulajdonos(doc, rootElement, "002", "Mészáros Mária", "36904258798", "marikameszaros55@freemail.hu");
            addTulajdonos(doc, rootElement, "003", "Várkonyi Erika", "36503459678", "varkonyierika16@gmail.com");

            // Csomag létrehozása
            addCsomag(doc, rootElement, "1", "901", "2023-07-07");
            addCsomag(doc, rootElement, "2", "902", "2023-08-02");
            addCsomag(doc, rootElement, "3", "903", "2023-08-11");

            // Dokumentum mentése
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");

            printDocument(doc);
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTermekek(Document doc, Element rootElement, String Term_ID, String Cukda_ID, String Cuk_ID,
            String Megr_ID, String fajtak, String leiras, String ar)

    {
        Element termekek = doc.createElement("termekek");
        termekek.setAttribute("Term_ID", Term_ID);
        termekek.setAttribute("Cukda_ID", Cukda_ID);
        termekek.setAttribute("Cuk_ID", Cuk_ID);
        termekek.setAttribute("Megr_ID", Megr_ID);
        rootElement.appendChild(termekek);

        Element fajtakElem = doc.createElement("fajtak");
        fajtakElem.appendChild(doc.createTextNode(fajtak));
        termekek.appendChild(fajtakElem);

        Element leirasElem = doc.createElement("leiras");
        leirasElem.appendChild(doc.createTextNode(leiras));
        termekek.appendChild(leirasElem);

        Element arElem = doc.createElement("ar");
        arElem.appendChild(doc.createTextNode(ar));
        termekek.appendChild(arElem);
    }

    private static void addCsomag(Document doc, Element rootElement, String Term_ID, String Szall_ID, String datum)

    {
        Element csomag = doc.createElement("csomag");
        csomag.setAttribute("Term_ID", Term_ID);
        csomag.setAttribute("Szall_ID", Szall_ID);
        rootElement.appendChild(csomag);

        Element datumElem = doc.createElement("datum");
        datumElem.appendChild(doc.createTextNode(datum));
        csomag.appendChild(datumElem);
    }

    private static void addSzallitas(Document doc, Element rootElement, String Szall_ID, String modja,
            String szallitasi_koltseg, String megjegyzes) {
        Element szallitas = doc.createElement("szallitas");
        szallitas.setAttribute("Szall_ID", Szall_ID);
        rootElement.appendChild(szallitas);

        Element modjaElem = doc.createElement("modja");
        modjaElem.appendChild(doc.createTextNode(modja));
        szallitas.appendChild(modjaElem);

        Element szallitasiKoltsegElem = doc.createElement("szallitasi_koltseg");
        szallitasiKoltsegElem.appendChild(doc.createTextNode(szallitasi_koltseg));
        szallitas.appendChild(szallitasiKoltsegElem);

        Element megjegyzesElem = doc.createElement("megjegyzes");
        megjegyzesElem.appendChild(doc.createTextNode(megjegyzes));
        szallitas.appendChild(megjegyzesElem);
    }

    private static void addMegrendelo(Document doc, Element rootElement, String Megr_ID, String Szall_ID, String nev,
            String telepules, String utca, String hazszam, List<String> telefonszam, String email)

    {
        Element megrendelo = doc.createElement("megrendelo");
        megrendelo.setAttribute("Megr_ID", Megr_ID);
        megrendelo.setAttribute("Szall_ID", Szall_ID);
        rootElement.appendChild(megrendelo);

        Element nevElem = doc.createElement("nev");
        nevElem.appendChild(doc.createTextNode(nev));
        megrendelo.appendChild(nevElem);

        Element cim = doc.createElement("cim");
        Element telepulesElem = doc.createElement("telepules");
        telepulesElem.appendChild(doc.createTextNode(telepules));
        cim.appendChild(telepulesElem);

        Element utcaElem = doc.createElement("utca");
        utcaElem.appendChild(doc.createTextNode(utca));
        cim.appendChild(utcaElem);

        Element hazszamElem = doc.createElement("hazszam");
        hazszamElem.appendChild(doc.createTextNode(hazszam));
        cim.appendChild(hazszamElem);

        megrendelo.appendChild(cim);

        for (String szam : telefonszam) {
            Element telefonszamElem = doc.createElement("telefonszam");
            telefonszamElem.appendChild(doc.createTextNode(szam));
            megrendelo.appendChild(telefonszamElem);
        }

        Element emailElem = doc.createElement("e-mail_cim");
        emailElem.appendChild(doc.createTextNode(email));
        megrendelo.appendChild(emailElem);
    }

    private static void addCukrasz(Document doc, Element rootElement, String Cuk_ID, String Cukda_ID, String nev,
            String minosites, String email) {

        Element cukrasz = doc.createElement("cukrasz");
        cukrasz.setAttribute("Cuk_ID", Cuk_ID);
        cukrasz.setAttribute("Cukda_ID", Cukda_ID);
        rootElement.appendChild(cukrasz);

        Element nevElem = doc.createElement("nev");
        nevElem.appendChild(doc.createTextNode(nev));
        cukrasz.appendChild(nevElem);

        Element minositestElem = doc.createElement("minosites");
        minositestElem.appendChild(doc.createTextNode(minosites));
        cukrasz.appendChild(minositestElem);

        Element emailElem = doc.createElement("e-mail_cim");
        emailElem.appendChild(doc.createTextNode(email));
        cukrasz.appendChild(emailElem);

    }

    private static void addCukraszda(Document doc, Element rootElement, String Cukda_ID, String Tul_ID, String nev,
            String nyitva) {

        Element cukraszda = doc.createElement("cukraszda");
        cukraszda.setAttribute("Cukda_ID", Cukda_ID);
        cukraszda.setAttribute("Tul_ID", Tul_ID);
        rootElement.appendChild(cukraszda);

        Element nevElem = doc.createElement("nev");
        nevElem.appendChild(doc.createTextNode(nev));
        cukraszda.appendChild(nevElem);

        Element nyitvaElem = doc.createElement("nyitva");
        nyitvaElem.appendChild(doc.createTextNode(nyitva));
        cukraszda.appendChild(nyitvaElem);

    }

    private static void addTulajdonos(Document doc, Element rootElement, String Tul_ID, String nev, String telefonszam,
            String email) {

        Element tulajdonos = doc.createElement("tulajdonos");
        tulajdonos.setAttribute("Tul_ID", Tul_ID);
        rootElement.appendChild(tulajdonos);

        Element nevElem = doc.createElement("nev");
        nevElem.appendChild(doc.createTextNode(nev));
        tulajdonos.appendChild(nevElem);

        Element telefonszamElem = doc.createElement("telefonszam");
        telefonszamElem.appendChild(doc.createTextNode(telefonszam));
        tulajdonos.appendChild(telefonszamElem);

        Element emailElem = doc.createElement("e-mail_cim");
        emailElem.appendChild(doc.createTextNode(email));
        tulajdonos.appendChild(emailElem);
    }

    private static void printDocument(Document doc) {

        try {
            // Fájlba írás
            File outputFile = new File("DOMParseF75CP6\\XMLF75CP6_3.xml");
            // Írás a konzolra
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile, false));
            // Kiírja az XML főgyökér elemét a konzolra és fájlba
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            // A gyökér elem attribútumainak kiírása
            StringJoiner rootAttributes = new StringJoiner(" ");
            // A gyökér elem attribútumainak lekérése
            NamedNodeMap rootAttributeMap = rootElement.getAttributes();

            for (int i = 0; i < rootAttributeMap.getLength(); i++) {
                Node attribute = rootAttributeMap.item(i);
                rootAttributes.add(attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
            }

            System.out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            System.out.print("<" + rootName + " " + rootAttributes.toString() + ">\n");
            writer.print("<" + rootName + " " + rootAttributes.toString() + ">\n");

            // A gyökér elem alatti elemek lekérése
            NodeList termekekList = doc.getElementsByTagName("termekek");
            NodeList csomagList = doc.getElementsByTagName("csomag");
            NodeList szallitasList = doc.getElementsByTagName("szallitas");
            NodeList megrendeloList = doc.getElementsByTagName("megrendelo");
            NodeList cukraszList = doc.getElementsByTagName("cukrasz");
            NodeList cukraszdaList = doc.getElementsByTagName("cukraszda");
            NodeList tulajdonosList = doc.getElementsByTagName("tulajdonos");

            // Kiírjuk az XML-t a konzolra megtartva az eredeti formázást
            printNodeList(termekekList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(csomagList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(szallitasList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(megrendeloList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(cukraszList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(cukraszdaList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(tulajdonosList, writer);
            System.out.println("");

            // XML gyökér lezárása
            System.out.println("</" + rootName + ">");
            writer.append("</" + rootName + ">");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // NodeList kiírása
    private static void printNodeList(NodeList nodeList, PrintWriter writer) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            printNode(node, 1, writer);
            System.out.println("");
            writer.println("");
        }
    }

    // Node kiírása
    private static void printNode(Node node, int indent, PrintWriter writer)

    {
        // Ha az elem típusa ELEMENT_NODE, akkor kiírjuk az elem nevét és attribútumait
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String nodeName = element.getTagName();
            StringJoiner attributes = new StringJoiner(" ");
            NamedNodeMap attributeMap = element.getAttributes();

            // Kiírjuk az elem nevét és attribútumait
            for (int i = 0; i < attributeMap.getLength(); i++) {
                Node attribute = attributeMap.item(i);
                attributes.add(attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
            }

            // Kiírjuk az elem nevét és attribútumait (tartalmát)
            System.out.print(getIndentString(indent));
            System.out.print("<" + nodeName + " " + attributes.toString() + ">");

            writer.print(getIndentString(indent));
            writer.print("<" + nodeName + " " + attributes.toString() + ">");

            NodeList children = element.getChildNodes();
            if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
                System.out.print(children.item(0).getNodeValue());
                writer.print(children.item(0).getNodeValue());
            }

            else {
                System.out.println();
                writer.println();
                for (int i = 0; i < children.getLength(); i++) {
                    printNode(children.item(i), indent + 1, writer);
                }
                System.out.print(getIndentString(indent));
                writer.print(getIndentString(indent));
            }
            System.out.println("</" + nodeName + ">");
            writer.println("</" + nodeName + ">");
        }
    }

    // Behúzások hozzáadása
    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}