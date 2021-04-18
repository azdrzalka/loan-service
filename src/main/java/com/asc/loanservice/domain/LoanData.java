package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Value
@Builder
public class LoanData {
    String loanRequestNumber;
    String customerName;
    LocalDate customerBirthday;
    String customerTaxId;
    BigDecimal customerMonthlyIncome;
    BigDecimal loanAmount;
    Integer numberOfInstallments;
    LocalDate firstInstallmentDate;
    LoanRequestEvaluationResult evaluationResult;
    LocalDateTime registrationDate;
}
