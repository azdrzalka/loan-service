package com.asc.loanservice.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CustomerCheckResultDto {

    private String customerTaxId;
    private boolean isRegisteredDebtor;

}
