package novotvir.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Titov Mykhaylo (titov)
 *         11.01.14 21:28
 */
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
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static String getRemoteAddr(){
        return getHttpServletRequest().getRemoteAddr();
    }
}
