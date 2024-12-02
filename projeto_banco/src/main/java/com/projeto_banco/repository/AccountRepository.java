package com.projeto_banco.repository;

import com.projeto_banco.models.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {


    @Query(value = "SELECT * FROM account WHERE user_id = :userId LIMIT 1", nativeQuery = true)
    public Account getByUseId(@Param("userId") Long userId);


    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET balance = :balance WHERE id = :accountId", nativeQuery = true)
    public void updateBalance(@Param("accountId") Long accountId, @Param("value") Double balance);
}
