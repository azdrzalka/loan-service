package com.asc.loanservice.contracts;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDto {
    @NonNull
    private String customerName;
    @Past
    private LocalDate customerBirthday;
    @NonNull
    private String customerTaxId;
    @NonNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Customer monthly income is unacceptable")
    private BigDecimal customerMonthlyIncome;
    @NonNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Loan amount is unacceptable")
    private BigDecimal loanAmount;
    @NonNull
    @Min(value = 1)
    private Integer numberOfInstallments;
    @Future
    private LocalDate firstInstallmentDate;
}
