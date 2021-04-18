package com.asc.loanservice;

import com.asc.loanservice.application.LoanHandler;
import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.domain.LoanData;
import com.asc.loanservice.domain.LoanService;
import com.asc.loanservice.domain.evaluators.CustomerLoanEvaluator;
import com.asc.loanservice.domain.exceptions.LoanNotFoundException;
import com.asc.loanservice.infrastructure.LoanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetLoanDataTests {

    @Mock
    private CustomerLoanEvaluator customerLoanEvaluator;
    @Mock
    private LoanRepository loanRepository;
    private LoanHandler loanHandler;
    @Mock
    private LoanService loanServiceMock;
    @InjectMocks
    private LoanService loanService;

    @Before
    public void setUp() {
        this.loanHandler = new LoanHandler(customerLoanEvaluator, loanServiceMock, 4.0);
    }

    @Test
    public void Should_ReturnLoanRequestData_When_GetLoanByLoanNumberIsCalledOnLoanHandler() {
        String loanNumber = "f6d815d5-dcbd-4cd6-9cd8-23424becd495";
        LoanData loanData = LoanData.builder()
                .evaluationResult(LoanRequestEvaluationResult.APPROVED)
                .loanRequestNumber(loanNumber)
                .build();

        when(loanServiceMock.getLoanDataByLoanNumber(loanNumber)).thenReturn(loanData);

        LoanRequestDataDto loanByLoanNumber = loanHandler.getLoanByLoanNumber(loanNumber);
        assertEquals(loanNumber, loanByLoanNumber.getLoanRequestNumber());
    }

    @Test(expected = LoanNotFoundException.class)
    public void Should_ThrowLoanNotException_When_FindByLoanNumberIsReturningNull() {
        String loanNumber = "f6d815d5-dcbd-4cd6-9cd8-23424becd495";

        when(loanRepository.findLoanByLoanNumber(loanNumber)).thenReturn(Optional.empty());

        loanService.getLoanDataByLoanNumber(loanNumber);
    }

}
