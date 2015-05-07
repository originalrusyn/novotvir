package common.util.filter.impl;

import lombok.Getter;
import lombok.Setter;
import common.util.filter.HiddenHttpMethodEnhancedFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Locale.ENGLISH;
import static org.springframework.util.StringUtils.hasLength;

// @author Titov Mykhaylo (titov) on 16.05.2014.
@Component("hiddenHttpMethodEnhancedFilter")
public class HiddenHttpMethodEnhancedFilterImpl extends OncePerRequestFilter implements HiddenHttpMethodEnhancedFilter{

    public static final String DEFAULT_METHOD_PARAM = "_method";

    @Setter @Getter
    private String methodParam = DEFAULT_METHOD_PARAM;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String paramValue = request.getParameter(this.methodParam);
        String reqMethod = request.getMethod();
        if (("POST".equals(reqMethod)|| "GET".equals(reqMethod)) && hasLength(paramValue)) {
            String method = paramValue.toUpperCase(ENGLISH);
            HttpServletRequest wrapper = new HttpMethodRequestWrapper(request, method);
            filterChain.doFilter(wrapper, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

        @Getter
        private final String method;

        public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }
    }
}
