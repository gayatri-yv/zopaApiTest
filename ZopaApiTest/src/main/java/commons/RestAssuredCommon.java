package commons;

import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class RestAssuredCommon {
    // common methods to use rest assured configuration ,format and syntax.

    public static String getMemberEndpoint() {
        // setting member endpoint and returns it.
       return BaseClass.baseUrl + BaseClass.basePathMember;
    }

    public static String getQuoteEndpoint() {
        // setting quote endpoint and returns it.
        return BaseClass.baseUrl + BaseClass.basePathQuote;
    }

    // this method calls the endpoint using GET method,with headers (using input parameter member id) and returns response.
    public static Response getRequest(String memberId) {
        return given()
               .queryParam("memberId",memberId)
               //.header("X-Fields", "mask")
               .header("Accept","application/json")
               .log().all()
               .when().get(BaseClass.basePathMember)
               .then().extract().response();
    }

    // posts request ( with json body from input file provided as parameter) and headers . Returns reponse.
    public static Response postRequest(JSONObject createMemberRequestBody) {
        return given()
                // .header("X-Fields", "mask") if this is enabled, response body will be blank
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .log().all()
                .when().body(createMemberRequestBody)
                .post(BaseClass.basePathMember)
                .peek()
                .then().extract().response();
    }

    // post request with json and member id as input parameter passed as query string parameter.
    public static Response postQuoteRequest(JSONObject getQuoteRequestBody,String memberId) {
        return given()
                .queryParam("memberId",memberId)
                //.header("X-Fields", "mask")
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .log().all()
                .when().body(getQuoteRequestBody)
                .post(BaseClass.basePathQuote)
                .then().extract().response();
    }
}
