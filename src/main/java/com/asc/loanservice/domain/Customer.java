package com.asc.loanservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@Getter
@AllArgsConstructor
@Builder
class Customer {

    private String customerName;
    private LocalDate customerBirthday;
    private String customerTaxId;
    private BigDecimal customerMonthlyIncome;

    private Customer(){}

}
