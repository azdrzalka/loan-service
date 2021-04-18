package com.asc.loanservice.domain.evaluators;

import com.asc.loanservice.application.LoanCommand;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class MonthInstallmentEvaluator implements Evaluator {

    private final LoanCommand loanCommand;
    private static final BigDecimal PERCENTAGE_OF_MONTHLY_INCOME = BigDecimal.valueOf(0.15);
    private static final Integer ONE_HUNDRED = 100;
    private static final Integer MONTHS = 12;

    @Override
    public boolean invalid() {
        return isFifteenPercentOfMonthlyIncomeNotExceedMonthlyInstallment(loanCommand);
    }

    private boolean isFifteenPercentOfMonthlyIncomeNotExceedMonthlyInstallment(LoanCommand loanCommand) {
        return loanCommand.getCustomerMonthlyIncome().multiply(PERCENTAGE_OF_MONTHLY_INCOME).compareTo(getMonthlyInstallment(loanCommand)) < 0;
    }

    private BigDecimal getMonthlyInstallment(LoanCommand loanCommand) {
        double monthlyRate = (loanCommand.getLoanRate() / ONE_HUNDRED / MONTHS);
        return loanCommand.getLoanAmount().multiply(BigDecimal.valueOf(monthlyRate)).divide(
                BigDecimal.valueOf(1 - Math.pow(1 + monthlyRate, -loanCommand.getNumberOfInstallments())), 2, RoundingMode.HALF_UP);
    }
}
