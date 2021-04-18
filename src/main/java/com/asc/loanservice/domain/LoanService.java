package com.asc.loanservice.domain;

import com.asc.loanservice.application.LoanCommand;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.domain.exceptions.LoanNotFoundException;
import com.asc.loanservice.infrastructure.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanData getLoanDataByLoanNumber(String loanNumber) {
        Loan loan = loanRepository.findLoanByLoanNumber(loanNumber).orElseThrow(LoanNotFoundException::new);
        return buildLoanData(loan);
    }

    public LoanData createLoan(LoanCommand loanCommand, LoanEvaluationResult loanEvaluationResult) {
        Customer customer = createCustomer(loanCommand);
        LoanConditions loanConditions = createLoanConditions(loanCommand);
        Loan loan = new Loan(customer, loanConditions, new LoanNumber(), LocalDateTime.now(), loanEvaluationResult);
        return buildLoanData(loanRepository.save(loan));
    }

    private LoanData buildLoanData(Loan loan) {
        Customer customer = loan.getCustomer();
        LoanConditions loanConditions = loan.getLoanConditions();
        return LoanData.builder()
                .loanRequestNumber(loan.getLoanNumber().getLoanNumber())
                .customerName(customer.getCustomerName())
                .customerBirthday(customer.getCustomerBirthday())
                .customerTaxId(customer.getCustomerTaxId())
                .customerMonthlyIncome(customer.getCustomerMonthlyIncome())
                .loanAmount(loanConditions.getLoanAmount())
                .numberOfInstallments(loanConditions.getNumberOfInstallments())
                .firstInstallmentDate(loanConditions.getFirstInstallmentDate())
                .evaluationResult(LoanRequestEvaluationResult.valueOf(loan.getLoanEvaluationResult().name()))
                .registrationDate(loan.getRegistrationDate())
                .build();
    }

    private LoanConditions createLoanConditions(LoanCommand loanCommand) {
        return LoanConditions.builder()
                .loanAmount(loanCommand.getLoanAmount())
                .numberOfInstallments(loanCommand.getNumberOfInstallments())
                .firstInstallmentDate(loanCommand.getFirstInstallmentDate())
                .build();
    }

    private Customer createCustomer(LoanCommand loanCommand) {
        return Customer.builder()
                .customerName(loanCommand.getCustomerName())
                .customerBirthday(loanCommand.getCustomerBirthday())
                .customerTaxId(loanCommand.getCustomerTaxId())
                .customerMonthlyIncome(loanCommand.getCustomerMonthlyIncome())
                .build();
    }

}
