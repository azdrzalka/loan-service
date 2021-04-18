package com.asc.loanservice.api;

import com.asc.loanservice.application.LoanHandler;
import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/loans")
public class LoanRequestController {

    private final LoanHandler loanHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanRequestRegistrationResultDto register(@Valid @RequestBody LoanRequestDto loanRequest){
        return loanHandler.registerLoan(loanRequest);
    }

    @GetMapping("/{loanNumber}")
    public LoanRequestDataDto getByNumber(@PathVariable("loanNumber") String loanNumber){
        return loanHandler.getLoanByLoanNumber(loanNumber);
    }
}
