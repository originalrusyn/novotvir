package app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

// @author: Mykhaylo Titov on 09.01.15 23:11.
public abstract class AppService {

    @Value("${environment_url}") String environmentUrl;
    @Resource RestTemplate restTemplate;
}
