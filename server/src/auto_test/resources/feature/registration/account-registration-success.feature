Feature: Successful user registration via email

  Scenario: User can be registered via email
    Given First time user
    When User creates credentials
    Then Account activation email sends