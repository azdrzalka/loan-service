package com.asc.loanservice.domain.evaluators;

import com.asc.loanservice.application.LoanCommand;
import com.asc.loanservice.domain.LoanEvaluationResult;
import com.asc.loanservice.external.DebtorHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomerLoanEvaluator implements CustomerLoanEvaluatorService {

    private final DebtorHistoryService debtorHistoryService;

    @Override
    public LoanEvaluationResult evaluate(LoanCommand loanCommand) {
        List<Evaluator> evaluators = EvaluatorFactory.createEvaluators(loanCommand, debtorHistoryService);
        if (evaluators.stream().anyMatch(Evaluator::invalid)) {
            return LoanEvaluationResult.REJECTED;
        }
        return LoanEvaluationResult.APPROVED;
    }
}
