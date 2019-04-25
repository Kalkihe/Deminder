Feature: A user creates a new subtask

  Scenario: The user clicks on the add "subtask button"
    Given "ManageDeadlinePage" is open on the screen
    And it should contain a "title"
    And it should contain a "title textfield"
    And it should contain a "date lable"
    And it should contain a "datefield"
    And it should contain a "textarea" for notes
    And it should contain a "subtask lable"
    And it should contain a "plus button" for subtasks
    And it should contain a "save button"
    And it should contain a "delete button"
    When the user clicks on the "plus button"
    Then it should add the a new line for the subtask

  Scenario: The user clicks on add "subtask button"
    When the user clicks on the "subtask button"
    Then a new "textfield" is added
    And a new "checkbox" is added
    And a new "delete button" is added