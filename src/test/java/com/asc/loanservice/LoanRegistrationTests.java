package com.asc.loanservice;

import com.asc.loanservice.application.LoanCommand;
import com.asc.loanservice.application.LoanHandler;
import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.Loan;
import com.asc.loanservice.domain.LoanData;
import com.asc.loanservice.domain.LoanEvaluationResult;
import com.asc.loanservice.domain.LoanService;
import com.asc.loanservice.domain.evaluators.CustomerLoanEvaluator;
import com.asc.loanservice.external.DebtorHistoryService;
import com.asc.loanservice.infrastructure.LoanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.asc.loanservice.contracts.LoanRequestEvaluationResult.APPROVED;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanRegistrationTests {
    
    private static final String CUSTOMER_NAME = "Jan Kowalski";
    private static final String CUSTOMER_TAX_ID = "12345";

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private DebtorHistoryService debtorHistoryService;
    @InjectMocks
    private CustomerLoanEvaluator customerLoanEvaluator;
    @InjectMocks
    private LoanService loanService;
    private LoanHandler loanHandler;

    @Before
    public void setUp() {
        this.loanHandler = new LoanHandler(customerLoanEvaluator, loanService, 4.0);
        when(loanRepository.save(Mockito.any(Loan.class)))
                .thenAnswer(it -> it.getArguments()[0]);
        when(debtorHistoryService.isCustomerOnDebtorList(any())).thenReturn(false);
    }

    @Test
    public void Should_EvaluationResultBeApproved_When_EachOfTheEvaluationConditionsIsValid() {
        LoanRequestDto loanRequestDto = new LoanRequestDto(CUSTOMER_NAME,
                LocalDate.of(1990, 12, 12),
                CUSTOMER_TAX_ID,
                BigDecimal.valueOf(8000),
                BigDecimal.valueOf(50000),
                48,
                LocalDate.of(2020, 12, 12)
        );
        LoanRequestRegistrationResultDto loanRequestRegistrationResultDto = loanHandler.registerLoan(loanRequestDto);
        assertEquals(APPROVED, loanRequestRegistrationResultDto.getEvaluationResult());
    }

    @Test
    public void Should_SaveLoan_When_CreateLoanIsCalledOnLoanService() {
        LoanCommand loanCommand = LoanCommand.builder()
                .customerName(CUSTOMER_NAME)
                .customerBirthday(LocalDate.of(1990, 12, 12))
                .customerTaxId(CUSTOMER_TAX_ID)
                .customerMonthlyIncome(BigDecimal.valueOf(8000))
                .loanAmount(BigDecimal.valueOf(50000))
                .numberOfInstallments(48)
                .firstInstallmentDate(LocalDate.of(2020, 12, 12))
                .build();
        loanService.createLoan(loanCommand, LoanEvaluationResult.APPROVED);

        Mockito.verify(loanRepository, times(1)).save(any(Loan.class));
    }

}
