package com.asc.loanservice.domain.evaluators;

import com.asc.loanservice.application.LoanCommand;
import com.asc.loanservice.domain.LoanEvaluationResult;

public interface CustomerLoanEvaluatorService {
    LoanEvaluationResult evaluate(LoanCommand loanCommand);
}
