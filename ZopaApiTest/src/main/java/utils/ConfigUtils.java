package utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import commons.BaseClass;
import java.io.IOException;

public class ConfigUtils extends BaseClass {
    // called in hooks . sent urls and endpoints.
    public static void setEndpoint(String configFile) throws IOException, ParseException {
        JSONObject endpoints = JsonUtils.ReadJsonFile(configFile);
        JSONObject urlObject = (JSONObject) endpoints.get(BaseClass.environment);
        BaseClass.baseUrl = urlObject.get("baseUrl").toString();
        BaseClass.basePathMember = urlObject.get("basePathMember").toString();
        BaseClass.basePathQuote = urlObject.get("basePathQuote").toString();
    }
}
