Feature: Successful user activation via email

  @T
  Scenario: User can be activated via email
    Given Registered via email user
    When User click activation link
    Then User's account activates