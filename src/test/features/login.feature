Feature: Test login

  Scenario: Login Functionality
    Given I navigate to the CE application
    When I try to login with valid credentials
    Then I should see that I logged in successfully
