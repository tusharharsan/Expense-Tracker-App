package com.example.service.Controller;

import com.example.service.Dto.ExpenseDto;
import com.example.service.Entities.ExpenseEntity;
import com.example.service.Service.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense/v1")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping(path = "/getExpense")
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(@RequestParam("user_id") @NonNull String userId) {
        try{
            List<ExpenseDto> expenseDtoList = expenseService.getExpense(userId);
            return new ResponseEntity<>(expenseDtoList , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path="/update")
    public ResponseEntity<Boolean> updateExpense(@RequestBody ExpenseDto expenseDto) {
        try{
            boolean isUpdated = expenseService.UpdateExpense(expenseDto);
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path ="/delete")
    public ResponseEntity<Boolean> deleteExpense(@RequestParam("user_id") @NonNull String userId){
        try{
            boolean isDeleted = expenseService.deleteExpense(userId);
            return new ResponseEntity<>(isDeleted, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/addExpennse")
    public ResponseEntity<Boolean> addExpense(@RequestHeader(value = "X-User-Id") @NonNull String userId , @RequestBody ExpenseDto expenseDto){
        try {
            expenseDto.setUserId(userId);
            return new ResponseEntity<>(expenseService.createExpense(expenseDto) , HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(false , HttpStatus.BAD_REQUEST);
        }
    }

}
