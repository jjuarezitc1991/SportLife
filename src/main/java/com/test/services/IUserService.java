package com.test.services;

import com.test.domain.User;
import com.test.services.base.IService;

public interface IUserService extends IService<User> {
    User findByUserName(String username);
    boolean isPaswordCorrect(User user, String password);
    void changePassword(User user, String newPassword);
}
