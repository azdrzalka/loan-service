package com.asc.loanservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Customer customer;
    private LoanConditions loanConditions;
    private LoanNumber loanNumber;
    private LocalDateTime registrationDate;
    private LoanEvaluationResult loanEvaluationResult;

    private Loan(){}

    Loan(Customer customer, LoanConditions loanConditions,
         LoanNumber loanNumber, LocalDateTime registrationDate,
         LoanEvaluationResult loanEvaluationResult) {
        this.customer = customer;
        this.loanConditions = loanConditions;
        this.loanNumber = loanNumber;
        this.registrationDate = registrationDate;
        this.loanEvaluationResult = loanEvaluationResult;
    }

}
