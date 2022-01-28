package utils;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtils {
    public JsonUtils() {

    }
// common utilities' method to read contents of json file.
    public static JSONObject ReadJsonFile(String fileAbsPath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(fileAbsPath));
        return (JSONObject)object;
    }
}
