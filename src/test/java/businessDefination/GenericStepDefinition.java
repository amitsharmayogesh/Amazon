package businessDefination;

import com.core.Framework;
import com.core.Logger;
import com.core.UIOperator;
import com.core.WebdriverWrapper;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

import java.util.List;

/**
 * Created by Amit on 05-12-2015.
 */
public class GenericStepDefinition {

    UIOperator oUI;

    @Before
    public void Initialize(Scenario scenario)throws Exception{
        Framework frameWork = Framework.CreateInstance(scenario.getName());
        frameWork.strCurrentScenarioName = scenario.getName();
        oUI = Framework.framework.UI;
        //Logger.Initialize("AUTOMATION RESULT",frameWork.strCurrentScenarioName);
        if (Framework.framework.webDriver == null){
            WebdriverWrapper.LoadWebDriver();
        }
    }

    @After
    public void Dispose() throws Exception{
        Logger.WriteScenarioFooter(Framework.framework.strCurrentScenarioName);
    }


    @Given("^Launch and Login into Amazon Website$")
    public void Launch_and_Login_into_Amazon_Website(List<LoginCredentials> loginCredentialsList) throws Throwable {

        LoginCredentials loginCredentials = loginCredentialsList.get(0);

        Logger.WriteLog("Launching Amazon Website",true,false,true);

        Framework.framework.webDriver.navigate().to(Framework.framework.GetProperty("APP_URL"));

        Framework.framework.webDriver.manage().window().maximize();

        Thread.sleep(30000);

        oUI.Click("SignIn_Link");

        Thread.sleep(30000);

        oUI.EnterText("Email_Field", loginCredentials.username);

        Thread.sleep(5000);

        oUI.EnterText("Password_Field", loginCredentials.passsword);

        Thread.sleep(5000);

        oUI.Click("LogIn_Button");


    }

    private class LoginCredentials{
        private String username;
        private String passsword;
    }
}
