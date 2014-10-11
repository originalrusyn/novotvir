package features.registration;

import features.domain.*;
import utils.invokers.SignUpInvoker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static features.domain.AppVersion._1_0;
import static features.domain.Device.android;
import static features.domain.Email.uniqueEmail;

// @author: Mykhaylo Titov on 08.09.14 23:03.
public class AccountRegistrationSuccess{

    @Resource SignUpInvoker signUpInvoker;

    List<Person> persons = new ArrayList<>();

    @Given("^First time user$")
    public void First_time_user() throws Throwable {
        persons.add(new Person().addApplication(new Application(android("4.4"), _1_0)).addEmail(uniqueEmail()));
    }

    @When("^User creates credentials$")
    public void User_creates_credentials() throws Throwable {
        persons.forEach(signUpInvoker::invoke);
    }

    @Then("^Account activation email sends$")
    public void Account_activation_email_sends() throws Throwable {

    }
}