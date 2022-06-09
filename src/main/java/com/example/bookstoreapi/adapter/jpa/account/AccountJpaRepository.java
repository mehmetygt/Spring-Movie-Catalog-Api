package com.example.bookstoreapi.adapter.jpa.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByMail(String mail);

    Boolean existsByMail(String mail);
}
