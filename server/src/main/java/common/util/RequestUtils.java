package common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

// @author Titov Mykhaylo (titov) on 11.01.14 21:28
@Slf4j
public class RequestUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getServerURL() {
        try {
            HttpServletRequest request = getHttpServletRequest();
            URL reconstructedURL = new URL(request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(), request.getContextPath());
            return reconstructedURL.toString();
        } catch (MalformedURLException e) {
            log.error("Couldn't get Server URL", e);
            return null;
        }
    }

    public static Locale getRequestLocale() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getLocale();
    }

    public static String getRequestUrl(){
        HttpServletRequest request = getHttpServletRequest();
        return request.getRequestURL().toString();
    }

    public static String getRemoteAddr(){
        return getHttpServletRequest().getRemoteAddr();
    }
}
