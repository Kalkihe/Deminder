package com.team.deminder.deminder;

import android.support.test.rule.ActivityTestRule;

import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import org.junit.Rule;

class AddDeadlineStepDefinitions extends GreenCoffeeSteps {

    @Rule
    public final ActivityTestRule<ManageDeadlinePage> main = new ActivityTestRule<>(ManageDeadlinePage.class);

    @Then("^it should contain a title textfield$")
    public void itShouldContainATitleTextfield() throws Throwable {
        onViewWithId(R.id.textTaskName).isDisplayed();
    }

    @And("^it should contain a datefield$")
    public void itShouldContainADatefield() throws Throwable {
        onViewWithId(R.id.textDeadline).isDisplayed();
    }

    @And("^it should contain a reacurringCheckbox for reacurring$")
    public void itShouldContainAReacurringCheckboxForReacurring() throws Throwable {
        onViewWithId(R.id.checkBoxRecurring).isDisplayed();
    }

    @And("^it should contain a textarea for notes$")
    public void itShouldContainATextareaForNotes() throws Throwable {
        onViewWithId(R.id.textNotes).isDisplayed();
    }

    @And("^it should contain a subtask lable$")
    public void itShouldContainASubtaskLable() throws Throwable {
        onViewWithId(R.id.labelSubtask).isDisplayed();
    }

    @And("^it should contain a plus button for subtasks$")
    public void itShouldContainAPlusButtonForSubtasks() throws Throwable {
        onViewWithId(R.id.buttonAddSubtask).isDisplayed();
    }

    @And("^it should contain a save button$")
    public void itShouldContainASaveButton() throws Throwable {
        closeKeyboard();
        onViewWithId(R.id.buttonSave).isDisplayed();
    }

    @And("^it should contain a delete button$")
    public void itShouldContainADeleteButton() throws Throwable {
        onViewWithId(R.id.buttonDelete).isDisplayed();
    }

    @When("^the user clicks on the add subtask button$")
    public void theUserClicksOnTheAddSubtaskButton() throws Throwable {
        onViewWithId(R.id.buttonAddSubtask).click();
    }

    @Then("^a new subtaskLayoutWidget is added$")
    public void aNewSubtaskLayoutWidgetIsAdded() throws Throwable {
        onViewWithId(R.id.subtaskLayoutWidget).isDisplayed();
    }

    @When("^the user clicks on the delete button$")
    public void theUserClicksOnTheDeleteButton() throws Throwable {
        onViewWithId(R.id.buttonDelete).click();
    }

    @Then("^the create deadline view is closed$")
    public void theCreateDeadlineViewIsClosed() throws Throwable {
        onViewWithId(R.id.deadlineOverviewPage).isDisplayed();
    }

    @Given("^title textfield is not empty$")
    public void titleTextfieldIsNotEmpty() throws Throwable {
        onViewWithId(R.id.textTaskName).type("TestTitle");
        closeKeyboard();
    }

    @And("^datefield is not empty$")
    public void datefieldIsNotEmpty() throws Throwable {
        onViewWithId(R.id.textDeadline).isNotEmpty();
    }

    @When("^the user clicks on the save button$")
    public void theUserClicksOnTheSaveButton() throws Throwable {
        onViewWithId(R.id.buttonSave).click();
    }

    @Then("^the ManageDeadlinePage is closed$")
    public void theManageDeadlinePageIsClosed() throws Throwable {
        onViewWithId(R.id.deadlineOverviewPage).isDisplayed();
    }

    @And("^the deadline object is saved$")
    public void theDeadlineObjectIsSaved() throws Throwable {
    }

    @And("^the DeadlineOverviewPage gets updated$")
    public void theDeadlineOverviewPageGetsUpdated() throws Throwable {
    }

    @Given("^title textfield or date field are empty$")
    public void titleTextfieldOrDateFieldAreEmpty() throws Throwable {
        onViewWithId(R.id.textTaskName).isEmpty();
    }

    @When("^the user clicks on save button$")
    public void theUserClicksOnSaveButton() throws Throwable {
        onViewWithId(R.id.buttonSave).click();
    }

    @Then("^toast with error message is shown$")
    public void toastWithErrorMessageIsShown() throws Throwable {
        onViewWithId(R.id.rootLayout).isDisplayed();
    }
}
