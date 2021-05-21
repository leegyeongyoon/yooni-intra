package com.yooni.intra.common.service.user;

import com.yooni.intra.common.repository.UserJpaRepository;
import com.yooni.intra.exception.detailException.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserJpaRepository userJpaRepository;


    @Override
    public UserDetails loadUserByUsername(String userPk)  {
        return userJpaRepository.findByUserid(userPk).orElseThrow(UserNotFoundException::new);
    }
}
