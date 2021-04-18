package com.asc.loanservice.domain.evaluators;

import com.asc.loanservice.application.LoanCommand;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class AgeEvaluator implements Evaluator {

    private final LoanCommand loanCommand;
    private static final int CUSTOMER_MAX_AGE = 65;
    
    @Override
    public boolean invalid() {
        LocalDate lastInstallmentsDate = getLastInstallmentsDate(loanCommand);
        return lastInstallmentsDate.minusYears(loanCommand.getCustomerBirthday().getYear()).getYear() >= CUSTOMER_MAX_AGE;
    }

    private LocalDate getLastInstallmentsDate(LoanCommand loanCommand){
        return loanCommand.getFirstInstallmentDate().plusMonths(loanCommand.getNumberOfInstallments());
    }
}
