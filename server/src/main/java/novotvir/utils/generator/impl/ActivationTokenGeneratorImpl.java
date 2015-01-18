package novotvir.utils.generator.impl;

import novotvir.utils.generator.ActivationTokenGenerator;

import java.util.UUID;

// @author Titov Mykhaylo (titov) on 19.05.2014.
public class ActivationTokenGeneratorImpl implements ActivationTokenGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
