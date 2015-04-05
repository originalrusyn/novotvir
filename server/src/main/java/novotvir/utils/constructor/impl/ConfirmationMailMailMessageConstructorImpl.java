package novotvir.utils.constructor.impl;

import exceptions.CouldNotConstructMailException;
import lombok.extern.slf4j.Slf4j;
import novotvir.controller.AccountActivationController;
import novotvir.persistence.domain.User;
import novotvir.service.CustomMessageSource;
import novotvir.utils.constructor.ConfirmationMailMailMessageConstructor;
import novotvir.utils.filters.HiddenHttpMethodEnhancedFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static novotvir.controller.AccountActivationController.ACTIVATION_TOKEN_REQ_PARAM;
import static novotvir.controller.AccountActivationController.NAME_PATH_VAR;
import static novotvir.utils.RequestUtils.getServerURL;
import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;
import static org.springframework.social.support.URIBuilder.fromUri;

// @author Titov Mykhaylo (titov) on 16.05.2014.
@Slf4j
public class ConfirmationMailMailMessageConstructorImpl implements ConfirmationMailMailMessageConstructor {

    @Autowired SimpleMailMessage templateMessage;
    @Autowired HiddenHttpMethodEnhancedFilter hiddenHttpMethodEnhancedFilter;
    @Autowired CustomMessageSource customMessageSource;

    @Override
    public SimpleMailMessage construct(User user){
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);

        RequestMapping annotation = getAnnotation(AccountActivationController.class, RequestMapping.class);

        String base = "";
        if(nonNull(annotation)){
            base = annotation.value()[0];
        }

        RequestMapping requestMapping = getActivationRequestMapping();

        String activationUri = requestMapping.value()[0].replaceFirst("\\{.*"+ NAME_PATH_VAR +".*\\}", user.name);
        RequestMethod method = requestMapping.method()[0];

        String emailValidationUrl = fromUri(getServerURL() + base + activationUri).queryParam(ACTIVATION_TOKEN_REQ_PARAM, user.activationToken).queryParam(hiddenHttpMethodEnhancedFilter.getMethodParam(), method.name()).build().toString();

        String subj = customMessageSource.getMailValidationMailSubj();
        String text = customMessageSource.getMailValidationMailText(emailValidationUrl);

        msg.setTo(user.email);
        msg.setSubject(subj);
        msg.setText(text);

        return msg;
    }

    @Cacheable("activateMethodRequestMapping")
    private RequestMapping getActivationRequestMapping() {
        Method[] methods = AccountActivationController.class.getMethods();
        Method activateMethod = asList(methods).stream().filter(e-> e.getName().equals("activate")).findFirst().get();
        if (isNull(activateMethod)) throw new CouldNotConstructMailException("Couldn't retrieve activationUrl and http method to construct mail message", null);
        return getAnnotation(activateMethod, RequestMapping.class);
    }
}
