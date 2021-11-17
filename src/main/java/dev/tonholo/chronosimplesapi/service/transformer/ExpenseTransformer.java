package dev.tonholo.chronosimplesapi.service.transformer;

import dev.tonholo.chronosimplesapi.domain.Expense;
import dev.tonholo.chronosimplesapi.domain.event.ExpenseCreationEvent;
import dev.tonholo.chronosimplesapi.domain.event.ExpenseUpdateEvent;
import org.springframework.stereotype.Service;

@Service
public class ExpenseTransformer {

    public Expense from(ExpenseCreationEvent expenseCreationEvent) {
        return Expense.builder()
                .description(expenseCreationEvent.getDescription())
                .value(expenseCreationEvent.getValue())
                .type(expenseCreationEvent.getType())
                .periodBegin(expenseCreationEvent.getPeriodBegin())
                .periodEnd(expenseCreationEvent.getPeriodEnd())
                .build();
    }

    public Expense from(ExpenseUpdateEvent expenseUpdateEvent) {
        return Expense.builder()
                .id(expenseUpdateEvent.getId())
                .value(expenseUpdateEvent.getValue())
                .type(expenseUpdateEvent.getType())
                .periodBegin(expenseUpdateEvent.getPeriodBegin())
                .periodEnd(expenseUpdateEvent.getPeriodEnd())
                .build();
    }
}
