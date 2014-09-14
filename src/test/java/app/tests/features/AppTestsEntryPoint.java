package app.tests.features;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import static cucumber.api.SnippetType.CAMELCASE;

// @author: Mykhaylo Titov on 13.09.14 22:50.
@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "@T",
        glue = "classpath:app.tests.features.registration",
        features = "src/test/resources/app/tests/features/registration",
        monochrome = false,
        format = {"html:target/build/reports/tests/cucumber"},
        strict = false,
        snippets = CAMELCASE)
public class AppTestsEntryPoint {
}
