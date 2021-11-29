package com.learnig.ms.currencyexchange.controller;

import com.learnig.ms.currencyexchange.entity.CurrencyExchange;
import com.learnig.ms.currencyexchange.repository.CurrencyExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/exchange")
@Slf4j
public class CurrencyExchangeController {
    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @RequestMapping(value = "/from/{from}/to/{to}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CurrencyExchange retrieveExchange(@PathVariable String from, @PathVariable String to) {
        log.info("CurrencyExchangeController -> retrieveExchange invoked");
        Optional<CurrencyExchange> optionalCurrencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        if (!optionalCurrencyExchange.isPresent()) {
            throw new RuntimeException("CurrencyExchange details not found for from : " + from + " and to: " + to);
        }
        CurrencyExchange currencyExchange = optionalCurrencyExchange.get();
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
