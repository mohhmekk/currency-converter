package org.sample.currency.app.acceptance;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sample.currency.app.BrowserDriver;

import java.util.List;

/**
 * Created by mohamedmekkawy.
 */
public class LoginSteps {

    LoginContainer loginContainer;

    @Given("^I navigate to the CE application$")
    public void I_navigate_to_home_screen() throws Throwable {
        BrowserDriver.loadPage("http://localhost:8080/");

        loginContainer = new LoginContainer();
    }

    @When("^I try to login with valid credentials$")
    public void I_try_to_login() throws Throwable {

                loginContainer.getUserNameText().sendKeys("test123");
                loginContainer.getPasswordText().sendKeys("Password1");

        loginContainer.getLoginBtn().click();
    }

    @Then("^I should see that I logged in successfully")
    public void i_should_see_that_i_logged_in() throws Throwable {

        Assert.assertNotNull(loginContainer.getLogoutButton());
    }

    private  static class LoginContainer {

        public  WebElement getLoginBtn(){
            return  BrowserDriver.getCurrentDriver().findElement(By.name("LogInButton"));
        }

        public  WebElement getUserNameText(){
            return BrowserDriver.getCurrentDriver().findElement(By.name("username"));
        }

        public  WebElement getPasswordText(){
            return BrowserDriver.getCurrentDriver().findElement(By.name("password"));
        }


        public  WebElement getLogoutButton(){
            return BrowserDriver.getCurrentDriver().findElement(By.partialLinkText("Logout"));
        }

    }
}


