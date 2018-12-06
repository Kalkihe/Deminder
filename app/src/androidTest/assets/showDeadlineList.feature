Feature: A User opens the app to see the deadline overview

  Scenario: show deadlines
    Given The deadlines to see are already existing
    And The app is running
    Then The overview of all created deadlines is shown as a list
    When The deadlines has a priority
    Then They have another color
