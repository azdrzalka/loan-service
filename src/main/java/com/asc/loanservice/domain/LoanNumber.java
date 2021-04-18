package com.asc.loanservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
@AllArgsConstructor
class LoanNumber {

    private String loanNumber;

    LoanNumber() {
        this.loanNumber = UUID.randomUUID().toString();
    }

}
