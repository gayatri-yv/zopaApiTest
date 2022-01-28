@All
Feature:Test Scenarios for creating and retrieving member details

    #################### 1. Create Member ID ###########################
  @test
  Scenario: Create a new member
    Given I have member endpoint and valid request
    When I post a request with correct details
    Then the response status code is 201
    And response body has valid details along with member id

  #################### 2. Get Member ID ###########################

  Scenario Outline: Retrieve member details with member id
    Given I have the member endpoint
    When I send a request with the <memberId>
    Then the response status code is 200
    And response body has valid details along with member id
    Examples:
      | memberId                             |
      | 95661dee-a9cc-426a-8374-96eded960224 |

    #################### 4. Negative Scenarios ###########################

  Scenario: Passing incorrect values in create member request body
    Given I have member endpoint and valid request
    When I post a request with incorrect details
    Then the response status code is 400
    And the response body returns errors and message

  Scenario: Passing null values in create member request body
    Given I have member endpoint and valid request
    When I post a request with null values
    Then the response status code is 404
    And the response body returns errors and message


  Scenario Outline: Passing invalid member id
    Given I have the member endpoint
    When I send a request with the <memberId>
    Then the response status code is 200
    And response body has valid details along with member id
    Examples:
      | memberId |
      | %        |
      | *        |
      | null     |