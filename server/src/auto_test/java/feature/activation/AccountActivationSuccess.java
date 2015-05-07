package feature.activation;

// @author: Mykhaylo Titov on 08.09.14 23:40.

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import feature.domain.Application;
import feature.domain.Person;
import org.springframework.test.context.ContextConfiguration;
import util.invoker.EmailActivationInvoker;
import util.invoker.SignUpInvoker;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static feature.domain.AppVersion._1_0;
import static feature.domain.Device.android;
import static feature.domain.Email.uniqueEmail;

@ContextConfiguration("classpath:cucumber.xml")
public class AccountActivationSuccess {

    @Resource SignUpInvoker signUpInvoker;
    @Resource EmailActivationInvoker emailActivationInvoker;

    List<Person> persons = new ArrayList<>();

    @Given("^Registered via email user$")
    public void Registered_via_email_user() throws Throwable {
        persons.add(new Person().addApplication(new Application(android("4.4"), _1_0)).addEmail(uniqueEmail()));
        persons.forEach(signUpInvoker::invoke);
    }

    @When("^User click activation link$")
    public void User_click_activation_link() throws Throwable {
        persons.forEach(emailActivationInvoker::invoke);
    }

    @Then("^User's account activates$")
    public void User_s_account_activates() throws Throwable {
    }
}
