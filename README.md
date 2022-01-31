# zopaApiTest
Technical Test for Zopa Quote Engine Api
Zopa Technical Test Framework using Cucumber and RestAssured for API testing.
This is the automation test project for the the api : I https://qanat-quotespublic.staging.zopa.com/api/docs.

The test scenarios are located in features folder in src->test->java->features.
There are two feature files :
1. To create a member id and to retrieve the member id.
2. To submit quote with the member id created previously.

These scenarios cover functional testing ( positive & negative scenarios).
Additional types of testing that should be considered are non-functional testing ( security & performance testing).
For security testing - passing special characters in request body & url( these are covered in negative scenarios).
For performance testing - I have prepared .jmx file. To run the performance script, download jmeter and import .jmx file.
Download .csv file provided in github.,as it will be used for test data.

Project folder Structure:
src
    main
        java
            commons (Common configuration) - common configuration used across the framework.
            utils (Utilities & libraries) - common methods that can be reused across the project.
            resources
    test
        java
            features (feature files containing scenarios to test)
            StepDefs ( implements actions to the scenario steps.)
            TestRunner ( provides configuration for reports, glues , and tags.)
             Note: to run specific scenarios , assign a tag on that scenario in the feature file against specific
             scenario and provide that tag in test runner,and either right click this file to run or run mvn test.
        resources
            config.json ( config file to set url and endpoints)
            createMember.json ( json file with request body for member endpoint to create a new member.)
            quote.json ( Input json request file for quote endpoint.)
            Note: The values provided in the above two json files can be replaced/overwritten either in feature file or
            in step definitions , to test varied scenarios.
target
    cucumber-html-reports
           report.html ( this file stores the html report. To view the report in html format , either navigate to the
           the location of the file and open in on browser. or double click on file in IDE, and choose a browser to open
           the file on the top right corner.
           Note: this folder gets deleted everytime we run mvn clean and gets created upon running mvn verify

GETTING STARTED:

SETUP:
1.Java:
Install JDK and set JAVA_HOME variable by following the below instructions
https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/

2.Maven:
Download the maven from https://maven.apache.org/download.cgi
Install the Maven following the instructions https://maven.apache.org/install.html

3.Git:
Download the git from https://git-scm.com/download/win
Install the git and copy the path then follow the below instructions
    A.  Right-Click on My Computer
    B.  Click Advanced System Settings
    C.  Click Environment Variables
    D.  Then under System Variables look for the path variable and click edit
    E.   Add the path to git’s bin and cmd at the end

Download/Clone the project:
    1. Open the project location from the below URL
        https://github.com/gayatri-yv/zopaApiTest
    2. Clone the repo into your local environment using
        git clone https://github.com/gayatri-yv/zopaApiTest.git


Build & Run the project:
   1. To build the project run the below command from the project root directory.
            mvn clean compile
   2. Once the Build is success, Run the sample test by running the below command from the project root directory
        mvn test
        Note: to generate html report, run
        mvn verify

Report Path :
- Cucumber HTML Report: `{ROOT_PROJECT_FOLDER}/target/cucumber-html-reports/report.html`
Note: Report need to be viewed in browser ( preferably IE Edge).
