package br.com.sensedia.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "br.com.sensedia",
        plugin = {"pretty"}
)
public class BoardRunnerTest {
}


