package com.asc.loanservice.application;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.LoanData;
import com.asc.loanservice.domain.LoanEvaluationResult;
import com.asc.loanservice.domain.LoanService;
import com.asc.loanservice.domain.evaluators.CustomerLoanEvaluatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoanHandler {

    private final CustomerLoanEvaluatorService customerLoanEvaluatorService;
    private final LoanService loanService;
    private Double loanRate;

    public LoanHandler(CustomerLoanEvaluatorService customerLoanEvaluatorService,
                       LoanService loanService,
                       @Value("${loan.rate}") Double loanRate) {
        this.customerLoanEvaluatorService = customerLoanEvaluatorService;
        this.loanService = loanService;
        this.loanRate = loanRate;
    }

    public LoanRequestRegistrationResultDto registerLoan(LoanRequestDto loanRequestDto) {
        LoanCommand loanCommand = LoanCommandConverter.newLoanCommand(loanRequestDto, loanRate);
        LoanEvaluationResult loanEvaluationResult = customerLoanEvaluatorService.evaluate(loanCommand);
        LoanData loanData = loanService.createLoan(loanCommand, loanEvaluationResult);
        return new LoanRequestRegistrationResultDto(
                loanData.getLoanRequestNumber(),
                LoanRequestEvaluationResult.valueOf(loanData.getEvaluationResult().name())
        );
    }

    public LoanRequestDataDto getLoanByLoanNumber(String loanNumber) {
        LoanData loanData = loanService.getLoanDataByLoanNumber(loanNumber);
        return buildLoanRequestDataDto(loanData);
    }

    private LoanRequestDataDto buildLoanRequestDataDto(LoanData loanData) {
        return new LoanRequestDataDto(loanData.getLoanRequestNumber(),
                loanData.getCustomerName(),
                loanData.getCustomerBirthday(),
                loanData.getCustomerTaxId(),
                loanData.getCustomerMonthlyIncome(),
                loanData.getLoanAmount(),
                loanData.getNumberOfInstallments(),
                loanData.getFirstInstallmentDate(),
                LoanRequestEvaluationResult.valueOf(loanData.getEvaluationResult().name()),
                loanData.getRegistrationDate());
    }

}
