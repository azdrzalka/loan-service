package com.asc.loanservice.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DebtorHistoryService {

    @Value("${debtor.service.endpoint.customerOnDebtorList}")
    private String customerCheckRestPath;

    public Boolean isCustomerOnDebtorList(String customerTaxId) {
        String restPath = String.format(customerCheckRestPath, customerTaxId);
        try {
            CustomerCheckResultDto customerCheckResultDto = new RestTemplate().getForObject(restPath, CustomerCheckResultDto.class);
            return customerCheckResultDto.isRegisteredDebtor();
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new DebtorServiceHttpException("" + customerTaxId);
        }
    }

}
