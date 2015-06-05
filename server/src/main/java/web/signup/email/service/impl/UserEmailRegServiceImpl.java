package web.signup.email.service.impl;

import common.executor.AfterCommitExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.persistence.domain.User;
import web.signup.dto.RegDto;
import web.signup.email.dto.UserRegDetailsDto;
import web.signup.service.UserRegService;

import javax.annotation.Resource;


// @author Titov Mykhaylo (titov) on 07.05.2014.
@Service("userEmailRegService")
public class UserEmailRegServiceImpl {
    @Resource AfterCommitExecutor afterCommitExecutor;
    @Resource ConfirmationMailMailServiceImpl confirmationMailMailService;
    @Resource UserRegService userRegService;

    @Transactional
    public User registerUser(UserRegDetailsDto userRegDetailsDto) {
        User user = userRegService.registerUser(RegDto.getInstance(userRegDetailsDto));
        afterCommitExecutor.execute(() -> confirmationMailMailService.sendMailConfirmationLink(user));
        return user;
    }
}
