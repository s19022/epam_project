package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.AccountDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDetailsRepository extends CrudRepository<AccountDetails, Long> {
    Optional<AccountDetails> findByLogin(String login);
}
