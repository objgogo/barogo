package com.objgogo.barogo.api.account.repository;

import com.google.common.io.Files;
import com.objgogo.barogo.api.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {

    Optional<AccountEntity> findByUsernameAndPassword(String username, String password);


    Optional<AccountEntity> findByUsername(String username);
}
