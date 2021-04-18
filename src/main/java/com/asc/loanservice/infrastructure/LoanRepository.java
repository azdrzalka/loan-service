package com.asc.loanservice.infrastructure;

import com.asc.loanservice.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(value="SELECT * FROM LOAN loan where loan.LOAN_NUMBER=?1", nativeQuery = true)
    Optional<Loan> findLoanByLoanNumber(String loanNumber);

}
