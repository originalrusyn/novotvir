package novotvir.service;

import novotvir.dto.RegDto;
import novotvir.persistence.domain.User;

// @author Titov Mykhaylo (titov) on 07.05.2014.
public interface UserRegService {
    User registerUser(RegDto regDto);
}
