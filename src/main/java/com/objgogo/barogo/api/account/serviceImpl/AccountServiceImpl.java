package com.objgogo.barogo.api.account.serviceImpl;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.account.repository.AccountRepository;
import com.objgogo.barogo.api.account.service.AccountService;
import com.objgogo.barogo.api.account.vo.RegisterUserRequest;
import com.objgogo.barogo.api.account.vo.RegisterUserResponse;
import com.objgogo.barogo.common.exception.BarogoException;
import com.objgogo.barogo.common.util.PasswordUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;
    private PasswordUtil passwordUtil;

    public AccountServiceImpl(AccountRepository accountRepository, PasswordUtil passwordUtil) {
        this.accountRepository = accountRepository;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {

        Optional<AccountEntity> isUsername = accountRepository.findByUsername(registerUserRequest.getUsername());

        if(isUsername.isPresent()){
            throw new BarogoException("ERROR.ACCOUNT.003");
        } else {
            ModelMapper mapper = new ModelMapper();
            registerUserRequest.setPassword(passwordUtil.passwordEncoding(registerUserRequest.getPassword()));

            AccountEntity accountEntity = mapper.map(registerUserRequest,AccountEntity.class);
            accountEntity.setCreateDt(LocalDateTime.now());

            accountEntity = accountRepository.save(accountEntity);

            return mapper.map(accountEntity, RegisterUserResponse.class);
        }
    }
}
