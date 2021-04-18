package com.asc.loanservice.application;

import com.asc.loanservice.contracts.LoanRequestDto;

class LoanCommandConverter {

    private LoanCommandConverter() {}

    static LoanCommand newLoanCommand(LoanRequestDto loanRequestDto, Double loanRate) {
        return LoanCommand.builder()
                .customerName(loanRequestDto.getCustomerName())
                .customerBirthday(loanRequestDto.getCustomerBirthday())
                .customerTaxId(loanRequestDto.getCustomerTaxId())
                .customerMonthlyIncome(loanRequestDto.getCustomerMonthlyIncome())
                .loanAmount(loanRequestDto.getLoanAmount())
                .numberOfInstallments(loanRequestDto.getNumberOfInstallments())
                .firstInstallmentDate(loanRequestDto.getFirstInstallmentDate())
                .loanRate(loanRate)
                .build();
    }
}
