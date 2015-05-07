package web.signup.email.generator.impl;

import org.springframework.stereotype.Component;
import web.signup.email.generator.ActivationTokenGenerator;

import java.util.UUID;

// @author Titov Mykhaylo (titov) on 19.05.2014.
@Component("activationTokenGenerator")
public class ActivationTokenGeneratorImpl implements ActivationTokenGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}