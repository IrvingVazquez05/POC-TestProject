package cucumbermm;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        dryRun = false,
        monochrome = true,
        features = "src/test/resources/cucumbermm/features",
        tags = "@GetPetById" 
        )
public class RunCucumberTest {

}
