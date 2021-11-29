package com.learning.ms.currencyconversion.feignclient;

import com.learning.ms.currencyconversion.model.CurrencyExchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient(name = "CURRENCY-EXCHANGE-SERVICE", url = "http://localhost:8000")     // without eureka
@FeignClient(name = "CURRENCY-EXCHANGE-SERVICE")     // with eureka
public interface CurrencyExchangeProxy {
    @RequestMapping("/api/exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchange(@PathVariable String from, @PathVariable String to);
}
