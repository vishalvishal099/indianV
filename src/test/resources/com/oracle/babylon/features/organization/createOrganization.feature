Feature: Contains test scenarios related to Organization and username/password creation




  @create_org
  Scenario: Create a organization and store information in UserData.json
    When user tries to create a organization
    Then user is able to login to application

    @fill_acnt_details
    Scenario: Fill up the account details
      When user needs to fill in the account details fields with data
        | Title | Job_Function  | Job_Title|
        | Professor  | Quality Control | Consultant |
      Then views the home page



