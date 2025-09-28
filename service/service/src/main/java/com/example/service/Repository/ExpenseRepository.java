package com.example.service.Repository;

import com.example.service.Entities.ExpenseEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends CrudRepository<ExpenseEntity , Long> {
    List<ExpenseEntity> findByUserId(String userId);

    List<ExpenseEntity> findByUserIdAndCreatedAtBetween(String userId , Timestamp starttime , Timestamp endtime);

    Optional<ExpenseEntity> findByUserIdAndExternalId(String userId, String externalId);
}
