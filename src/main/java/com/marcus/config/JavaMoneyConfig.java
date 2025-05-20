package com.marcus.config;

import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.convert.ecb.ECBCurrentRateProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JavaMoneyConfig {

  @Bean
  public ExchangeRateProvider exchangeRateProvider() {
    ExchangeRateProvider provider = new ECBCurrentRateProvider();
    new Thread(
            () -> {
              try {
                MonetaryConversions.getExchangeRateProvider("ECB").getExchangeRate("USD", "EUR");
                MonetaryConversions.getExchangeRateProvider("ECB").getExchangeRate("EUR", "USD");
                MonetaryConversions.getExchangeRateProvider("ECB").getExchangeRate("EUR", "GBP");
                MonetaryConversions.getExchangeRateProvider("ECB").getExchangeRate("GBP", "EUR");
                MonetaryConversions.getExchangeRateProvider("ECB").getExchangeRate("GBP", "USD");
                MonetaryConversions.getExchangeRateProvider("ECB").getExchangeRate("USD", "GBP");
                log.info("Exchange rates preloaded successfully.");
              } catch (Exception e) {
                log.error("Failed to preload exchange rates: {}", e.getMessage());
              }
            })
        .start();
    return provider;
  }
}
