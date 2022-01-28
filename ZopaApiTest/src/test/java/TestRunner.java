import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = "classpath:features",
        monochrome = true,
        tags = "@All",
        plugin = { "pretty","html:target/cucumber-html-reports/report.html" }
)
public class TestRunner {
}
