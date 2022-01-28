package StepDefs;


import io.cucumber.java.Before;
import io.restassured.RestAssured;
import commons.BaseClass;
import utils.ConfigUtils;

import java.io.IOException;

public class hooks {

    private static boolean propertiesFlag = false;

    String configFile = BaseClass.absPath + BaseClass.separator + "src" + BaseClass.separator + "test" + BaseClass.separator + "resources" + BaseClass.separator + "config.json";

    // this class is called first before running any named scenarios.
    @Before
    public void setup() throws IOException, org.json.simple.parser.ParseException {
        if(!propertiesFlag) {
            // sets environment to run the tests in.
            BaseClass.environment = "Staging";
            // sets configuration file to pick urls from.
            ConfigUtils.setEndpoint(configFile);
            // sets base url to the value provided in config file.
            RestAssured.baseURI = BaseClass.baseUrl;
            System.out.println("hooks ran successfully");
            propertiesFlag = true;
        }
    }
}
