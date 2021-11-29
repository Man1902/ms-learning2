package com.learnig.ms.currencyexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@XmlRootElement
public class CurrencyExchange {
    @Id
    private Long id;

    @Column(name = "CURRENCY_FROM")
    private String from;

    @Column(name = "CURRENCY_TO")
    private String to;

    private BigDecimal conversionMultiple;

    private String environment;
}
