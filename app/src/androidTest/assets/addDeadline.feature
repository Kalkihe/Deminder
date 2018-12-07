Feature: A user creates a new deadline

  Scenario: The user clicks on the add deadline button
    Then it should contain a title textfield
    And it should contain a datefield
    And it should contain a reacurringCheckbox for reacurring
    And it should contain a textarea for notes
    And it should contain a subtask lable
    And it should contain a plus button for subtasks
    And it should contain a save button
    And it should contain a delete button

  Scenario: The user clicks on add subtask button
    When the user clicks on the add subtask button
    Then a new subtaskLayoutWidget is added

  Scenario: The user clicks on the delete button
    When the user clicks on the delete button
    Then the create deadline view is closed

  Scenario: The user clicks on the save button and mandatory fields filled out
    Given title textfield is not empty
    And datefield is not empty
    When the user clicks on the save button
    Then the ManageDeadlinePage is closed
    And the deadline object is saved
    And the DeadlineOverviewPage gets updated

  Scenario: The user clicks on the save button amd mandatory fields are not filled out
    Given title textfield or date field are empty
    When the user clicks on save button
    Then toast with error message is shown