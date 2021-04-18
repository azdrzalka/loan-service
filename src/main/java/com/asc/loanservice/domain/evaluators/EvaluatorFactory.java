package com.asc.loanservice.domain.evaluators;

import com.asc.loanservice.application.LoanCommand;
import com.asc.loanservice.external.DebtorHistoryService;

import java.util.Arrays;
import java.util.List;

class EvaluatorFactory {

    private EvaluatorFactory() {}

    static List<Evaluator> createEvaluators(LoanCommand loanCommand, DebtorHistoryService debtorHistoryService) {
        return Arrays.asList(
                new AgeEvaluator(loanCommand),
                new MonthInstallmentEvaluator(loanCommand),
                new DebtHistoryEvaluator(loanCommand, debtorHistoryService)
        );
    }

}
