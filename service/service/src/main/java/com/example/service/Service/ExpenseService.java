package com.example.service.Service;

import com.example.service.Dto.ExpenseDto;
import com.example.service.Entities.ExpenseEntity;
import com.example.service.Repository.ExpenseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public boolean createExpense(ExpenseDto expenseDto){
        setCurrency(expenseDto);

        try{
            expenseRepository.save(objectMapper.convertValue(expenseDto , ExpenseEntity.class));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean UpdateExpense(ExpenseDto expenseDto){
        Optional<ExpenseEntity> expensefound = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(), expenseDto.getExternal_id());
        if(expensefound.isEmpty()){
            return false;
        }
        ExpenseEntity expenseEntity = expensefound.get();
        expenseEntity.setCurrency(Strings.isNotBlank(expenseDto.getCurrency())? expenseDto.getCurrency() : expenseEntity.getCurrency());
        expenseEntity.setMerchant(Strings.isNotBlank(expenseDto.getMerchant())? expenseDto.getMerchant() : expenseEntity.getMerchant());

        expenseEntity.setAmount(expenseDto.getAmount());
        expenseRepository.save(expenseEntity);
        return true;
    }

    public Boolean deleteExpense(String userId){
        List<ExpenseEntity> expenseEntity = expenseRepository.findByUserId(userId);
        if(expenseEntity.isEmpty()){
            return false;
        }
        expenseRepository.delete(expenseEntity.getFirst());
        return true;
    }


    public List<ExpenseDto> getExpense(String userId){
        List<ExpenseEntity> epenselist = expenseRepository.findByUserId(userId);

        return objectMapper.convertValue(epenselist, new TypeReference<List<ExpenseDto>>() {});
    }

    private void setCurrency(ExpenseDto expenseDto) {
        if(Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("inr");
        }
    }

}
