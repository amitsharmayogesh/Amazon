package core;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.junit.runner.Description;

/**
 * Created by Amit on 29-11-2015.
 */


@RunWith(Cucumber.class)
@CucumberOptions(features = "D:\\Projects\\Demo\\src\\test\\resources\\com.features",
                 glue = "businessDefination",
                 tags = {"@Launch"},
                 monochrome = true,
                 plugin = {"pretty","html:target/cucumber","json:target/cucumber.json"})
public class TestRunner {


}
