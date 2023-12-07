package f75cp6;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONRead {

    public static void main(String[] args) {

        JSONParser JSONparser = new JSONParser();

        try (Reader reader = new FileReader("JSONParseF75CP6/orarendF75CP6.json")) {

            JSONObject JSONObject = (JSONObject) JSONparser.parse(reader);

            JSONObject = (JSONObject) JSONObject.get("F75CP6_orarend");

            JSONArray JSONarr = (JSONArray) JSONObject.get("ora");

            for (int i = 0; i < JSONarr.size(); i++) {
                JSONObject localObject = (JSONObject) JSONarr.get(i);
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

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
