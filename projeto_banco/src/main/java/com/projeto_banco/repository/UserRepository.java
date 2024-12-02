package com.projeto_banco.repository;

import com.projeto_banco.models.Account;
import com.projeto_banco.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT COUNT(id) > 0 FROM user WHERE name = :name AND password = :password", nativeQuery = true)
    public int checkExists(@Param("name") String name, @Param("password") String password);

    @Query(value = "SELECT * FROM user WHERE name = :name AND password = :password", nativeQuery = true)
    public User getUser(@Param("name") String name, @Param("password") String password);
}
