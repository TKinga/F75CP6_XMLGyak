package hu.domparse.f75cp6;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.StringJoiner;

public class DOMReadF75CP6 {

    public static void ReadXMLDocument(String filePath) {
        try {

            // Fájl beolvasása
            filePath = ("DOMParseF75CP6\\XMLF75CP6.xml");
            File XMLFile = new File(filePath);

            // A DocumentumBuilderFactoryból megkapjuk a DocumentBuildert
            // A DocumentumBuilder tartalmazza az API-t a DOM dokumentumok példányok
            // XML-dokumentumból való beszerzéséhez
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // DocumentBuilder a példányok létrehozására szolgál
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Ez a dokumentum építésére szolgál
            Document doc = dBuilder.parse(XMLFile);

            // A dokumentum normalizálását segíti a helyes eredmény elérése érdekében
            doc.getDocumentElement().normalize();
            printDocument(doc);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    private static void printDocument(Document doc) {
        try {

            // Mentés fájlba
            File outputFile = new File("DOMParseF75CP6\\XMLF75CP6_2.xml");
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile, false));

            // Kiírjuk az XML főgyökér elemét a konzolra és fájlba
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            StringJoiner rootAttributes = new StringJoiner(" ");
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

            // Zárjuk le az XML gyökér elemét
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
            } else {
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
            // A szóközök száma, minden iteráció után, amit hozzáfűz a Stringbuilderhez
            // amivel indentálunk
            sb.append("  ");
        }
        return sb.toString();
    }
}