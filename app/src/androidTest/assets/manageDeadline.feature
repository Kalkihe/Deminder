Feature: An already existing deadline has to be managable. The user can modify the title, the description and the due date. Also, one
  can delete the deadline or set if it's a recurring deadline.

  As a user of Deminder
  i want to manage an already created deadline
  so that I updated its content.

  Scenario: edit deadline name
    Given The deadline to change is already existing
    And The app is running
    And I click on the "edit button" for this specific deadline
    When I click into the "name field"
    And I edit the text in the "name field"
    And I click on the "save button"
    Then the name of the deadline is updated corresponding to my changes

  Scenario: edit deadline date
    Given The deadline to change is already existing
    And The app is running
    And I click on the "edit button" for this specific deadline
    When I click into the field named "Deadline"
    And I select a new date
    And I click on the "save button"
    Then the date of the dealine is updated corresponding to my changes

  Scenario: set if the deadline is recurring
    Given The deadline to change is already existing
    And The app is running
    When I click on the "edit button" for this specific deadline
    And I click on the "save button"
    Then the repetition of the deadline is updated corresponding to my changes

  Scenario: change the description of the deadline
    Given The deadline to change is already existing
    And The app is running
    When I click on the "edit button" for this specific deadline
    And I click into the "texfield"
    And I change the text in this "textfield"
    And I click on the "save button"
    Then the description of the deadline is updated corresponding to my changes

  Scenario: Delete the deadline
    Given The deadline to change is already existing
    And The app is running
    When I click on the "edit button" for this specific deadline
    And I click on the "delete button"
    And I confirm the 'Are you sure?' prompt with 'Yes'
    Then the deadline is deleted
