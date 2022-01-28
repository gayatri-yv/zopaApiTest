package StepDefs;

import io.cucumber.java.en.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import commons.BaseClass;
import commons.RestAssuredCommon;
import utils.JsonUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyStepdefs {

    Response response;

    Logger logger = LoggerFactory.getLogger(MyStepdefs.class);

    String requestUrl, id;
    public static JSONObject createMemberRequestBody;
    public static JSONObject getQuoteRequestBody;

    @Given("I have the member endpoint")
    public void i_Have_Member_Endpoint() {
        // calls the method from common class for restassured methods (get/post).
        //This method restassured base uri/url and base path.
        requestUrl = RestAssuredCommon.getMemberEndpoint();
        logger.info("Request url is " + requestUrl);
    }

    @Then("the response status code is {int}")
    public void the_Response_Status_Code_Is(int statusCode) {
        //checks the response status code is either 200 or 201 or 400 or 404
        Assert.assertEquals("Response status code should be 200 even if quote decision is false", statusCode, response.getStatusCode());
    }

    @And("response body has valid details along with member id")
    public void response_Body_Has_Valid_Details_With_MemberId() {
        //checks that the member id is returned in the response and that the value is not null.
        Assert.assertNotNull("member id ", response.jsonPath().getString("memberId"));
    }

    @When("^I send a request with the (.*)$")
    public void i_Send_Request_With_MemberId(String memberId) {
        //sets id value to the member id value provided in the data table of feature file.
        //calls the get methos from rest assured common class,with member id as a parameter.
        //member id is passed as query parameter.
        response = RestAssuredCommon.getRequest(memberId);
        logger.info("response from member endpoint " + response.jsonPath().get().toString());
    }

    @Given("I have member endpoint and valid request")
    public void iHaveMemberEndpointAndValidRequest() throws IOException, ParseException {
        //calls the common rest assured method to get base url & base path.
        requestUrl = RestAssuredCommon.getMemberEndpoint();
        logger.info("Request url is " + requestUrl);
        // Sets the absolute path to the input json file.
        String requestJson = BaseClass.absPath + BaseClass.separator + "src" + BaseClass.separator + "test" + BaseClass.separator + "resources" + BaseClass.separator + "createMember.json";
        //calls the Json utilities common class to read json file.
        createMemberRequestBody = JsonUtils.ReadJsonFile(requestJson);
    }

    @When("I post a request with correct details")
    public void i_Post_Request() {
        // The contents of json file are converted into Json Object. It is then passed as the payload /request body and post it.
        response = RestAssuredCommon.postRequest(createMemberRequestBody);
        // checks that the response is not null.
        Assert.assertNotNull(response);
        // prints full response in readable format.
        logger.info("response from post member endpoint" + response.body().prettyPrint());
        //member id is displayed.
        logger.info("member id is: " + response.body().path("memberId"));
    }

    @Given("^I have quote endpoint and (.*)$")
    public void iHaveQuoteEndpointAndMemberId(String memberId) {
        requestUrl = RestAssuredCommon.getQuoteEndpoint();
        id = memberId;
        logger.info("Quote endpoint is :" + requestUrl + " \nmember id is: " + memberId);
    }

    @When("I post a request with valid details")
    public void iPostARequestWithValidDetails() throws IOException, ParseException {
        // Below code gets full path for input json file and uses common Json Utilities method to read the contents of the file.
        String requestJson = BaseClass.absPath + BaseClass.separator + "src" + BaseClass.separator + "test" + BaseClass.separator + "resources" + BaseClass.separator + "quote.json";
        getQuoteRequestBody = JsonUtils.ReadJsonFile(requestJson);
        // the Json file is converted to json object and passed as parameter for post request along with member id.
        //id is global variable, whose value is set in previous step, via datatable in cucumber feature file.
        response = RestAssuredCommon.postQuoteRequest(getQuoteRequestBody, id);
        logger.info("response from quote endpoint " + response.jsonPath().get().toString());
    }

    @When("^I post a request with details (\\d+) and (\\d+) and (\\d+)$")
    public void iPostARequestWithDetailsCurrentSalaryAndAmountToBorrowAndTermLength(Integer currentSalary, Integer amountToBorrow, Integer termLength) {
        // the below three lines of code, replaces the values of the named fields.
        //Default values for these fields come from the input json and those values are replaced by values in feature file data table.
        getQuoteRequestBody.put("currentSalary", currentSalary);
        getQuoteRequestBody.put("amountToBorrow", amountToBorrow);
        getQuoteRequestBody.put("termLength", termLength);
        // json object request is sent with replaced values, into post request, along with member id set in previous step.
        response = RestAssuredCommon.postQuoteRequest(getQuoteRequestBody, id);
        logger.info("response from quote endpoint " + response.jsonPath().get().toString());
    }

    @And("the response body has all the fields with expected values")
    public void theResponseBodyHasAllTheFieldsWithExpectedValues() {
        // the below steps uses Jsonpath library to extract keys & values from the json response body.
        JsonPath jsonPath = response.jsonPath();
        String quoteId = jsonPath.getString("quoteId");
        boolean quoteOffered = jsonPath.getBoolean("quoteOffered");
        int amountToBorrow = jsonPath.getInt("amountToBorrow");
        int termLength = jsonPath.getInt("termLength");
        float interestRate = jsonPath.getFloat("interestRate");
        String memberId = jsonPath.getString("memberId");
        String membersFullName = jsonPath.getString("membersFullName");
        // Check to ensure quote id is returned in the response and the value is not null.
        Assert.assertNotNull("Quote Id generated successfully.", quoteId);
        // checks that the quote decision is returned true , as the input has valid fields.
        Assert.assertTrue("Loan can be offered", quoteOffered);
        // compares that the value returned in the response matches with the value in request.
        Assert.assertEquals(amountToBorrow, getQuoteRequestBody.get("amountToBorrow"));
        Assert.assertEquals(termLength, getQuoteRequestBody.get("termLength"));
        //checks that interest rate returns a value value other than 0
        Assert.assertNotEquals(0.0, interestRate);
        //checks that member id returned in response matches with member id passed in the request params.
        Assert.assertEquals(memberId, id);
        // checks that members full name value matches with the values sent in request for firstname + last name using string concatenation.
        Assert.assertEquals(membersFullName, createMemberRequestBody.get("firstName").toString() + " " + createMemberRequestBody.get("lastName").toString());
    }

    @And("the response body returns appropriate error message")
    public void theResponseBodyReturnsAppropriateErrorMessage() {
        // extract error message from response body.
        String errorMessage = response.jsonPath().get("message");
        // checks that the error message is returned for the same member id as passed in the request.
        Assert.assertEquals("No Member exists for memberId=" + id, errorMessage);
        logger.info("Member id should be a valid registered number." + id);
    }

    @And("the response body returns false decision for quote")
    public void theResponseBodyReturnsFalseDecisionForQuote() {
        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertNotNull(message);
        logger.info("Incorrect input for amount.Please enter valid loan amount within provided range");
        Assert.assertFalse("Incorrect input for amount.Please enter valid loan amount within provided range", jsonPath.getBoolean("quoteOffered"));
    }

    @When("I post a request with incorrect details")
    public void iPostARequestWithIncorrectDetails() {
        //passing invalid values in the input json request
        createMemberRequestBody.put("firstName", 1);
        createMemberRequestBody.put("lastName", "*");
        createMemberRequestBody.put("emailAddress", "a");
        createMemberRequestBody.put("address.postCode", "abc");
        createMemberRequestBody.put("jobTitle", true);

        response = RestAssuredCommon.postRequest(createMemberRequestBody);
    }

    @And("the response body returns errors and message")
    public void theResponseBodyReturnsErrorsAndMessage() {
        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        LinkedHashMap<String, String> errors = response.jsonPath().get("errors");
//        for (Map.Entry<String, String> e : errors.entrySet()) {
//            logger.info("Please fix the below errors and resend the request with valid values for the below fields" + (e.getKey() + " " + e.getValue()));
//        }
        Assert.assertNotNull(message);
        Assert.assertNotNull(errors);
        logger.info("Incorrect input for amount.Please enter valid loan amount within provided range :" + message);
        logger.info("Please check the values in the request field are correct :" + errors);

    }

    @When("I post a request with null values")
    public void iPostARequestWithNullValues() {
        //passing invalid values in the input json request
        createMemberRequestBody.put("firstName", null);
        createMemberRequestBody.put("lastName", null);
        createMemberRequestBody.put("emailAddress", null);
        createMemberRequestBody.put("address.postCode", null);
        createMemberRequestBody.put("jobTitle", null);

        response = RestAssuredCommon.postRequest(createMemberRequestBody);
    }
}
