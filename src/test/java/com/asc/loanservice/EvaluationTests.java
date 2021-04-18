package com.asc.loanservice;

import com.asc.loanservice.application.LoanCommand;
import com.asc.loanservice.domain.evaluators.AgeEvaluator;
import com.asc.loanservice.domain.evaluators.DebtHistoryEvaluator;
import com.asc.loanservice.domain.evaluators.Evaluator;
import com.asc.loanservice.domain.evaluators.MonthInstallmentEvaluator;
import com.asc.loanservice.external.DebtorHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvaluationTests {

    private static final String CUSTOMER_NAME = "Jan Kowalski";
    private static final String CUSTOMER_TAX_ID = "12345";

    @Mock
    private DebtorHistoryService debtorHistoryService;

    @Before
    public void setUp() {
        when(debtorHistoryService.isCustomerOnDebtorList(any())).thenReturn(false);
    }

    @Test
    public void Should_AgeEvaluatorResultBeRejected_When_CustomerIsOlderThan65YearsOldOnTheDateOfTheLastInstallment() {
        LoanCommand loanRequestDto = LoanCommand.builder()
                .customerName(CUSTOMER_NAME)
                .customerBirthday(LocalDate.of(1958, 12, 1))
                .customerTaxId(CUSTOMER_TAX_ID)
                .customerMonthlyIncome(BigDecimal.valueOf(8000))
                .loanAmount(BigDecimal.valueOf(50000))
                .numberOfInstallments(48).firstInstallmentDate(LocalDate.of(2020, 12, 12))
                .build();

        Evaluator ageEvaluator = new AgeEvaluator(loanRequestDto);
        assertTrue(ageEvaluator.invalid());
    }

    @Test
    public void Should_DebtHistoryEvaluatorResultBeRejected_When_CustomerIsOnDebtorList() {
        LoanCommand loanCommand = LoanCommand.builder()
                .customerName(CUSTOMER_NAME)
                .customerBirthday(LocalDate.of(1990, 12, 12))
                .customerTaxId(CUSTOMER_TAX_ID)
                .customerMonthlyIncome(BigDecimal.valueOf(2000))
                .loanAmount(BigDecimal.valueOf(5000))
                .numberOfInstallments(100)
                .firstInstallmentDate(LocalDate.of(2020, 12, 12))
                .build();

        when(debtorHistoryService.isCustomerOnDebtorList(any())).thenReturn(true);

        Evaluator ageEvaluator = new DebtHistoryEvaluator(loanCommand, debtorHistoryService);
        assertTrue(ageEvaluator.invalid());
    }

    @Test
    public void Should_MonthInstallmentEvaluatorResultBeRejected_When_FifteenPercentOfCustomerIncomeIsGreaterThanInstallmentRate() {
        LoanCommand loanCommand = LoanCommand.builder()
                .customerName(CUSTOMER_NAME)
                .customerBirthday(LocalDate.of(1990, 12, 12))
                .customerTaxId(CUSTOMER_TAX_ID)
                .customerMonthlyIncome(BigDecimal.valueOf(2000))
                .loanAmount(BigDecimal.valueOf(500000))
                .numberOfInstallments(100)
                .firstInstallmentDate(LocalDate.of(2020, 12, 12))
                .loanRate(4.0)
                .build();

        Evaluator ageEvaluator = new MonthInstallmentEvaluator(loanCommand);
        assertTrue(ageEvaluator.invalid());
    }


}
