package dev.tonholo.chronosimplesapi.web.transformer;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.domain.event.ExpenseCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.ExpenseUpdateEvent;
import dev.tonholo.chronosimplesapi.web.model.ExpenseRequest;
import dev.tonholo.chronosimplesapi.web.model.ExpenseResponse;
import org.springframework.stereotype.Service;

@Service
public class ExpenseWebTransformer {

    public ExpenseCreationEvent from(ExpenseRequest expenseRequest) {
        return ExpenseCreationEvent.builder()
                .description(expenseRequest.getDescription())
                .value(expenseRequest.getValue())
                .type(expenseRequest.getType())
                .periodBegin(expenseRequest.getPeriodBegin())
                .periodEnd(expenseRequest.getPeriodEnd())
                .build();
    }

    public ExpenseUpdateEvent from(ExpenseRequest expenseRequest, String expenseId) {
        return ExpenseUpdateEvent.builder()
                .id(expenseId)
                .description(expenseRequest.getDescription())
                .value(expenseRequest.getValue())
                .type(expenseRequest.getType())
                .periodBegin(expenseRequest.getPeriodBegin())
                .periodEnd(expenseRequest.getPeriodEnd())
                .build();
    }

    public ExpenseResponse from(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .value(expense.getValue())
                .type(expense.getType())
                .periodBegin(expense.getPeriodBegin())
                .periodEnd(expense.getPeriodEnd())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }
}
