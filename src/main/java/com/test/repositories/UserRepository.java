package com.test.repositories;

import com.test.customrepository.GenericRepository;
import com.test.domain.User;

public interface UserRepository extends GenericRepository<User, Long> {
    User findByUsername(String username);
}
