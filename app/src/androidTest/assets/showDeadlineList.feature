Feature: A User opens the app to see the deadline overview

  Scenario: show deadlines
    Given The deadlines to see are already existing
    And The app is running
    Then The overview of all created deadlines is shown as a list

    Scenario: show deadlines
      Given There are no deadlines created
      And The app is running
      Then The overview is empty