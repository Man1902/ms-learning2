package com.learning.ms.currencyconversion.controller;

import com.learning.ms.currencyconversion.feignclient.CurrencyExchangeProxy;
import com.learning.ms.currencyconversion.model.CurrencyConversion;
import com.learning.ms.currencyconversion.model.CurrencyExchange;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class CurrencyConversionController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @RequestMapping("/conversion/from/{from}/to/{to}/quantity/{quantity}")
    @Retry(name = "default")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        log.info("calculateCurrencyConversion request called");

        // String url = "http://localhost:8000/api/exchange/from/{from}/to/{to}";
        String url = "http://currency-exchange-service/api/exchange/from/{from}/to/{to}";
        Map<String, String> uriParameters = new HashMap<>(2);
        uriParameters.put("from", from);
        uriParameters.put("to", to);
        // Approach1
        ResponseEntity<CurrencyExchange> currencyExchangeResponse = restTemplate.getForEntity(url, CurrencyExchange.class, uriParameters);
        CurrencyExchange currencyExchange = currencyExchangeResponse.getBody();

        // Approach2
        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
        ResponseEntity<CurrencyExchange> currencyExchangeResponse = restTemplate.exchange(url, HttpMethod.GET, requestEntity, CurrencyExchange.class, uriParameters);
        CurrencyExchange currencyExchange = currencyExchangeResponse.getBody();*/

        CurrencyConversion currencyConversion = CurrencyConversion.builder()
                .id(currencyExchange.getId())
                .from(currencyExchange.getFrom())
                .to(currencyExchange.getTo())
                .conversionMultiple(currencyExchange.getConversionMultiple())
                .quantity(quantity)
                .totalCalculatedAmt(currencyExchange.getConversionMultiple().multiply(quantity))
                .environment(currencyExchange.getEnvironment() + " using REST")
                .build();
        return currencyConversion;
    }

    @RequestMapping("/conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyExchange currencyExchange = currencyExchangeProxy.retrieveExchange(from, to);

        CurrencyConversion currencyConversion = CurrencyConversion.builder()
                .id(currencyExchange.getId())
                .from(currencyExchange.getFrom())
                .to(currencyExchange.getTo())
                .conversionMultiple(currencyExchange.getConversionMultiple())
                .quantity(quantity)
                .totalCalculatedAmt(currencyExchange.getConversionMultiple().multiply(quantity))
                .environment(currencyExchange.getEnvironment() + " using Feign")
                .build();
        return currencyConversion;
    }
}
