package com.asc.loanservice.application;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Value
public class LoanCommand {
    @NotNull
    String customerName;
    @NotNull
    LocalDate customerBirthday;
    @NotNull
    String customerTaxId;
    @NotNull
    BigDecimal customerMonthlyIncome;
    @NotNull
    BigDecimal loanAmount;
    @NotNull
    Integer numberOfInstallments;
    @NotNull
    LocalDate firstInstallmentDate;
    @NotNull
    Double loanRate;
}
