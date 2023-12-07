package f75cp6;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class JSONWrite {

    public static void main(String[] args) {

        JSONParser JSONparser = new JSONParser();

        try (Reader reader = new FileReader("JSONParseF75CP6/orarendF75CP6.json")) {

            JSONObject inputJSONObject = (JSONObject) JSONparser.parse(reader);

            JSONArray oraArray = (JSONArray) inputJSONObject.get("orarend").get("ora");

            JSONObject JSONObject = new JSONObject();
            JSONArray newOraArray = new JSONArray();

            oraArray.add(createOra(new String[] { "Pénzügytan", "Dr. Bozsik Sándor", "BGI", "hétfő", "10:00", "12:00",
                    "XXXVII A3 és A5 között" }));
            oraArray.add(createOra(new String[] { "WEB technológiák", "Dr. Agárdi Anita", "BGI", "hétfő", "14:00",
                    "16:00", "XXX A1 III. em. 305" }));
            oraArray.add(createOra(new String[] { "WEB technológiák", "Dr. Agárdi Anita", "BGI", "hétfő", "16:00",
                    "18:00", "XXX A1 III. em. 305" }));
            oraArray.add(createOra(new String[] { "Számvitel", "Dr. Pál Tibor", "BGI", "kedd", "8:00", "10:00",
                    "VIII A1 I. em. 116" }));
            oraArray.add(createOra(new String[] { "Mesterséges intelligencia", "Kunné Dr. Tamás Judit", "BGI", "kedd",
                    "10:00", "12:00", "XXXII A1 magasfsz" }));
            oraArray.add(createOra(new String[] { "Adatkezelés XML-ben", "Dr. Bednarik László", "BGI", "kedd", "12:00",
                    "14:00", "XXXII.ea" }));
            oraArray.add(createOra(new String[] { "Pénzügytan", "Dr. Süveges Gábor", "BGI", "szerda", "8:00", "10:00",
                    "A1/204 A1 II. em. 204" }));
            oraArray.add(createOra(new String[] { "Adatkezelés XML-ben", "Dr. Bednarik László", "BGI", "szerda",
                    "10:00", "12:00", "Inf/101 fsz." }));
            oraArray.add(createOra(new String[] { "Számvitel", "Várkonyiné Dr. Juhász Mária", "BGI", "szerda", "12:00",
                    "14:00", "A3/312 III.em. XII.ea" }));
            oraArray.add(createOra(new String[] { "Vállalati információs rendszerek fejlesztése", "Dr. Sasvári Péter",
                    "BGI", "szerda", "14:00", "16:00", "Inf/101 fsz." }));

            for (int i = 0; i < oraArray.size(); i++) {
                JSONObject localObject = (JSONObject) oraArray.get(i);
                System.out.println("\nÓra");
                System.out.println("  Targy: " + localObject.get("targy"));
                System.out.println("  Oktató: " + localObject.get("oktato"));
                System.out.println("  Szak: " + localObject.get("szak"));
                System.out.println("  Időpont: ");

                JSONObject time = (JSONObject) localObject.get("idopont");
                System.out.println("    Nap: " + time.get("nap"));
                System.out.println("    Tól: " + time.get("tol"));
                System.out.println("    Ig: " + time.get("ig"));

                System.out.println("  Helyszín: " + localObject.get("helyszin"));

            }

            JSONObject oraObject = new JSONObject();
            oraObject.put("ora", newOraArray);
            JSONObject.put("F75CP6_orarend", oraObject);

            FileWriter file = new FileWriter("JSONParseF75CP6/orarendF75CP6.json");
            file.write(JSONObject.toString());
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static JSONObject createOra(String[] data) {
        JSONObject localObject = new JSONObject();

        localObject.put("targy", data[0]);
        localObject.put("oktato", data[1]);
        localObject.put("szak", data[2]);

        // Parse idopont string into separate fields
        String[] idopontData = data[3].split("[\":,]");
        JSONObject timeObject = new JSONObject();
        timeObject.put("nap", idopontData[3]);
        timeObject.put("tol", idopontData[7]);
        timeObject.put("ig", idopontData[11]);
        localObject.put("idopont", timeObject);

        localObject.put("helyszin", data[4]);

        return localObject;
    }
}
