import hu.domparse.f75cp6.DOMModifyF75CP6;
import hu.domparse.f75cp6.DOMQueryF75CP6;
import hu.domparse.f75cp6.DOMReadF75CP6;
import hu.domparse.f75cp6.DOMWriteF75CP6;

public class Main {
    public static void main(String[] args) {

        // Elementek módosítása
        DOMModifyF75CP6.ModifyElement("XMLTAskF75CP6\\DOMParseF75CP6\\src\\XMLF75CP6.xml");

        // Elementek lekérdezése
        DOMQueryF75CP6.QueryPrescribedDetails("XMLTAskF75CP6\\DOMParseF75CP6\\src\\XMLF75CP6.xml");

        // RootElement beolvasása
        DOMReadF75CP6.ReadXMLDocument("DOMParseF75CP6\\src\\XMLF75CP6.xml");

        // Elemek kiírása
        DOMWriteF75CP6.WriteElementsToFileAndConsole();

    }
}