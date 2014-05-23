package novotvir.service.impl;

import novotvir.dto.RegDto;
import novotvir.dto.UserRegDetailsDto;
import novotvir.executors.AfterCommitExecutor;
import novotvir.persistence.domain.User;
import novotvir.service.ConfirmationMailMailService;
import novotvir.service.UserEmailRegService;
import novotvir.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
@Service("userEmailRegService")
public class UserEmailRegServiceImpl implements UserEmailRegService{
    @Autowired AfterCommitExecutor afterCommitExecutor;
    @Autowired ConfirmationMailMailService confirmationMailMailService;
    @Autowired UserRegService userRegService;

    @Override
    @Transactional(propagation = REQUIRED)
    public User registerUser(UserRegDetailsDto userRegDetailsDto) {
        User user = userRegService.registerUser(RegDto.getInstance(userRegDetailsDto));
        afterCommitExecutor.execute(() -> confirmationMailMailService.sendMailConfirmationLink(user));
        return user;
    }
}
