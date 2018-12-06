Feature: An already existing subtask has to be managable. The user can modify the title. Also, one
  can delete the subtask or set if the subtask is completed or not.

  As a user of Deminder
  i want to manage an already created subtask
  so that I updated its content.

  Scenario: edit subtask name
    Given The subtask to change is already existing
    And The app is running
    And I click on the "edit button" for this specific deadline
    And I click on the specific subtask
    When I click into the "name field" for the subtask
    And I edit the text in the "name field"
    And I click on the "save button"
    Then the name of the subtask is updated in the openend deadline corresponding to my changes

  Scenario: Delete the subtask
    Given The subtask to change is already existing
    And The app is running
    When I click on the "edit button" for the specific deadline
    And I scroll to the subtask overview
    And I click on the "delete button" next to the subtask to delete
    And I confirm the 'Are you sure?' prompt with 'Yes'
    Then the subtask is deleted

  Scenario: set that the subtask is completed
    Given The subtask to delete is already existing
    And The app is running
    When I click on the "edit button" for this specific deadline
    And I scroll to the subtask overview
    And I check the "checkbox" next to the subtask which is completed
    Then The subtask is strike through

  Scenario: set that the subtask isn't completed anymore
    Given The subtask to change is already existing
    And The app is running
    When I click on the "edit button" for this specific deadline
    And I scroll to the subtask overview
    And I check the "checkbox" next to the subtask to change
    Then The subtask isn't strike through anymore