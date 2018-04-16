package com.test.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.test.domain.User;
import com.test.repositories.UserRepository;
import com.test.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserService extends BaseService<User> implements IUserService, UserDetailsService {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private UserRepository repository;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<User, Long> getRepository() {
        return (JpaRepository) repository;
    }

    @Override
    public User findByUserName(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        final User user = findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found by username " + userName);
        }
        return new HappyfacesUserDetails(user);
    }
    
    @Override
    public boolean isPaswordCorrect(User user, String password) {
        User userFromDb = findById(user.getId());
        PasswordEncoder encoder = new StandardPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        return encodedPassword.equals(userFromDb.getPassword());
    }
    
    @Override
    @Transactional(readOnly = false)
    public void changePassword(User user, String newPassword) {
        PasswordEncoder encoder = new StandardPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);

        User userFromDb = findById(user.getId());
        userFromDb.setPassword(encodedPassword);
        update(userFromDb);
    }

    public static final class HappyfacesUserDetails implements UserDetails {

        private static final long serialVersionUID = 1L;

        private User user;

        private HappyfacesUserDetails(User user) {
            super();
            this.user = user;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }
        
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
            roles.add(new GrantedAuthority() {
                private static final long serialVersionUID = 1L;

                @Override
                public String getAuthority() {
                    return "ROLE_USER";
                }
            });
            return roles;
        }

        public User getUser() {
            return user;
        }

    }

}
