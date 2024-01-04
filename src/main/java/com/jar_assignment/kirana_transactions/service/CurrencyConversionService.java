package com.jar_assignment.kirana_transactions.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private final RestTemplate restTemplate;

    public CurrencyConversionService() {
        this.restTemplate = new RestTemplate();
    }

    public double getCurrentConversionRate() {
        String url = "https://api.fxratesapi.com/latest";

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            return rates != null ? rates.get("INR") : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
