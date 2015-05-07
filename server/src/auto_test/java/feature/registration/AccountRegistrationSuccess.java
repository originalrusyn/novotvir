package feature.registration;

import checker.ActivationMailChecker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import feature.domain.Application;
import feature.domain.Person;
import org.springframework.test.context.ContextConfiguration;
import util.invoker.SignUpInvoker;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static feature.domain.AppVersion._1_0;
import static feature.domain.Device.android;
import static feature.domain.Email.uniqueEmail;

// @author: Mykhaylo Titov on 08.09.14 23:03.
@ContextConfiguration("classpath:cucumber.xml")
public class AccountRegistrationSuccess{

    @Resource SignUpInvoker signUpInvoker;
    @Resource ActivationMailChecker activationMailChecker;

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
        persons.forEach(activationMailChecker::check);
    }
}