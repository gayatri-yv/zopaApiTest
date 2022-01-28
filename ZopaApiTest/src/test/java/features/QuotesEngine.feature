@All
Feature:Test Scenarios for Quotes Engine

          #################### 1. Quote Engine - Sending valid details ###########################

  Scenario Outline: Request quote
    Given I have quote endpoint and <memberId>
    When I post a request with valid details
    Then the response status code is 200
    And the response body has all the fields with expected values
    Examples:
      | memberId                             |
      | 95661dee-a9cc-426a-8374-96eded960224 |

 #################### Quote decision = true ###########################

  Scenario Outline: Request quote
    Given I have quote endpoint and <memberId>
    When I post a request with details <currentSalary> and <amountToBorrow> and <termLength>
    Then the response status code is 200
    And the response body has all the fields with expected values
    Examples:
      | memberId                             | currentSalary | amountToBorrow | termLength |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 1000           | 12         |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 1001           | 12         |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 25000          | 120        |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 24999          | 120        |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 12500          | 120        |

     ## validations for current salary

  Scenario Outline: Request quote for loan amount too less or too high
    Given I have quote endpoint and <memberId>
    When I post a request with details <currentSalary> and <amountToBorrow> and <termLength>
    Then the response status code is 200
    And the response body has all the fields with expected values
    Examples:
      | memberId                             | currentSalary | amountToBorrow | termLength |
      | 95661dee-a9cc-426a-8374-96eded960224 | 0             | 1500           | 12         |
      | 95661dee-a9cc-426a-8374-96eded960224 | 1             | 1500           | 12         |
      | 95661dee-a9cc-426a-8374-96eded960224 | 100           | 1500           | 12         |
      | 95661dee-a9cc-426a-8374-96eded960224 | 999           | 1500           | 12         |
      | 95661dee-a9cc-426a-8374-96eded960224 | 99999999      | 1500           | 12         |

              #################### Quote decision = false ###########################
  ## validations for loan amount
  Scenario Outline: Request quote for loan amount too less or too high
    Given I have quote endpoint and <memberId>
    When I post a request with details <currentSalary> and <amountToBorrow> and <termLength>
    Then the response status code is 200
    And the response body returns false decision for quote
    Examples:
      | memberId                             | currentSalary | amountToBorrow | termLength |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 999            | 12         |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 0              | 12         |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 25001          | 120        |


    ## validations for term length
  Scenario Outline: Request quote for loan amount too less or too high
    Given I have quote endpoint and <memberId>
    When I post a request with details <currentSalary> and <amountToBorrow> and <termLength>
    Then the response status code is 400
    And the response body returns false decision for quote
    Examples:
      | memberId                             | currentSalary | amountToBorrow | termLength |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 999            | 12         |

          #################### Negative scenarios ###########################

  Scenario Outline: Non-existing member should receive 404 error
    Given I have quote endpoint and <memberId>
    When I post a request with valid details
    Then the response status code is 404
    And the response body returns appropriate error message
    Examples:
      | memberId                             |
      | 00000dee-a9cc-426a-8374-96eded960224 |

     #################### Non - functional testing ( Security) ###########################
  # Passing special character and invalid values in the payload and in headers would cover for some security testing aspects

  Scenario Outline: Non-existing member should receive 404 error
    Given I have quote endpoint and <memberId>
    When I post a request with valid details
    Then the response status code is 404
    And the response body returns appropriate error message
    Examples:
      | memberId |
      | *        |

  Scenario Outline: Request quote for loan amount too less or too high
    Given I have quote endpoint and <memberId>
    When I post a request with details <currentSalary> and <amountToBorrow> and <termLength>
    Then the response status code is 400
    And the response body returns false decision for quote
    Examples:
      | memberId                             | currentSalary | amountToBorrow | termLength |
      | 95661dee-a9cc-426a-8374-96eded960224 | 50000         | 999            | 12         |

