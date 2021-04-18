package com.asc.loanservice.external;

class DebtorServiceHttpException extends RuntimeException {
    DebtorServiceHttpException(String message) {
        super(message);
    }
}
