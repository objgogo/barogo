package com.objgogo.barogo.api.login.serviceImpl;


import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.account.repository.AccountRepository;
import com.objgogo.barogo.common.exception.BarogoException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private AccountRepository accountRepository;

    public CustomUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return accountRepository.findByUsername(username)
                .map(this::convertAccountEntityToUserDetails)
                .orElseThrow(()-> new BarogoException("ERROR.ACCOUNT.005"));
    }

    //AccountEntity 정보를 UserDetails로 변환
    public UserDetails convertAccountEntityToUserDetails(AccountEntity accountEntity){
        return User.builder()
                .username(accountEntity.getUsername())
                .password(accountEntity.getPassword())
                .roles(accountEntity.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .build();
    }

}
