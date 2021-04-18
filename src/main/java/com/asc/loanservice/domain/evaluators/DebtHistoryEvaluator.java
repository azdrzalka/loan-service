package com.asc.loanservice.domain.evaluators;

import com.asc.loanservice.application.LoanCommand;
import com.asc.loanservice.external.DebtorHistoryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DebtHistoryEvaluator implements Evaluator {

    private final LoanCommand loanCommand;
    private final DebtorHistoryService debtorHistoryService;

    @Override
    public boolean invalid() {
        return debtorHistoryService.isCustomerOnDebtorList(loanCommand.getCustomerTaxId());
    }
}
