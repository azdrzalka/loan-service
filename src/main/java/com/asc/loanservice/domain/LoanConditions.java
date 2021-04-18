package com.asc.loanservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@Getter
@AllArgsConstructor
@Builder
class LoanConditions {

    private BigDecimal loanAmount;
    private Integer numberOfInstallments;
    private LocalDate firstInstallmentDate;
    @Transient
    private Double loanRate;

    private LoanConditions(){}

}
