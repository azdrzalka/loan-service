package com.asc.loanservice.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Loan not found")
public class LoanNotFoundException extends RuntimeException {
}
