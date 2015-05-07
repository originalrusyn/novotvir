package web.signup.service;

import web.persistence.domain.User;
import web.signup.dto.RegDto;

// @author Titov Mykhaylo (titov) on 07.05.2014.
public interface UserRegService {
    User registerUser(RegDto regDto);
}
