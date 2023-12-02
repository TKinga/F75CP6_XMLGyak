package hu.domparse.f75cp6;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.StringWriter;

import org.w3c.dom.*;

public class DOMModifyF75CP6 {

    public static void ModifyElement(String filePath) {
        // Fájl beolvasása

        try {
            filePath = ("DOMParseF75CP6\\XMLF75CP6.xml");
            File inputFile = new File(filePath);
            // Ez létrehoz egy singleton objektumot, amely lehetővé teszi a dokumentumok
            // építését
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // Ez a dokumentumépítő példányok létrehozására szolgál
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Ez a dokumentum építésére szolgál
            Document doc = dBuilder.parse(inputFile);

            // A dokumentum normalizálását segíti a helyes eredmény elérése érdekében
            doc.getDocumentElement().normalize();
            ModifyPrescribedElements(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ModifyPrescribedElements(Document doc) throws TransformerException {

        // Root Element lekérése
        NodeList nList = doc.getElementsByTagName("F75CP6_Cukraszda");
        Element element = (Element) nList.item(0);

        // Megváltoztatom az Termékekben az első elem nevét
        NodeList termekekList = element.getElementsByTagName("termekek");
        Element termekek = (Element) termekekList.item(0);
        termekek.getElementsByTagName("fajtak").item(0).setTextContent("Gyümölcsös csokitorta");

        // Például az első Szállítás költségét változtatja meg
        NodeList szallitasList = element.getElementsByTagName("szallitas");
        Element szallitas = (Element) szallitasList.item(0);
        szallitas.getElementsByTagName("szallitasi_koltseg").item(0).setTextContent("6350");

        // Az első megrendelo Települését változtatja meg
        NodeList megrendeloList = element.getElementsByTagName("megrendelo");
        Element megrendelo = (Element) megrendeloList.item(1);
        megrendelo.getElementsByTagName("telepules").item(0).setTextContent("Miskolc");

        // Az első cukrasz minősítését változtatja meg
        NodeList cukraszList = element.getElementsByTagName("cukrasz");
        Element cukrasz = (Element) cukraszList.item(1);
        cukrasz.getElementsByTagName("minosites").item(0).setTextContent("4");

        // Az első Cukrászda nevét változtatja meg
        NodeList cukraszdaList = element.getElementsByTagName("cukraszda");
        Element cukraszda = (Element) cukraszdaList.item(0);
        cukraszda.getElementsByTagName("nev").item(0).setTextContent("Hajnali Harmat Cukrászda");

        // Az első tulajdonos nevét változtatja meg
        NodeList tulajdonosList = element.getElementsByTagName("tulajdonos");
        Element tulajdonos = (Element) tulajdonosList.item(0);
        tulajdonos.getElementsByTagName("nev").item(0).setTextContent("Tommy Hilfiger");

        // Az első csomag dátumát változtatja meg
        NodeList csomagList = element.getElementsByTagName("csomag");
        Element csomag = (Element) csomagList.item(0);
        csomag.getElementsByTagName("datum").item(0).setTextContent("2022-01-01");

        printDocument(doc);
    }

    private static void printDocument(Document doc) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String output = writer.getBuffer().toString();
        System.out.println(output);
    }
}