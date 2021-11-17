package dev.tonholo.chronosimplesapi.repository.postgres.mapper;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.repository.postgres.entity.ExpenseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExpenseMapper {
    public ExpenseEntity from(Expense expense) {
        return ExpenseEntity.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .value(expense.getValue())
                .type(expense.getType())
                .periodBegin(expense.getPeriodBegin())
                .periodEnd(expense.getPeriodEnd())
                .createdAt(expense.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Expense from(ExpenseEntity expenseEntity) {
        return Expense.builder()
                .id(expenseEntity.getId())
                .description(expenseEntity.getDescription())
                .value(expenseEntity.getValue())
                .type(expenseEntity.getType())
                .periodBegin(expenseEntity.getPeriodBegin())
                .periodEnd(expenseEntity.getPeriodEnd())
                .createdAt(expenseEntity.getCreatedAt())
                .updatedAt(expenseEntity.getUpdatedAt())
                .build();
    }
}
