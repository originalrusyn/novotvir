package novotvir.service.impl;

import novotvir.dto.RegDto;
import novotvir.persistence.domain.User;
import novotvir.service.UserGoogleRegService;
import novotvir.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;

// @author: m on 01.05.15 16:27.
@Service("userGoogleRegService")
public class UserGoogleRegServiceImpl implements UserGoogleRegService {
    @Autowired UserRegService userRegService;

    @Override
    public User registerUser(Person person) {
        return userRegService.registerUser(RegDto.getInstance(person));
    }
}
