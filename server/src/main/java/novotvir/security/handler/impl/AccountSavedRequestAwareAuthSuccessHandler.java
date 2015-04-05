package novotvir.security.handler.impl;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import novotvir.dto.AccountDto;
import novotvir.security.credential.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @author: Titov Mykhaylo (titov) on 31.03.15 21:46.
public class AccountSavedRequestAwareAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private JsonEncoding encoding = JsonEncoding.UTF8;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AccountDto accountDto = AccountDto.accountDto(((UserDetailsImpl) authentication.getPrincipal()).getUser());

        ObjectMapper objectMapper = new ObjectMapper();

        ServletOutputStream outputStream = response.getOutputStream();
        JsonGenerator generator = objectMapper.getJsonFactory().createJsonGenerator(outputStream, encoding);

        // A workaround for JsonGenerators not applying serialization features
        // https://github.com/FasterXML/jackson-databind/issues/12
        if (objectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            generator.useDefaultPrettyPrinter();
        }

        objectMapper.configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, false);
        objectMapper.writeValue(generator, accountDto);

        response.setContentType("application/json");

        super.onAuthenticationSuccess(request, response, authentication);
        generator.flush();

    }
}
