import com.mauriciotogneri.greencoffee.GreenCoffeeConfig;
import com.mauriciotogneri.greencoffee.GreenCoffeeTest;
import com.mauriciotogneri.greencoffee.ScenarioConfig;
import com.team.deminder.deminder.ManageDeadlinePage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

import androidx.test.rule.ActivityTestRule;

@RunWith(Parameterized.class)
public class addDeadlineTestLauncher extends GreenCoffeeTest {

    @Rule
    public ActivityTestRule<ManageDeadlinePage> activity = new ActivityTestRule<>(ManageDeadlinePage.class);

    public addDeadlineTestLauncher(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException
    {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/addDeadline.feature")
                .takeScreenshotOnFail()
                .scenarios(); // the locales used to run the scenarios (optional)
    }

    @Test
    public void test()
    {
        start(new AddDeadlineStepDefinitions());
    }
}
