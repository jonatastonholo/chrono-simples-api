package dev.tonholo.chronosimplesapi.web.converter;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.domain.type.ExpenseType;
import dev.tonholo.chronosimplesapi.service.event.ExpenseCreationEvent;
import dev.tonholo.chronosimplesapi.service.event.ExpenseUpdateEvent;
import dev.tonholo.chronosimplesapi.web.dto.ExpenseRequest;
import dev.tonholo.chronosimplesapi.web.dto.ExpenseResponse;
import dev.tonholo.chronosimplesapi.web.dto.ExpenseTypeResponse;
import org.springframework.stereotype.Service;

@Service
public class ExpenseConverter {

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
                .type(from(expense.getType()))
                .periodBegin(expense.getPeriodBegin())
                .periodEnd(expense.getPeriodEnd())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }

    private ExpenseTypeResponse from(ExpenseType type) {
        return ExpenseTypeResponse.builder()
                .value(type.toString())
                .description(type.getDescription())
                .build();
    }
}
